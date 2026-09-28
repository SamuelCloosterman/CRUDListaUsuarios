package com.lunaltas.ListaUsuarios.model;

public class Animal {

    private Long id;
    private String nome;
    private String raca;
    private int idade;
    private String nomeCientifico;

    public Animal() {
    }

    public Animal(Long id, String nome, String raca, int idade, String nomeCientifico) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.nomeCientifico = nomeCientifico;
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

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }


}