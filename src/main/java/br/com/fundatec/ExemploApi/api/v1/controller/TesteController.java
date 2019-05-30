package br.com.fundatec.ExemploApi.api.v1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {
	
	@GetMapping("/konbanwa")
	public ResponseEntity<String> meuPrimeiroTeste() {
		return ResponseEntity.ok("Konbanwa");
	}
}
