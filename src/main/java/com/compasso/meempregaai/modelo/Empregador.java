package com.compasso.meempregaai.modelo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empregador")
public class Empregador extends Usuario{

	private String empresa;
	private String cnpj;
	@OneToMany(mappedBy = "empregador")
	private List<Contrato> contratos = new ArrayList<>();

	public Empregador(String nome, String empresa, String cnpj, String email, String senha) {
		this.setNome(nome);
		this.setEmail(email);
		this.setSenha(senha);
		this.empresa = empresa;
		this.cnpj = cnpj;
		this.setAtivo(true);
		this.setTipo(getClass().getSimpleName());
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

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}
}
