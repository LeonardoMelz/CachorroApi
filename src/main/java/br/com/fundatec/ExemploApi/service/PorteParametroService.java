package br.com.fundatec.ExemploApi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fundatec.ExemploApi.entity.PorteParametro;
import br.com.fundatec.ExemploApi.repository.PorteParametroRepository;

@Service
public class PorteParametroService {

	private PorteParametroRepository porteParametroRepository;

	public PorteParametroService(PorteParametroRepository porteParametroRepository) {
		this.porteParametroRepository = porteParametroRepository;

	}

	public boolean porteValido(String porte) {
		List<PorteParametro> listaPorteParametro = (List<PorteParametro>) porteParametroRepository.findAll();

		for (PorteParametro porteParametro : listaPorteParametro) {
			if (porteParametro.getNome().equals(porte)) {

				return true;
			}
		}
		return false;
	}

	

	

	

}
