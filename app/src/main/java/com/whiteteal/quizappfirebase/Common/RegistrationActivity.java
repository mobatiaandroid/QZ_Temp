package com.whiteteal.quizappfirebase.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.whiteteal.quizappfirebase.Add_Data.Add_Data_Activity;
import com.whiteteal.quizappfirebase.R;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText Name,Email,Password;
    Button Register;
    Context mContext;
    Activity activity;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mContext = this;
        activity = this;

        IniUi();
    }

    private void IniUi() {
        Email = findViewById(R.id.email_edt);
        Name = findViewById(R.id.name_edt);
        Password = findViewById(R.id.password_edt);
        Register = findViewById(R.id.reg_btn);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UploadData();
            }
        });


    }

//    private void UploadData() {
//
//        mAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("FireBaseAuth", "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            if (user != null) {
//                                UserId = user.getUid();
//                            }
//
//                            Map<String,Object> map = new HashMap<>();
//                            map.put("Name",Name.getText().toString());
//                            map.put("Email",Email.getText().toString());
//                            map.put("ID",UserId);
//
//                            db.collection("Accounts")
//                                    .add(map)
//                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                        @Override
//                                        public void onSuccess(DocumentReference documentReference) {
//                                            Toast.makeText(mContext, "Account has been created", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("FireBaseAuth", "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(mContext, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            //updateUI(null);
//                        }
//                    }
//                });
//
//
//
//
//    }

    public void ToLogin(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void AddData(View view) {
        startActivity(new Intent(mContext, AddData.class));
    }
}
