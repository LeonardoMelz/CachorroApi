package br.com.fundatec.ExemploApi.integration;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fundatec.ExemploApi.entity.Cachorro;
import br.com.fundatec.ExemploApi.repository.CachorroRepository;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConsultarCachorroTest {
	
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
	public void deveBuscarUmaListaDeCachorros() {
		cachorroRepository.save(new Cachorro(null, "Bob", "Poodle", "Médio", 15));
		cachorroRepository.save(new Cachorro(null, "Rex", "Pitbull", "Grande", 4));
		
		RestAssured
		.given()
		.when()
		.get("/v1/cachorros")
		.then()
		.body("nome", Matchers.hasItems("Bob", "Rex"))
		.body("raca", Matchers.hasItems("Poodle", "Pitbull"))
		.body("porte", Matchers.hasItems("Médio", "Grande"))
		.body("idade", Matchers.hasItems(15, 4))
		.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarUmCachorro() {
		Cachorro cachorro = new Cachorro(null,"Son Goku","Sayajin Dog","Grande",4);
		cachorroRepository.save(cachorro);
		RestAssured
		.when()
		.get("/v1/cachorros/{id}",cachorro.getId())
		.then()
		.assertThat()
		.statusCode(HttpStatus.OK.value())
		.body("id",Matchers.equalTo(cachorro.getId().intValue())) 
			.body("nome", Matchers.equalTo(cachorro.getNome()))
			.body("raca", Matchers.equalTo(cachorro.getRaca()))
			.body("porte", Matchers.equalTo(cachorro.getPorte()))
			.body("idade", Matchers.equalTo(cachorro.getIdade().intValue()));
		
	}
	
	@Test
	public void deveRetornarErroAoConsultarCachorroInexistente() {
		
		RestAssured
		.when()
		.get("/v1/cachorros/{id}",1903)
		.then()
		.assertThat()
		.statusCode(HttpStatus.NOT_FOUND.value())
		.body("mensagem",Matchers.equalTo("Não exite chachorro com esse id")); 
			
	}

}
