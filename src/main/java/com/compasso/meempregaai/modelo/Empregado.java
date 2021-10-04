package com.compasso.meempregaai.modelo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
@Entity
@Table(name = "empregado")
public class Empregado extends Usuario{

	private String cpf;
	private LocalDate dataNascimento;
	private long curtidas;
	@ManyToMany(mappedBy = "empregados")
	private List<Vaga> vagas = new ArrayList<>();
	@ManyToMany(mappedBy = "empregados")
	private List<Empregador> empregadores = new ArrayList<>();

	public Empregado(String nome, String cpf, LocalDate dataNascimento, String email, String senha) {
		this.setNome(nome);
		this.setEmail(email);
		this.setSenha(senha);
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.curtidas = 0;
		this.setTipo("EO ");
	}

	public Empregado() {
	}

	//GETTERS AND SETTERS

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
