package com.example.controleuber;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Movimentacao {
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private String data;
    private Double ganhoBruto;
    private Double ganhoLiquido;
    private Double gastoCombustivel;
    private Double gastoComida;
    private Double gastoLavagem;
    private Double gastoAluguel;
    private String key;

    /*public Movimentacao() {
    } */

    public void salvar (String dataEscolhida) {
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64( autenticacao.getCurrentUser().getEmail());
        String mesAno = DateCustom.mesAnoDataEscolhida(dataEscolhida);
        DatabaseReference reference = referencia.child("movimentacao");
        reference.child(idUsuario).child(mesAno).push().setValue(this);

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getGanhoBruto() {
        return ganhoBruto;
    }

    public void setGanhoBruto(Double ganhoBruto) {
        this.ganhoBruto = ganhoBruto;
    }

    public Double getGanhoLiquido() {
        return ganhoLiquido;
    }

    public void setGanhoLiquido(Double ganhoLiquido) {
        this.ganhoLiquido = ganhoLiquido;
    }

    public Double getGastoCombustivel() {
        return gastoCombustivel;
    }

    public void setGastoCombustivel(Double gastoCombustivel) {
        this.gastoCombustivel = gastoCombustivel;
    }

    public Double getGastoComida() {
        return gastoComida;
    }

    public void setGastoComida(Double gastoComida) {
        this.gastoComida = gastoComida;
    }

    public Double getGastoLavagem() {
        return gastoLavagem;
    }

    public void setGastoLavagem(Double gastoLavagem) {
        this.gastoLavagem = gastoLavagem;
    }

    public Double getGastoAluguel() {
        return gastoAluguel;
    }

    public void setGastoAluguel(Double gastoAluguel) {
        this.gastoAluguel = gastoAluguel;
    }
}

