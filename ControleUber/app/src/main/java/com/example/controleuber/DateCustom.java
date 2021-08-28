package com.example.controleuber;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual() {
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
        String dataString = simpleDateFormat.format(data);
        return  dataString;
    }

    public static String mesAnoDataEscolhida(String data) {
        // quebrar a data em pedaços
        String retornoData[] = data.split("/");
        String dia = retornoData[0]; // dia 19
        String mes = retornoData[1]; // mes 9
        String ano = retornoData[2]; // ano 2019

        // retornar apenas o mes e o ano
        String mesAno = mes + ano;
        return mesAno;
    }

}
