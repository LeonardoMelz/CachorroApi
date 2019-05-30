package br.com.fundatec.ExemploApi.integration;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fundatec.ExemploApi.repository.CachorroRepository;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IncluirCachorroTest {
	
	@LocalServerPort
	private int port;
	@Autowired
	private CachorroRepository cachorroRepository;
	
	@Before
	public void setUp() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
		cachorroRepository.deleteAll();
	}
	
	@Test
	public void deveIncluirUmCachorro() {
		RestAssured
			.given()
			.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body("{" + 
					"	\"nome\": \"Urso\"," + 
					"	\"raca\": \"Pastor Belga\"," + 
					"	\"porte\": \"grande\"," + 
					"	\"idade\": 2" + 
					"}")
			.when()
			.post("/v1/cachorros")
			.then()
			.assertThat()
			.body("nome", Matchers.equalTo("Urso"))
			.body("raca", Matchers.equalTo("Pastor Belga"))
			.body("porte", Matchers.equalTo("grande"))
			.body("idade", Matchers.equalTo(2))
			.body("id", Matchers.greaterThan(0))
			.statusCode(HttpStatus.CREATED.value());
	
		Assert.assertTrue(cachorroRepository.count() > 0);
		
	}
	
	
}
