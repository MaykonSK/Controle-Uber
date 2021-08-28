package com.example.controleuber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth usuario = FirebaseAuth.getInstance();
    private EditText loginEmail;
    private EditText loginSenha;
    private TextView mensagemErro;

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmail = findViewById(R.id.loginEmail);
        loginSenha = findViewById(R.id.loginSenha);
        mensagemErro = findViewById(R.id.mensagemErro);
    }
    public void irCadastro (View view) {
        Intent intent = new Intent(getApplicationContext(), cadastro.class);
        startActivity(intent);
    }
    public void irLogin (View view) {
        if (loginEmail.length() == 0 || loginSenha.length() == 0) {
            mensagemErro.setText("Digite um e-mail e senha para logar.");
        } else {
            String txtemail = loginEmail.getText().toString();
            String txtsenha = loginSenha.getText().toString();
            usuario.signInWithEmailAndPassword(txtemail, txtsenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Login efetuado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), Principal.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    } else {

                        try {
                            throw task.getException();
                        }catch (FirebaseAuthInvalidUserException e) {
                            mensagemErro.setText("O usuario informado não existe.");
                        }catch (FirebaseAuthInvalidCredentialsException e){
                            mensagemErro.setText("Senha incorreta.");
                        }catch (Exception e) {
                            mensagemErro.setText("E-mail inválido.");
                        }
                    }

                }
            });
        }
    }
    public void verificarUsuarioLogado () {
        if (usuario.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), Principal.class);
            startActivity(intent);
            finish();
        }
    }
}
