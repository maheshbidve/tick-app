package com.solactive.codingchallenge.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.solactive.codingchallenge.exception.ClosePriceNotSetException;
import com.solactive.codingchallenge.model.Tick;
import com.solactive.codingchallenge.util.Constants;;

/**
 * This class is used for tick 
 * 
 * @author mahesh.bidve
 *
 */
@Service
public class TickService {

	private static final Logger logger = LoggerFactory.getLogger(TickService.class);

	@Autowired
	ExportService exportService;

	private List<Tick> list = new ArrayList<>();

	/**
	 * Consume Tick
	 * 
	 * @param tick
	 * @return
	 */
	public Tick consumeTick(Tick tick) {
		list.add(tick);
		return tick;
	}

	/**
	 * Get ticks
	 * 
	 * @param ricName
	 * @return
	 */
	public List<Tick> getTicks(String ricName) {
		List<Tick> ricTicks = list.stream().filter(e -> e.getRicName().equals(ricName)).collect(Collectors.toList());
		if (!ObjectUtils.isEmpty(ricTicks) && ricTicks.stream().anyMatch(e -> e.getClose_price() > 0)) {
			try {
				String fileName = "ExportedFiles/" + "tick_" + getTimestamp() + ".csv";
				exportService.createCSVFile(ricTicks, fileName);
			} catch (IOException e) {
				logger.error("Failed to create csv file", e);
			}
		}
		return ricTicks;
	}

	/**
	 * export ticks
	 * 
	 * @param ricName
	 * @return
	 * @throws Exception
	 */
	public String exportTicks(String ricName) throws Exception {
		List<Tick> ricTicks = list.stream().filter(e -> e.getRicName().equals(ricName)).collect(Collectors.toList());
		if (!ObjectUtils.isEmpty(ricTicks) && ricTicks.stream().anyMatch(e -> e.getClose_price() > 0)) {
			String fileName = "ExportedFiles/" + "tick_" + ricName + "_" + getTimestamp() + ".csv";
			return exportService.createCSVFile(ricTicks, fileName);
		} else {
			throw new ClosePriceNotSetException("Close price is not set for the RIC " + ricName);
		}
	}

	/**
	 * getTimestamp
	 * 
	 * @return
	 */
	private static String getTimestamp() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat(Constants.DEFAULT_FILE_PATTERN);
		return format.format(date);
	}

}
