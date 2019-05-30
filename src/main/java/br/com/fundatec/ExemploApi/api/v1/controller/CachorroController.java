package br.com.fundatec.ExemploApi.api.v1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fundatec.ExemploApi.api.v1.dto.CachorroOutputDto;
import br.com.fundatec.ExemploApi.api.v1.dto.CachorroInputDto;
import br.com.fundatec.ExemploApi.entity.Cachorro;
import br.com.fundatec.ExemploApi.mapper.CachorroMapper;
import br.com.fundatec.ExemploApi.service.CachorroService;

@RestController
public class CachorroController {
	
	private CachorroService cachorroService;
	private CachorroMapper cachorroMapper;
	
	
	public CachorroController(CachorroService cachorroService, CachorroMapper cachorroMapper) {
		this.cachorroService = cachorroService;
		this.cachorroMapper = cachorroMapper;
	}


	@GetMapping("/v1/cachorros")
	public ResponseEntity<List<CachorroOutputDto>> getCachorros() {
//		List<CachorroDto> listaCachorro = new ArrayList<>();
//		listaCachorro.add(new CachorroDto("Bob", "Poodle", "M�dio", 15));
//		listaCachorro.add(new CachorroDto("Goku", "Vira-Lata", "Grande", 3));
//		listaCachorro.add(new CachorroDto("Rex", "Pitbull", "Grande", 4));
//		listaCachorro.add(new CachorroDto("Bilu", "Salsichinha", "Pequeno", 2));
//		listaCachorro.add(new CachorroDto("Amarelo", "Golden Retriever", "Grande", 1));
		
		
		List<Cachorro> listaCachorro = cachorroService.listarTodos();
		List<CachorroOutputDto> listaCachorroDto = cachorroMapper.mapearListaCachorroOutputDto(listaCachorro);
		
		
		
		return ResponseEntity.status(HttpStatus.OK).body(listaCachorroDto);
		
	}
	@PostMapping("/v1/cachorros")
	public ResponseEntity<CachorroOutputDto> incluirCachorro(@RequestBody CachorroInputDto cachorroInputDto){
		Cachorro cachorro=cachorroMapper.mapearCachorro(cachorroInputDto);
		cachorro = cachorroService.incluir(cachorro);
		CachorroOutputDto cachorroOutputDto = cachorroMapper.mapearCachorroOutputDto(cachorro);
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cachorroOutputDto);
	}

	

}
