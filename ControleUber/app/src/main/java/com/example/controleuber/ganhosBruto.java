package com.example.controleuber;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ganhosBruto extends AppCompatActivity {

    private EditText ganhosBrutoUber;
    private EditText ganhosBruto99;
    private EditText campoData;
    private TextView mensagemErro;
    private EditText gastoCombustivel;
    private EditText gastoComida;
    private EditText gastoLavagem;
    private EditText gastoAluguel;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth Usuario = FirebaseAuth.getInstance();
    FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    DatabaseReference firebaseref = ConfiguracaoFirebase.getFirebaseDatabase();
    private Double ganhosLiquidoTotal;
    private Double saldoTotalBruto;
    private Double saldoTotalLiquido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganhos_bruto);

        ganhosBrutoUber = findViewById(R.id.ganhosBrutoUber);
        ganhosBruto99 = findViewById(R.id.ganhosBruto99);
        campoData = findViewById(R.id.campoData);
        mensagemErro = findViewById(R.id.mensagemErro);
        gastoCombustivel = findViewById(R.id.gastoCombustivel);
        gastoComida = findViewById(R.id.gastoComida);
        gastoLavagem = findViewById(R.id.gastoLavagem);
        gastoAluguel = findViewById(R.id.gastoAluguel);
        recuperarDados();



        //preenche o campo data com o date atual
        campoData.setText(DateCustom.dataAtual());
    }

    public void salvarGanhos(View view) {
        if (ganhosBrutoUber.length() == 0 || campoData.length() == 0) {
            mensagemErro.setText("Você precisa preencher o campo ganho bruto Uber.");
            mensagemErro.setTextColor(Color.RED);
        } else if (ganhosBruto99.length() == 0 & gastoCombustivel.length() == 0 & gastoAluguel.length() == 0 & gastoComida.length() == 0 & gastoLavagem.length() == 0) {
            String num = "0";
            ganhosBruto99.setText(num);
            gastoCombustivel.setText(num);
            gastoAluguel.setText(num);
            gastoComida.setText(num);
            gastoLavagem.setText(num);
            mensagemErro.setText("Correção efetuada! Pressione salvar novamente.");
            mensagemErro.setTextColor(Color.parseColor("#FF00FF"));
        } else if (gastoCombustivel.length() == 0 & gastoAluguel.length() == 0 & gastoComida.length() == 0 & gastoLavagem.length() == 0) {
            String num = "0";
            gastoCombustivel.setText(num);
            gastoAluguel.setText(num);
            gastoComida.setText(num);
            gastoLavagem.setText(num);
            mensagemErro.setText("Correção efetuada! Pressione salvar novamente.");
            mensagemErro.setTextColor(Color.parseColor("#FF00FF"));
        } else if (gastoAluguel.length() == 0 & gastoComida.length() == 0 & gastoLavagem.length() == 0) {
            String num = "0";
            gastoAluguel.setText(num);
            gastoComida.setText(num);
            gastoLavagem.setText(num);
            mensagemErro.setText("Correção efetuada! Pressione salvar novamente.");
            mensagemErro.setTextColor(Color.parseColor("#FF00FF"));
        } else if (gastoComida.length() == 0 & gastoLavagem.length() == 0) {
            String num = "0";
            gastoComida.setText(num);
            gastoLavagem.setText(num);
            mensagemErro.setText("Correção efetuada! Pressione salvar novamente.");
            mensagemErro.setTextColor(Color.parseColor("#FF00FF"));
        } else if (gastoComida.length() == 0) {
            String num = "0";
            gastoComida.setText(num);
            mensagemErro.setText("Correção efetuada! Pressione salvar novamente.");
            mensagemErro.setTextColor(Color.parseColor("#FF00FF"));
        } else if (gastoLavagem.length() == 0) {
            String num = "0";
            gastoLavagem.setText(num);
            mensagemErro.setText("Correção efetuada! Pressione salvar novamente.");
            mensagemErro.setTextColor(Color.parseColor("#FF00FF"));
        } else {
            // converter para string
            mensagemErro.setText("Salvo com sucesso!");
            mensagemErro.setTextColor(Color.parseColor("#228B22"));
            String txtganhosBrutoUber = ganhosBrutoUber.getText().toString();
            String txtganhoBruto99 = ganhosBruto99.getText().toString();
            String txtgastoCombustivel = gastoCombustivel.getText().toString();
            String txtgastoComida = gastoComida.getText().toString();
            String txtgastoLavagem = gastoLavagem.getText().toString();
            String txtgastoAluguel = gastoAluguel.getText().toString();
            // converter para double
            Double nGanhosBrutoUber = Double.parseDouble(txtganhosBrutoUber);
            Double nGanhosBruto99 = Double.parseDouble(txtganhoBruto99);
            Double ngastoCombustivel = Double.parseDouble(txtgastoCombustivel);
            Double ngastoComida = Double.parseDouble(txtgastoComida);
            Double ngastoLavagem = Double.parseDouble(txtgastoLavagem);
            Double ngastoAluguel = Double.parseDouble(txtgastoAluguel);

            Double ganhoBrutoTotalUber99 = nGanhosBrutoUber + nGanhosBruto99;
            ganhosLiquidoTotal = ganhoBrutoTotalUber99 - ngastoCombustivel - ngastoComida - ngastoLavagem - ngastoAluguel;

            String data = campoData.getText().toString();
            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setGanhoBruto(ganhoBrutoTotalUber99);
            movimentacao.setGanhoLiquido(ganhosLiquidoTotal);
            movimentacao.setGastoCombustivel(ngastoCombustivel);
            movimentacao.setGastoComida(ngastoComida);
            movimentacao.setGastoLavagem(ngastoLavagem);
            movimentacao.setGastoAluguel(ngastoAluguel);
            movimentacao.setData(data);
            movimentacao.salvar(data);

            FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
            DatabaseReference reference = referencia.child("usuarios");
            //reference.child(idUsuario).child("saldoLiquido").setValue(recuperarSaldo);
            //reference.child(idUsuario).child("saldoBruto").setValue(nGanhosBruto);

            Double saldoAtualizadoLiquido = saldoTotalLiquido + ganhosLiquidoTotal;
            atualizarSaldoLiquido(saldoAtualizadoLiquido);

            Double saldoAtualizadoBruto = saldoTotalBruto + nGanhosBrutoUber;
            atualizarSaldoBruto(saldoAtualizadoBruto);

            Toast.makeText(ganhosBruto.this, "Dados salvos com sucesso", Toast.LENGTH_LONG).show();
        }

    }
    public void recuperarDados(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUsuario );
        DatabaseReference usuarioRef = firebaseref.child("usuarios").child( idUsuario );

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuarios usuario = dataSnapshot.getValue( Usuarios.class );
                saldoTotalBruto = usuario.getSaldoBruto();
                saldoTotalLiquido = usuario.getSaldoLiquido();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void atualizarSaldoLiquido(Double saldoLiquido){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUsuario );
        DatabaseReference usuarioRef = firebaseref.child("usuarios").child( idUsuario );

        usuarioRef.child("saldoLiquido").setValue(saldoLiquido);

    }
    public void atualizarSaldoBruto(Double saldoBruto){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUsuario );
        DatabaseReference usuarioRef = firebaseref.child("usuarios").child( idUsuario );

        usuarioRef.child("saldoBruto").setValue(saldoBruto);

    }

}
