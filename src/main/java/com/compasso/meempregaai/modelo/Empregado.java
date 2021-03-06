package com.compasso.meempregaai.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
@Entity
@Table(name = "empregado")
public class Empregado extends Usuario{

	private String cpf;
	private LocalDate dataNascimento;
	@ManyToMany
	@JoinTable(name = "empregado_curtidas", joinColumns = @JoinColumn(name = "empregado_id"), inverseJoinColumns = @JoinColumn(name = "empregador_id"))
	private List<Empregador> curtidas = new ArrayList<>();
	@ManyToMany(mappedBy = "candidatos")
	private List<Vaga> vagas = new ArrayList<>();

	@OneToMany(mappedBy = "empregado")
	private List<Contrato> contratos = new ArrayList<>();

	@OneToOne
	private Curriculo curriculo;

	public Empregado(String nome, String cpf, LocalDate dataNascimento, String email, String senha) {
		this.setNome(nome);
		this.setEmail(email);
		this.setSenha(senha);
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.setAtivo(true);
		this.setTipo(getClass().getSimpleName());
	}

	public Empregado() {
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Empregador> getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(List<Empregador> curtidas) {
		this.curtidas = curtidas;
	}

	public List<Vaga> getVagas() {
		return vagas;
	}

	public void setVagas(List<Vaga> vagas) {
		this.vagas = vagas;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public Curriculo getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(Curriculo curriculo) {
		this.curriculo = curriculo;
	}
}
