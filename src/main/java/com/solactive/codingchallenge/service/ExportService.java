package com.solactive.codingchallenge.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.solactive.codingchallenge.model.Tick;
import com.solactive.codingchallenge.util.Constants;

/**
 * This class is used for tick export  
 * 
 * @author mahesh.bidve
 *
 */
@Service
public class ExportService {

	private static final Logger logger = LoggerFactory.getLogger(ExportService.class);

	/**
	 * This method is used to create csv file from the given ric
	 * 
	 * @param list
	 * @throws IOException
	 */
	public String createCSVFile(List<Tick> list, String fileName) throws IOException {
		FileWriter out = new FileWriter(fileName);
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(Constants.TIMESTAMP, Constants.PRICE,
				Constants.CLOSE_PRICE, Constants.CURRENCY, Constants.RIC))) {
			list.forEach((e) -> {
				try {
					printer.printRecord(e.getTimestamp(), e.getPrice(), e.getClose_price(), e.getCurrency(),
							e.getRicName());
				} catch (IOException ex) {
					logger.error("Error while creating csv file", ex);
				}
			});
		}
		return fileName;
	}
}
