package br.com.fundatec.ExemploApi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fundatec.ExemploApi.entity.Cachorro;
import br.com.fundatec.ExemploApi.entity.PorteParametro;
import br.com.fundatec.ExemploApi.repository.CachorroRepository;

@Service
public class CachorroService {

	private CachorroRepository cachorroRepository;
	private PorteParametroService porteParametroService;

	public CachorroService(CachorroRepository cachorroRepository, PorteParametroService porteParametroService) {
		this.cachorroRepository = cachorroRepository;
		this.porteParametroService = porteParametroService;
	}

	public List<Cachorro> listar(String nome) {
		return (List<Cachorro>) cachorroRepository.findByNomeContainingIgnoreCase(nome);
	}
	
	public List<Cachorro> listaNomeIdadeMinMax(String nome, Integer idadeMin , Integer idadeMax) {
		return (List<Cachorro>) cachorroRepository.findByNomeAndIdadeBetween(nome, idadeMin, idadeMax);
	}

	public Cachorro salvar(Cachorro cachorro) {
		validarSalvarCachorro(cachorro);
		return cachorroRepository.save(cachorro);

	}

	private void validarSalvarCachorro(Cachorro cachorro) {
		validarPorte(cachorro);

	}

	public void validarPorte(Cachorro cachorro) {
		if (!porteParametroService.porteValido(cachorro.getPorte())) {
			throw new IllegalArgumentException("Campo porte invalido");
		}
	}

	public void deletar(long id) {
		if (cachorroRepository.existsById(id)) {
			cachorroRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("N�o exite chachorro com esse id");
		}
	}

	public Cachorro consultar(Long id) {
		
		return cachorroRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("N�o exite chachorro com esse id"));
	}

}
