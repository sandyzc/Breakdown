package com.sandyzfeaklab.breakdown_app;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sandyzfeaklab.breakdown_app.dataModel.User_Info;

import java.util.Objects;

public class SaveUserInfo extends AppCompatActivity {

    TextInputEditText name, newpass, confirmnewwpass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_user_info);
        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.login_username1);
        newpass = findViewById(R.id.new_pass_et);
        confirmnewwpass=findViewById(R.id.new_pass_con_et);

    }

    public void save_user_info(View view) {

        if (!(Objects.requireNonNull(name.getText()).toString().length() < 2)&& Objects.requireNonNull(newpass.getText()).toString().equals(Objects.requireNonNull(confirmnewwpass.getText()).toString())) {


            FirebaseUser user = mAuth.getCurrentUser();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            assert user != null;
            db.collection("users").document(Objects.requireNonNull(user.getEmail())).set(new User_Info(name.getText().toString(),user.getUid()));

            user.updatePassword(confirmnewwpass.getText().toString());

            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString())
                    .build();

            user.updateProfile(request)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            view.setEnabled(true);
                            Toast.makeText(SaveUserInfo.this, "Succesfully updated profile", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            view.setEnabled(true);

                            Log.e(TAG, "onFailure: ", e.getCause());
                        }
                    });

        } else {
            name.setError("Enter Valid Name");
        }


    }
}