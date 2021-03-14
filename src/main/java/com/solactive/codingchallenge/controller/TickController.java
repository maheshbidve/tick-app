package com.solactive.codingchallenge.controller;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solactive.codingchallenge.exception.UnAuthorizedException;
import com.solactive.codingchallenge.model.Response;
import com.solactive.codingchallenge.model.Tick;
import com.solactive.codingchallenge.service.TickService;
import com.solactive.codingchallenge.util.Constants;

@RestController
@RequestMapping("v1/tick")
public class TickController {
	@Autowired
	private TickService service;

	/**
	 * Consume Tick
	 * 	This API is used to consume tick for various RIC's
	 * @param input
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = { "/consume" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> consumeTick(@RequestParam String input) {
		Tick tick = buildTick(input);
		validateTick(tick);
		service.consumeTick(tick);
		Response response = new Response();
		response.setStatusCode(HttpStatus.CREATED);
		response.setStatusMessage("Tick processed successfully !");
		return new ResponseEntity<>(response.getStatusMessage(), response.getStatusCode());
	}

	/**
	 * Get ticks API is used to list all the ticks for a specific RIC, it is secured by a x-api-key which will be unique to each RIC
	 * 
	 * @param key - header param
	 * @param ric - RIC name
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, path = { "/getTicks/{ric}" }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<Tick> getTicks(@RequestHeader("x-api-key") String key, @PathVariable String ric) throws Exception {
		if (!Constants.API_KEYS.containsKey(ric) || !Constants.API_KEYS.get(ric).equals(key)) {
			throw new UnAuthorizedException("Invalid api key provided !");
		}
		return service.getTicks(ric);
	}

	private void validateTick(Tick tick) {
		if (ObjectUtils.isEmpty(tick.getRicName())) {
			throw new IllegalArgumentException("RIC name must be provided !");
		} else if (ObjectUtils.isEmpty(tick.getTimestamp())) {
			throw new IllegalArgumentException("Timestamp must be provided !");
		} else if (ObjectUtils.isEmpty(tick.getCurrency())) {
			throw new IllegalArgumentException("Currency must be provided !");
		}
	}

	private Tick buildTick(String input) {
		Tick tick = new Tick();
		try {
			String[] data = input.split(Pattern.quote(Constants.PIPE));
			for (String pair : data) {
				String[] keyValue = pair.split(Constants.EQUAL);
				if (ObjectUtils.isEmpty(keyValue) || keyValue.length < 2) {
					continue;
				}
				String key = keyValue[0];
				String value = keyValue[1];

				switch (key) {
				case Constants.TIMESTAMP:
					Instant instant = Instant.ofEpochSecond(Long.valueOf(value));
					tick.setTimestamp(Date.from(instant));
					break;

				case Constants.PRICE:
					tick.setPrice(Double.valueOf(value));
					break;

				case Constants.CLOSE_PRICE:
					tick.setClose_price(Double.valueOf(value));
					break;

				case Constants.CURRENCY:
					tick.setCurrency(value);
					break;

				case Constants.RIC:
					tick.setRicName(value);
					break;

				default:
					throw new IllegalArgumentException("Unknown parameter present in the input !");
				}
			}
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid input parameters provided");
		}
		return tick;
	}

}
