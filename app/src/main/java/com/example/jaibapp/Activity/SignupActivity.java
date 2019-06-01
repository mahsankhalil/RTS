package com.example.jaibapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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


public class SignupActivity extends AppCompatActivity {

    public static final int  RC_SIGN_IN = 1;
    private boolean signInFlag = false;
    private boolean signUpFlag = true;



    private FirebaseAuth mAuth;
    private Button btnRegister;
    private TextView txtEmail;
    private TextView txtPassword;
    private Button btnSignIn;
    private Button btnSignUp;
    private TextView lblHeading;
    private TextView errorField;
    private ProgressDialog progressDialog;
    private SignInButton signInButton;
    Intent intent = new Intent(this, MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        btnRegister = findViewById(R.id.signup_button);
        txtEmail = findViewById(R.id.signup_email);
        txtPassword = findViewById(R.id.signup_password);
        btnSignUp = findViewById(R.id.sign_up);
        btnSignIn = findViewById(R.id.sign_in);
        lblHeading = findViewById(R.id.heading);
        errorField = findViewById(R.id.error_field);
        progressDialog = new ProgressDialog(this);
        signInButton = findViewById(R.id.sign_in_button);

        signInButton.setSize(SignInButton.SIZE_STANDARD);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            startActivity(intent);
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInFlag = true;
                signUpFlag = false;
                txtEmail.setText("");
                txtPassword.setText("");
                lblHeading.setText("Signin Here!");
                btnRegister.setText("Signin");
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInFlag = false;
                signUpFlag = true;
                txtEmail.setText("");
                txtPassword.setText("");
                lblHeading.setText("Signup Here!");
                btnRegister.setText("Signup");
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                errorField.setText("");

                if(email.isEmpty() || password.isEmpty()){
                    errorField.setText("Email and Password are required.");
                }

                if(signUpFlag){
                    progressDialog.setMessage("Signing Up");
                    progressDialog.show();
                    doSignUp(email, password);
                    startActivity(intent);
                }
                else if(signInFlag){
                    progressDialog.setMessage("Signing In");
                    progressDialog.show();
                    doSignIn(email, password);
                    startActivity(intent);
                }
            }
        });
    }

    private void doSignIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignIn", "signInWithEmail:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("signIn", "signInWithEmail:failure", task.getException());

                        }
                        progressDialog.dismiss();
                        // ...
                    }
                });
    }
    private void doSignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Intent intent = new Intent(this, MainActivity.class);
                            //startActivity(intent);
                            Log.d("Signup", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignupActivity.this, "Done", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("Signup", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            //updateUI(null);
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            Log.d("SignIn", "onActivityResult: " + resultCode);
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("SignIn", "signInResult:failed code=" + e.getStatusCode());
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            startActivity(intent);
        }
    }
}
