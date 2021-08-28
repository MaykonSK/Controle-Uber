package com.example.controleuber;

public class Usuarios {
    private String nome;
    private String email;
    private String senha;
    private Double saldoBruto;
    private Double saldoLiquido;

    public Usuarios() {
    }

    public Double getSaldoBruto() {
        return saldoBruto;
    }

    public void setSaldoBruto(Double saldoBruto) {
        this.saldoBruto = saldoBruto;
    }

    public Double getSaldoLiquido() {
        return saldoLiquido;
    }

    public void setSaldoLiquido(Double saldoLiquido) {
        this.saldoLiquido = saldoLiquido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
