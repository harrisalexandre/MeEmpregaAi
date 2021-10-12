package com.compasso.meempregaai.modelo;

import com.compasso.meempregaai.controller.dto.AdminDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empregador")
public class Empregador extends Usuario{

	private String empresa;
	private String cnpj;
	@ManyToMany
	@JoinTable(name = "empregador_empregado", joinColumns = @JoinColumn(name = "empregador_id"), inverseJoinColumns = @JoinColumn(name = "empregado_id"))
	private List<Empregado> empregados = new ArrayList<>();

	public Empregador(String nome, String empresa, String cnpj, String email, String senha) {
		this.setNome(nome);
		this.setEmail(email);
		this.setSenha(senha);
		this.empresa = empresa;
		this.cnpj = cnpj;
		this.setTipo("ER ");
	}

	public Empregador() {
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Empregado> getEmpregados() {return empregados;}

	public void setEmpregados(List<Empregado> empregados) {this.empregados = empregados;}

    public Empregador getEmpregador() {return getEmpregador();}
}
