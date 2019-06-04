package com.example.jaibapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaibapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnRegister;
    private TextView txtEmail;
    private TextView txtFullname;
    private TextView txtPassword;
    private TextView txtConfirmPassword;
    private TextView txtPhoneNo;
    private Spinner spinnerGender;
    private ImageView btnClose;


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        btnClose = findViewById(R.id.signup_btnClose);
        btnRegister = findViewById(R.id.signup_btnRegister);
        txtEmail = findViewById(R.id.signup_txtEmail);
        txtPassword = findViewById(R.id.signup_txtPassword);
        txtFullname = findViewById(R.id.signup_txtFullname);
        txtConfirmPassword = findViewById(R.id.signup_txtConfirmPassword);
        txtPhoneNo = findViewById(R.id.signup_txtPhoneNo);

        progressDialog = new ProgressDialog(this);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                String confirmPassword = txtConfirmPassword.getText().toString();

                if(email.trim().isEmpty() || password.trim().isEmpty()){
                    Toast.makeText(SignupActivity.this, "Email and Password are required.", Toast.LENGTH_LONG).show();
                } else if(!password.equals(confirmPassword)){
                    Toast.makeText(SignupActivity.this, "Password and Confirm Password are not same.", Toast.LENGTH_LONG).show();
                }else {
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();

                    doSignUp(email, password);
                }
            }
        });
    }

    private void doSignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            String fullName = txtFullname.getText().toString();
                            String phoneNo = txtPhoneNo.getText().toString();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fullName)
                                    .setPhotoUri(Uri.parse(phoneNo))
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            setResult(RESULT_OK);
                                            finish();
                                        }
                                    });
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
