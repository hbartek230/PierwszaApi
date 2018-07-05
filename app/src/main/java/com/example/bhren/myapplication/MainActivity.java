package com.example.bhren.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bhren.myapplication.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPassword;
    private Button btnLogin;
    private int counter = 5;
    String WrongInfo;
    String RightInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_users = database.getReference("Users");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Proszę czekać...");
                mDialog.show();
                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Czy użytkownik istnieje
                        if(dataSnapshot.child(etName.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Users users = dataSnapshot.child(etName.getText().toString()).getValue(Users.class);
                            if (users.getPassword().equals(etPassword.getText().toString())) {
                                Toast.makeText(MainActivity.this, "Login poprawny", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Login niepoprawny", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            mDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Użytkownik nie istnieje", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    /*private void login(String userName, String userPassword) {
        if ((userName.equals("admin")) && (userPassword.equals("123"))) {
            RightInfo = "Hasło poprawne. Zalogowano";
            Toast.makeText(MainActivity.this, RightInfo, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
            finish();
        } else {
            counter--;
            WrongInfo = "Błędne hasło. Pozostało prób: " + String.valueOf(counter);
            Toast.makeText(MainActivity.this, WrongInfo, Toast.LENGTH_SHORT).show();
            if (counter == 0) {
                Login.setEnabled(false);
            }
        }*/
    }
}
