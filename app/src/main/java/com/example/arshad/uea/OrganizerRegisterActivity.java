package com.example.arshad.uea;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.arshad.uea.Organizer.OrganizerCreate;
import com.example.arshad.uea.Organizer.OrganizerHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;




public class OrganizerRegisterActivity extends AppCompatActivity {



    private Button signupBtn;

    private Toolbar organizercreateToolbar;

    private EditText emailEt;
    private EditText passEt;
    private EditText confirmpassEt;
    private EditText nameEt;

    private ProgressBar organizerregPb;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        organizercreateToolbar = (Toolbar) findViewById(R.id.organizercreate_toolbar);
        setSupportActionBar(organizercreateToolbar);
        getSupportActionBar().setTitle("Organizer Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        emailEt = (EditText) findViewById(R.id.sg_email_et);
        passEt = (EditText) findViewById(R.id.sg_password_et);
        confirmpassEt = (EditText) findViewById(R.id.sg_confirm_password_et);
        nameEt = (EditText) findViewById(R.id.sg_name_et);
        organizerregPb = findViewById(R.id.organizerreg_pb);






        signupBtn = (Button) findViewById(R.id.signup_btn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = emailEt.getText().toString();
                String pass = passEt.getText().toString();
                String confirm_pass = confirmpassEt.getText().toString();
                final String name = nameEt.getText().toString();
                final String type = "Organizer";



                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) & !TextUtils.isEmpty(confirm_pass)){

                    if(pass.equals(confirm_pass)){

                        organizerregPb.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    String RegisteredUserID = currentUser.getUid();

                                    Map<String, String> userMap = new HashMap<>();
                                    userMap.put("name", name);
                                    userMap.put("email", email);
                                    userMap.put("type", type);



                                    firebaseFirestore.collection("Users").document(RegisteredUserID).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                mAuth.getCurrentUser().sendEmailVerification();
                                                mAuth.signOut();
                                                Toast.makeText(OrganizerRegisterActivity.this, "Successfully created. Please check your email to verify your account", Toast.LENGTH_LONG).show();
                                                Intent mainIntent = new Intent(OrganizerRegisterActivity.this, MainActivity.class);
                                                startActivity(mainIntent);
                                                finish();

                                            } else {

                                                String error = task.getException().getMessage();
                                                Toast.makeText(OrganizerRegisterActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();

                                            }

                                            organizerregPb.setVisibility(View.INVISIBLE);

                                        }
                                    });


                                } else {

                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(OrganizerRegisterActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();

                                }
                            }
                        });

                    } else {

                        Toast.makeText(OrganizerRegisterActivity.this, "Confirm Password and Password Field doesn't match.", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.organizer_create_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            default:
                Intent backIntent = new Intent(OrganizerRegisterActivity.this, RegisterActivity.class);
                startActivity(backIntent);
                return true;
        }

    }




    private  void sendToRegister(){

        Intent backintent = new Intent(OrganizerRegisterActivity.this, RegisterActivity.class);
        startActivity(backintent);
        finish();

    }




}
