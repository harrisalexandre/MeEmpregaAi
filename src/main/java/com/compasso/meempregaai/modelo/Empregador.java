package com.compasso.meempregaai.modelo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empregador")
public class Empregador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private String empresa;
	private String cnpj;
	@ManyToMany
	@JoinTable(
			name = "empregador_empregado",
			joinColumns = @JoinColumn(name = "empregador_id"),
			inverseJoinColumns = @JoinColumn(name = "empregado_id")
	)
	private List<Empregado> empregados = new ArrayList<>();

	public Empregador(String nome, String empresa, String cnpj) {
		this.nome = nome;
		this.empresa = empresa;
		this.cnpj = cnpj;
	}

	public Empregador() {
	}

	//GETTERS AND SETTERS
	//SEM GET E SET DE ID

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Empregado> getEmpregados() {return empregados;}

	public void setEmpregados(List<Empregado> empregados) {this.empregados = empregados;}
}
