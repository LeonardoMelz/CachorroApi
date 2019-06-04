package br.com.fundatec.ExemploApi.api.v1.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CachorroInputDto {
	private String nome; 
	private String raca;
	@Pattern(regexp = "^Pequeno$|^Medio$|^Grande$" , message = "Campo porte invalido")
	private String porte;
	private Integer idade;	
	@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Campo cpc inv�lido")
	private String cpc;
	
	
	public String getCpc() {
		return cpc;
	}
	public void setCpc(String cpc) {
		this.cpc = cpc;
	}
	
	@NotBlank(message = "O campo nome n�o foi preenchido")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRaca() {
		return raca;
	}
	public void setRaca(String raca) {
		this.raca = raca;
	}
	public String getPorte() {
		return porte;
	}
	public void setPorte(String porte) {
		this.porte = porte;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}

}
