package com.sandyzfeaklab.breakdown_app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextInputEditText email,password;
    ProgressBar progressBar;
    ImageView succss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        email=findViewById(R.id.login_username);
        password=findViewById(R.id.login_password_editText);
        progressBar= findViewById(R.id.progress_bar_login);
        succss=findViewById(R.id.sucess);

    }

    public void callForgetPassword(View view) {


    }

    public void letTheUserLoggedIn(View view) {

        progressBar.setVisibility(View.VISIBLE);

            signIn(email.getText().toString(),password.getText().toString());

    }

    private void takeMeInside() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);



        startActivity(intent);


    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }



        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressBar.setVisibility(View.GONE);
                            succss.setVisibility(View.VISIBLE);


                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.getDisplayName()!=null){

                                takeMeInside();


                            }else
                            {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("users").document(user.getUid());


                                Intent intent = new Intent(getApplicationContext(), SaveUserInfo.class);



                                startActivity(intent);

                            }




                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "Authentication failed.",Toast.LENGTH_LONG).show();
                        }


                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email1 = email.getText().toString();
        if (TextUtils.isEmpty(email1)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        String password1 = password.getText().toString();
        if (TextUtils.isEmpty(password1)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){


            takeMeInside();
            finish();

        }
    }
}