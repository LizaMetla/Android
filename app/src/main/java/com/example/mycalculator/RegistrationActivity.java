package com.example.mycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    TextView btn;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    private EditText inputName, inputEmail, inputPassword, inputConfirmPassword;
    Button button_register;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPatternCaps = "[A-ZА-Я]";
    String passwordPatternSymbols = "[a-zа-я]";
    String passwordPatternNumbers = "[0-9]";
    String noWhiteSpace = "(?=\\s+$)";
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btn = findViewById(R.id.inSystemLogInTextView);
        inputName = findViewById(R.id.nameRegTextField);
        inputEmail = findViewById(R.id.emailRegTextField);
        inputPassword = findViewById(R.id.passwordRegTextField);
        inputConfirmPassword = findViewById(R.id.confirmPasswordRegTextField);

        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(RegistrationActivity.this);

        button_register = findViewById(R.id.registrateButton);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentails();
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    private void checkCrededentails() {
        String username = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if (username.isEmpty() || username.length() < 3 ) {
            showError(inputName, "Your name is not valid!");
        }
//        else if(!username.matches(whiteSace)) {
//            showError(inputName, "White Spaces are not allowed!");
//        }
        else if (email.isEmpty() || !email.trim().matches(emailPattern)) {
            showError(inputEmail, "Email is not valid!");
        }
//        else if(!email.matches(noWhiteSpace)) {
//            showError(inputEmail, "White Spaces are not allowed!");
//        }
        else if (password.isEmpty() || password.length()<7) {
            showError(inputPassword, "Password must be 7 character!");

        } else if (!Pattern.compile(passwordPatternCaps).matcher(password.trim()).find()) {
            showError(inputPassword, "Must contain UPPER CASE symbols!");

        }
        else if (!Pattern.compile(passwordPatternSymbols).matcher(password.trim()).find()) {
            showError(inputPassword, "Must contain LOWER CASE symbols!");

        }
        else if (!Pattern.compile(passwordPatternNumbers).matcher(password.trim()).find()) {
            showError(inputPassword, "Must contain numbers!");
        }
//        else if(!password.matches(noWhiteSpace)) {
//            showError(inputPassword, "White Spaces are not allowed!");
//        }

        else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            showError(inputConfirmPassword, "Password not match!");
        }
        else if(password.toLowerCase().contains(email.split("@")[0].toLowerCase())){
            showError(inputPassword, "Password can't contain first part of email!");
        }
        else {

            //Toast.makeText(getApplicationContext(), "Call Registration Method", Toast.LENGTH_SHORT).show();
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please wait, while checking your credits");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();


            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();

                        mLoadingBar.dismiss();

                        Intent intent = new Intent(RegistrationActivity.this, PostsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);


                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showError(EditText input, String s) {

        input.setError(s);
        input.requestFocus();
    }
}