package com.compasso.meempregaai.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "empregador")
public class Empregador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private String empresa;
	private long cnpj;
	
	
	
	
	//GETTERS AND SETTERS
	//SEM GET E SET DE ID
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public long getCpf() {
		return cnpj;
	}
	public void setCpf(long cpf) {
		this.cnpj = cpf;
	}
	
	
}
