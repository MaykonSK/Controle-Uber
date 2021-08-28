package com.example.controleuber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class informacaoConta extends AppCompatActivity {

    FirebaseAuth Usuario = FirebaseAuth.getInstance();
    FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    DatabaseReference firebaseref = ConfiguracaoFirebase.getFirebaseDatabase();
    private Double ganhosLiquido;
    private Double ganhosBruto;
    private String mostrarNome;
    private String mostrarEmail;
    private TextView resultadoNome;
    private TextView resultadoEmail;
    private TextView resultadoBruto;
    private TextView resultadoLiquido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_conta);
        resultadoBruto = findViewById(R.id.resultadoBruto);
        resultadoEmail = findViewById(R.id.resultadoEmail);
        resultadoLiquido = findViewById(R.id.resultadoLiquido);
        resultadoNome = findViewById(R.id.resultadoNome);
        recuperarDados();
    }
    public void recuperarDados () {
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);

        DatabaseReference usuarioRef = firebaseref.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuarios recuperarDados = dataSnapshot.getValue(Usuarios.class);
                ganhosLiquido = recuperarDados.getSaldoLiquido();
                ganhosBruto = recuperarDados.getSaldoBruto();
                mostrarNome = recuperarDados.getNome();
                mostrarEmail = recuperarDados.getEmail();
                resultadoEmail.setText("E-mail: "+mostrarEmail);
                resultadoNome.setText("Nome: "+mostrarNome);
                resultadoBruto.setText("Ganhos Bruto: R$"+String.valueOf(ganhosBruto));
                resultadoLiquido.setText("Ganhos Liquido: R$"+String.valueOf(ganhosLiquido));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void zerarGanhos(View view) {
        Toast.makeText(informacaoConta.this, "Função indisponivel no momento.", Toast.LENGTH_LONG).show();
    }
}
