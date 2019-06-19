package br.com.fundatec.ExemploApi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fundatec.ExemploApi.entity.Cachorro;

@Repository
public interface CachorroRepository extends CrudRepository<Cachorro, Long>{

	List<Cachorro> findByNomeContainingIgnoreCase(String nome);
	
}
