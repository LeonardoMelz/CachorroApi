package br.com.fundatec.ExemploApi.integration;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fundatec.ExemploApi.api.v1.dto.CachorroOutputDto;
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

		RestAssured.given().when().get("/v1/cachorros").then().body("nome", Matchers.hasItems("Bob", "Rex"))
				.body("raca", Matchers.hasItems("Poodle", "Pitbull"))
				.body("porte", Matchers.hasItems("Médio", "Grande")).body("idade", Matchers.hasItems(15, 4))
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarUmCachorro() {
		Cachorro cachorro = new Cachorro(null, "Son Goku", "Sayajin Dog", "Grande", 4);
		cachorroRepository.save(cachorro);
		RestAssured.when().get("/v1/cachorros/{id}", cachorro.getId()).then().assertThat()
				.statusCode(HttpStatus.OK.value()).body("id", Matchers.equalTo(cachorro.getId().intValue()))
				.body("nome", Matchers.equalTo(cachorro.getNome())).body("raca", Matchers.equalTo(cachorro.getRaca()))
				.body("porte", Matchers.equalTo(cachorro.getPorte()))
				.body("idade", Matchers.equalTo(cachorro.getIdade().intValue()));

	}

	@Test
	public void deveRetornarErroAoConsultarCachorroInexistente() {

		RestAssured.when().get("/v1/cachorros/{id}", 1903).then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
				.body("mensagem", Matchers.equalTo("Não exite chachorro com esse id"));

	}

	@Test
	public void deveFiltrarCachorroPeloNome() {
		cachorroRepository.save(new Cachorro(null, "Bob", "Poodle", "Médio", 15));
		cachorroRepository.save(new Cachorro(null, "Rex", "Pitbull", "Grande", 4));
		cachorroRepository.save(new Cachorro(null, "Roberto", "Chihuahua", "Pequeno", 10));

		CachorroOutputDto[] resultado = RestAssured.given().when().get("/v1/cachorros/?nome=ob").then().assertThat()
				.statusCode(HttpStatus.OK.value()).extract().as(CachorroOutputDto[].class);

		List<String> nomesEsperados = Arrays.asList("Bob", "Roberto");
		List<String> racasEsperados = Arrays.asList("Poodle", "Chihuahua");
		List<String> portesEsperados = Arrays.asList("Médio", "Pequeno");
		List<Integer> idadeEsperados = Arrays.asList(15, 10);

		for (CachorroOutputDto cachorroOutputDto : resultado) {
			Assert.assertTrue("Não encontrou o nome "+ cachorroOutputDto.getNome()+" na lista de esperados",nomesEsperados.contains(cachorroOutputDto.getNome()));
			Assert.assertTrue(racasEsperados.contains(cachorroOutputDto.getRaca()));
			Assert.assertTrue(portesEsperados.contains(cachorroOutputDto.getPorte()));
			Assert.assertTrue(idadeEsperados.contains(cachorroOutputDto.getIdade()));
		}
		}
		
		@Test
		public void deveFiltrarCachorroPeloNomeIdade() {
			cachorroRepository.save(new Cachorro(null, "Bob", "Poodle", "Médio", 15));
			cachorroRepository.save(new Cachorro(null, "Rex", "Pitbull", "Grande", 4));
			cachorroRepository.save(new Cachorro(null, "Roberto", "Chihuahua", "Pequeno", 10));
	
		CachorroOutputDto[] resultado = RestAssured.given().when().get("/v1/cachorros/exercicio?nome=ob&idadeMin=1&idadeMax=16").then().assertThat()
				.statusCode(HttpStatus.OK.value()).extract().as(CachorroOutputDto[].class);
		
		List<String> nomesEsperados = Arrays.asList("Bob", "Roberto");
		List<String> racasEsperados = Arrays.asList("Poodle", "Chihuahua");
		List<String> portesEsperados = Arrays.asList("Médio", "Pequeno");
		List<Integer> idadeEsperados = Arrays.asList(15, 10);
		
		for (CachorroOutputDto cachorroOutputDto : resultado) {
			Assert.assertTrue(racasEsperados.contains(cachorroOutputDto.getNome()));
			Assert.assertTrue(racasEsperados.contains(cachorroOutputDto.getRaca()));
			Assert.assertTrue(portesEsperados.contains(cachorroOutputDto.getPorte()));
			Assert.assertTrue(idadeEsperados.contains(cachorroOutputDto.getIdade()));
		}

 }
}

