package com.solactive.codingchallenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.solactive.codingchallenge.exception.ClosePriceNotSetException;
import com.solactive.codingchallenge.model.Tick;
import com.solactive.codingchallenge.service.TickService;

@SpringBootTest
public class TickTests {

	@Autowired
	TickService tickService;

	@Test
	public void get_ticks_test_success() {
		Tick tick = new Tick();
		tick.setRicName("ric1");
		tick.setCurrency("EUR");
		tick.setPrice(5.5);
		tick.setTimestamp(new Date());
		tickService.consumeTick(tick);

		tick = new Tick();
		tick.setRicName("ric1");
		tick.setCurrency("EUR");
		tick.setPrice(6.5);
		tick.setTimestamp(new Date());
		tickService.consumeTick(tick);
		assertEquals(2, tickService.getTicks("ric1").size());
	}

	@Test
	public void consumeTick_test_success1() {
		Tick tick = new Tick();
		tick.setRicName("ric2");
		tick.setCurrency("EUR");
		tick.setPrice(5.5);
		tick.setTimestamp(new Date());
		tickService.consumeTick(tick);

		assertEquals(1, tickService.getTicks("ric2").size());
	}
	
	@Test
	public void get_random_tick_test() {
		assertEquals(0, tickService.getTicks("random").size());
	}

	@Test
	public void export_test_success() throws Exception {
		Tick tick = new Tick();
		tick.setRicName("test");
		tick.setCurrency("EUR");
		tick.setPrice(5.5);
		tick.setTimestamp(new Date());
		tickService.consumeTick(tick);

		tick = new Tick();
		tick.setRicName("test");
		tick.setCurrency("EUR");
		tick.setPrice(6.5);
		tick.setTimestamp(new Date());
		tick.setClose_price(3.4);
		tickService.consumeTick(tick);
		assertEquals(2, tickService.getTicks("test").size());
		assertThat(tickService.exportTicks("test")).isNotEmpty();

	}

	@Test
	public void export_test_expected_fail() throws Exception {
		Tick tick = new Tick();
		tick.setRicName("fail");
		tick.setCurrency("EUR");
		tick.setPrice(5.5);
		tick.setTimestamp(new Date());
		tickService.consumeTick(tick);

		assertEquals(1, tickService.getTicks("fail").size());
		boolean thrown = false;
		try {
			tickService.exportTicks("fail");
		} catch (ClosePriceNotSetException e) {
			thrown = true;
		}

		assertTrue(thrown);

	}
}
