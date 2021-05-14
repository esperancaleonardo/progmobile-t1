package com.example.manager;

public class Curso {

    private final String nome;
    private final Integer carga_horaria;

    public Curso(String nome, Integer carga_horaria){
        this.nome = nome;
        this.carga_horaria = carga_horaria;
    }

    public String getNome(){return nome;}

    public Integer getCarga_horaria(){return carga_horaria;}
}
