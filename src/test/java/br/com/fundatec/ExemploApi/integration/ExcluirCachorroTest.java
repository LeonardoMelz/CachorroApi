package br.com.fundatec.ExemploApi.integration;

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

import br.com.fundatec.ExemploApi.entity.Cachorro;
import br.com.fundatec.ExemploApi.entity.PorteParametro;
import br.com.fundatec.ExemploApi.repository.CachorroRepository;
import br.com.fundatec.ExemploApi.repository.PorteParametroRepository;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExcluirCachorroTest {

	@LocalServerPort
	private int port;
	@Autowired
	private CachorroRepository cachorroRepository;
	@Autowired
	private PorteParametroRepository porteParametroRepository;
	
	@Before
	public void setUp() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
		cachorroRepository.deleteAll();
		porteParametroRepository.deleteAll();
	
	}
	
	@Test
	public void deveDeletarCachorro() {
		Cachorro cachorro = new Cachorro(null, "Tobias", "Beagle", "Pequeno", 2);
		cachorro = cachorroRepository.save(cachorro);
		
		RestAssured
		.given()
		.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
		.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.when()
		.delete("/v1/cachorros/{id}",cachorro.getId())
		.then ()
		.assertThat()
		.statusCode(HttpStatus.OK.value());

		Cachorro cachorroDeletado = cachorroRepository.findById(cachorro.getId()).orElse(null);
		Assert.assertNull(cachorroDeletado);
		Assert.assertTrue(cachorroRepository.count() == 0);
	
	

	
	
	
	}
}
