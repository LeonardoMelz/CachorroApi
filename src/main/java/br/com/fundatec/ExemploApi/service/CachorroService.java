package br.com.fundatec.ExemploApi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fundatec.ExemploApi.entity.Cachorro;
import br.com.fundatec.ExemploApi.repository.CachorroRepository;

@Service
public class CachorroService {

	private CachorroRepository cachorroRepository;
	
	public CachorroService(CachorroRepository cachorroRepository) {
		this.cachorroRepository = cachorroRepository;
	}

	public List<Cachorro> listarTodos() {
		return (List<Cachorro>) cachorroRepository.findAll();
	}
	
	public Cachorro incluir(Cachorro cachorro) {	
		
		
		
		return cachorroRepository.save(cachorro);
		
		
		
	}

	
	
}
