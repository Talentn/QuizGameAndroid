package com.example.questionnaire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Page extends AppCompatActivity {
    EditText mail;
    EditText password;
    Button signIn;
    SignInButton signInGoogle;
    LoginButton signInFacebook;
    TextView signUp;
    TextView forgotPassword;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mail = findViewById(R.id.editTextLoginEmal);
        password = findViewById(R.id.editTextTextPassword);
        signIn = findViewById(R.id.buttonLoginSignin);
        signInGoogle = findViewById(R.id.ButtonLoginGoogleSignin);
        signInFacebook = findViewById(R.id.loginButtonFacebookSignin);
        signUp = findViewById(R.id.textViewLoginSignup);
        forgotPassword = findViewById(R.id.textViewForgetPass);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = mail.getText().toString();
                String userPassword = password.getText().toString();
                signInWithFirebase(userEmail, userPassword);

            }
        });

        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        signInFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login_Page.this, Sign_Up_Page.class);
                startActivity(i);

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login_Page.this, Forgot_Password.class);
                startActivity(i);

            }
        });
    }

      public void signInWithFirebase (String userEmail, String userPassword){

         signIn.setClickable(false);

         auth.signInWithEmailAndPassword(userEmail,userPassword)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent i = new Intent(Login_Page.this,MainActivity.class);
                            startActivity(i);
                            finish();
                            Toast.makeText(Login_Page.this, "Connexion réussie avec succès", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Login_Page.this, "Connexion n'est pas réussie", Toast.LENGTH_SHORT).show();
                        }
                     }
                 });
        }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Intent i = new Intent(Login_Page.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
