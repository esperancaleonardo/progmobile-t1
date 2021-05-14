package com.example.manager;

public class Aluno {

    private final String nome, email, cpf, telefone;
    private final Integer id_curso;

    public Aluno(String _nome, String _email, String _cpf, String _telefone, Integer _id_curso){
        this.nome = _nome;
        this.cpf = _cpf;
        this.email = _email;
        this.telefone = _telefone;
        this.id_curso = _id_curso;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public Integer getCurso() {
        return id_curso;
    }
}
