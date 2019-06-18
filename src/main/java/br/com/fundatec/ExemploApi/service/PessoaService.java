package br.com.fundatec.ExemploApi.service;

import org.springframework.stereotype.Service;

import br.com.fundatec.ExemploApi.entity.Pessoa;
import br.com.fundatec.ExemploApi.repository.PessoaRepository;

@Service
public class PessoaService {

	private PessoaRepository pessoaRepository;

	public PessoaService(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}
	
	public Pessoa consultar(Long id) {
		
		return pessoaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Não esvontrou pessoa para esse id "+id));
		
		
	}
	
	
	
}
