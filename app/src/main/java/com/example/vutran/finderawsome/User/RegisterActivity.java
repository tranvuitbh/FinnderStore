package com.example.vutran.finderawsome.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vutran.finderawsome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by VuTran on 5/25/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    //Define
    private EditText editTextEmail, editTextPassword;
    private Button buttonRegister;
    private TextView textViewLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        // Set On Click
        textViewLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void AnhXa() {
        editTextEmail       = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword    = (EditText) findViewById(R.id.editTextPassword);
        buttonRegister      = (Button) findViewById(R.id.buttonRegister);
        textViewLogin       = (TextView) findViewById(R.id.textViewLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBarRegister);
    }
    
    @Override
    public void onClick(View view) {
        if(view == textViewLogin){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        if(view == buttonRegister){
            registerUser();
        }
    }

    private void registerUser() {
        String email    = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this, "Please enter email or password", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Failure to Register", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
