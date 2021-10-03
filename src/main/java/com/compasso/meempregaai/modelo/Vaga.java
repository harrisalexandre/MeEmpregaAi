package com.compasso.meempregaai.modelo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vaga")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToOne
    private Empregador empregador;
    private String descricao;
    private boolean ativa;
    private long curtidas;
    @ManyToMany
    @JoinTable(name = "vaga_empregado", joinColumns = @JoinColumn(name = "vaga_id"), inverseJoinColumns = @JoinColumn(name = "empregado_id"))
    private List<Empregado> empregados = new ArrayList<>();

    public Vaga(String nome, String descricao, Empregador empregador) {
        this.nome = nome;
        this.empregador = empregador;
        this.descricao = descricao;
        this.curtidas = 0;
        this.ativa = true;
    }

    public Vaga() {
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

    public Empregador getEmpregador() {
        return empregador;
    }

    public void setEmpregador(Empregador empregador) {
        this.empregador = empregador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Empregado> getEmpregados() {
        return empregados;
    }

    public void setEmpregados(List<Empregado> empregados) {
        this.empregados = empregados;
    }

    public long getCurtidas() {return curtidas;}

    public void setCurtidas(long curtidas) {this.curtidas = curtidas;}

    public boolean isAtiva() {return ativa;}

    public void setAtiva(boolean ativa) {this.ativa = ativa;}
}
