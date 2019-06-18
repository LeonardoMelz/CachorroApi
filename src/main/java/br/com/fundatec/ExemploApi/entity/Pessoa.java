package br.com.fundatec.ExemploApi.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

@Entity
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30, nullable = false)
	private String nome;

	@Min(value = 0)
	@Column(nullable = false)
	private Integer idade;

	@OneToMany(mappedBy = "pessoa")
	private List<Cachorro> cachorro;

	public Pessoa() {
		
	}
	
	
	public Pessoa(Long id, String nome, @Min(0) Integer idade) {
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.cachorro = cachorro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

}
