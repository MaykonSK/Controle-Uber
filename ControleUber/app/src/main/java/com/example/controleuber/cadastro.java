package com.example.controleuber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cadastro extends AppCompatActivity {

    FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private EditText cadastroEmail;
    private EditText cadastroSenha;
    private EditText nome;
    private TextView mensagemErro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        setTitle("Cadastro");

        cadastroEmail = findViewById(R.id.cadastroEmail);
        cadastroSenha = findViewById(R.id.cadastroSenha);
        mensagemErro = findViewById(R.id.mensagemErro);
        nome = findViewById(R.id.nome);
    }

    public void cadastrar(View view) {

        if (cadastroSenha.length() == 0 || cadastroEmail.length() == 0) {
            mensagemErro.setText("Você precisa digitar um e-mail e senha para cadastrar-se.");
        } else {

            final String email = cadastroEmail.getText().toString();
            String senha = cadastroSenha.getText().toString();
            final String txtnome = nome.getText().toString();
            usuario.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(cadastro.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // converter e-mail para a criptgrafia 64
                        String idUsuario = Base64Custom.codificarBase64(email);

                        // obter dados do usuario
                        Usuarios salvarUsuario = new Usuarios();
                        salvarUsuario.setEmail(email);
                        salvarUsuario.setNome(txtnome);
                        salvarUsuario.setSaldoLiquido(0.00);
                        salvarUsuario.setSaldoBruto(0.00);

                        // salvar no firebase
                        DatabaseReference usuarios = referencia.child("usuarios");
                        usuarios.child(idUsuario).setValue(salvarUsuario);

                        // mostrar mensagem na tela e seguir para a proxima pagina
                        Toast.makeText(cadastro.this, "Cadastro efetuado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), Principal.class);
                        startActivity(intent);
                        cadastro.this.finish();
                    } else {
                        try {
                            throw task.getException();
                        }catch (FirebaseAuthWeakPasswordException e) {
                            mensagemErro.setText("Digite uma senha mais forte.");
                        }catch (FirebaseAuthInvalidCredentialsException e){
                            mensagemErro.setText("Por favor, digite um e-mail valido.");
                        }catch (FirebaseAuthUserCollisionException e){
                            mensagemErro.setText("Essa conta já foi cadastrada.");
                        }catch (Exception e){
                            mensagemErro.setText("Erro inesperado.");
                        }
                    }
                }
            });
        }
    }
}
