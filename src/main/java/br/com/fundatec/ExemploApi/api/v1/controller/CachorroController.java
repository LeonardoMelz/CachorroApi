package br.com.fundatec.ExemploApi.api.v1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fundatec.ExemploApi.api.v1.dto.CachorroOutputDto;
import br.com.fundatec.ExemploApi.api.v1.dto.ErroDto;
import br.com.fundatec.ExemploApi.api.v1.dto.CachorroInputDto;
import br.com.fundatec.ExemploApi.entity.Cachorro;
import br.com.fundatec.ExemploApi.entity.Pessoa;
import br.com.fundatec.ExemploApi.mapper.CachorroMapper;
import br.com.fundatec.ExemploApi.service.CachorroService;
import br.com.fundatec.ExemploApi.service.PessoaService;

@RestController
public class CachorroController {

	private CachorroService cachorroService;
	private CachorroMapper cachorroMapper;
	private PessoaService pessoaService;

	

	public CachorroController(CachorroService cachorroService, CachorroMapper cachorroMapper,
			PessoaService pessoaService) {
		this.cachorroService = cachorroService;
		this.cachorroMapper = cachorroMapper;
		this.pessoaService = pessoaService;
	}

	@GetMapping("/v1/cachorros")
	public ResponseEntity<List<CachorroOutputDto>> getCachorros() {
		List<Cachorro> listaCachorro = cachorroService.listarTodos();
		List<CachorroOutputDto> listaCachorroDto = cachorroMapper.mapearListaCachorroOutputDto(listaCachorro);
		return ResponseEntity.ok(listaCachorroDto);

	}

	@GetMapping("/v1/cachorros/{id}")
	public ResponseEntity<?> consultarCachorro(@PathVariable Long id) {
		try {
			Cachorro cachorro = cachorroService.consultar(id);
			CachorroOutputDto cachorroOutputDto = CachorroMapper.mapearCachorroOutputDto(cachorro);
			return ResponseEntity.ok(cachorroOutputDto);
		} catch (IllegalArgumentException e) {
			ErroDto erroDto = new ErroDto(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDto);
		}
	}

	@PostMapping("/v1/cachorros")
	public ResponseEntity<?> incluirCachorro(@Valid @RequestBody CachorroInputDto cachorroInputDto) {

		try {
			Cachorro cachorro = cachorroMapper.mapearCachorro(cachorroInputDto);
			if (cachorroInputDto.getIdPessoa() != null) {
			Pessoa pessoa = pessoaService.consultar(cachorroInputDto.getIdPessoa());
			cachorro.setPessoa(pessoa);
			}
			cachorro = cachorroService.salvar(cachorro);
			CachorroOutputDto cachorroOutputDto = cachorroMapper.mapearCachorroOutputDto(cachorro);
			return ResponseEntity.status(HttpStatus.CREATED).body(cachorroOutputDto);
		} catch (IllegalArgumentException e) {
			ErroDto erroDto = new ErroDto(e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(erroDto);
		} catch (Exception e) {
			ErroDto erroDto = new ErroDto(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroDto);
		}

	}

	@PutMapping("/v1/cachorros/{id}")
	public ResponseEntity<?> AlterarCachorro(@PathVariable Long id, @RequestBody CachorroInputDto cachorroInputDto) {
		Cachorro cachorro = cachorroMapper.mapearCachorro(cachorroInputDto);
		cachorro.setId(id);
		cachorro = cachorroService.salvar(cachorro);
		CachorroOutputDto cachorroOutputDto = cachorroMapper.mapearCachorroOutputDto(cachorro);
		return ResponseEntity.ok(cachorroOutputDto);

	}

	@DeleteMapping("v1/cachorros/{id}")
	public ResponseEntity<?> ExcluirCachorro(@PathVariable Long id) {
		try {
			cachorroService.deletar(id);
			return ResponseEntity.ok().build();

		} catch (IllegalArgumentException e) {
			ErroDto erroDto = new ErroDto(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDto);
		} catch (Exception e) {
			ErroDto erroDto = new ErroDto(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroDto);
		}
	}
}
