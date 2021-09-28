package com.compasso.meempregaai.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "empregado")
public class Empregado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	private long curtidas;
	@ManyToMany(mappedBy = "empregados")
	private List<Vaga> vagas = new ArrayList<>();
	@ManyToMany(mappedBy = "empregados")
	private List<Empregador> empregadores = new ArrayList<>();

	public Empregado(String nome, String cpf, LocalDate dataNascimento) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.curtidas = 0;
	}

	public Empregado() {
	}

	//GETTERS AND SETTERS
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

	public long getCurtidas() {return curtidas;}

	public void setCurtidas(long curtidas) {this.curtidas = curtidas;}
}
