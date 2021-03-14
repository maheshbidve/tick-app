package com.solactive.codingchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(classes = { CodingchallengeApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
	@Autowired
	@LocalServerPort
	private int serverPort;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void consume_tick_missing_timestamp_test() throws Exception {

		final String baseUrl = "http://localhost:" + serverPort + "v1/tick/consume";
		HttpHeaders headers = new HttpHeaders();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParam("input",
				"PRICE=5.24|CLOSE_PRICE=4.5|CURRENCY=EUR|RIC=ric2");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
				entity, String.class);

		// Verify request succeed
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Timestamp must be provided !", response.getBody());
	}

	@Test
	public void consume_tick_missing_ric_name_test() throws Exception {

		final String baseUrl = "http://localhost:" + serverPort + "v1/tick/consume";
		HttpHeaders headers = new HttpHeaders();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParam("input",
				"TIMESTAMP=1615626899|PRICE=5.24|CLOSE_PRICE=4.5|CURRENCY=EUR");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
				entity, String.class);

		// Verify request succeed
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("RIC name must be provided !", response.getBody());
	}

	@Test
	public void consume_tick_invalid_param_test() throws Exception {

		final String baseUrl = "http://localhost:" + serverPort + "v1/tick/consume";
		HttpHeaders headers = new HttpHeaders();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParam("input",
				"TIMESTAMP=1615626899|randomparam=5.24|CLOSE_PRICE=4.5|CURRENCY=EUR");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
				entity, String.class);

		// Verify request succeed
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Unknown parameter present in the input !", response.getBody());
	}

	@Test
	public void consume_tick_success_test() throws Exception {

		final String baseUrl = "http://localhost:" + serverPort + "v1/tick/consume";
		HttpHeaders headers = new HttpHeaders();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl).queryParam("input",
				"TIMESTAMP=1615626899|PRICE=5.24|CLOSE_PRICE=4.5|CURRENCY=EUR|RIC=ric2");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
				entity, String.class);

		// Verify request succeed
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void get_ric_specific_test() throws JSONException {
		String consumeUrl = "http://localhost:" + serverPort + "v1/tick/consume";
		HttpHeaders headers = new HttpHeaders();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(consumeUrl).queryParam("input",
				"TIMESTAMP=1615626899|PRICE=5.24|CLOSE_PRICE=4.5|CURRENCY=EUR|RIC=ric3");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
				entity, String.class);

		// Verify request succeed
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		builder = UriComponentsBuilder.fromHttpUrl(consumeUrl).queryParam("input",
				"TIMESTAMP=1615626999|PRICE=6.34|CLOSE_PRICE=5.5|CURRENCY=EUR|RIC=ric3");

		response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity, String.class);

		// Verify request succeed
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		consumeUrl = "http://localhost:" + serverPort + "v1/tick/getTicks/ric3";
		builder = UriComponentsBuilder.fromHttpUrl(consumeUrl);
		headers.set("x-api-key", "c3b2cd34fg3qdf6g7h8i9jadf");
		entity = new HttpEntity<>(headers);

		response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONArray object = new JSONArray(response.getBody());
		assertEquals(2, object.length());

	}

}
