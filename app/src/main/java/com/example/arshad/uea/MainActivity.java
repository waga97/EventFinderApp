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
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.arshad.uea.Admin.AdminHome;
import com.example.arshad.uea.Organizer.OrganizerHome;
import com.example.arshad.uea.Student.StudentHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    private EditText emailEt;
    private EditText passwordEt;

    private Toolbar mainToolbar;

    private Button loginBtn;
    private Button signupBtn;

    private ProgressBar loginProg;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("             UNITEN EVENT APPLICATION");





        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();



        emailEt = (EditText) findViewById(R.id.sg_email_et);
        passwordEt = (EditText) findViewById(R.id.sg_password_et);
        loginBtn = (Button) findViewById(R.id.login_btn);
        signupBtn = (Button) findViewById(R.id.signup_btn);
        loginProg = (ProgressBar) findViewById(R.id.login_prog);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                if(!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)) {
                    loginUser(email, password);
                }else{
                    Toast.makeText(MainActivity.this, "Error : Empty inputs are not allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToRegister();

            }
        });
    }



    private void loginUser(final String email, final String password) {

        loginProg.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if(task.isSuccessful()){



                                FirebaseUser currentUser = firebaseAuth.getInstance().getCurrentUser();

                                String RegisteredUserID = currentUser.getUid();

                                firebaseFirestore.collection("Users").document(RegisteredUserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        String userType = task.getResult().getString("type");

                                        if (userType.equals("Student")) {
                                            sendToStudent();
                                        }
                                        else if (userType.equals("Admin")){
                                            sendToAdmin();
                                        } else  {
                                            sendToOrganizer();

                                        }

                                    }
                                });





                        }else{

                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(MainActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();

                        }

                        loginProg.setVisibility(View.INVISIBLE);

                    }

                });


    }

   /* protected void onStart() {
       super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {

            validate();

       }

   }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_about:

                Intent settingsIntent = new Intent(MainActivity.this, About.class);
                startActivity(settingsIntent);
                return true;

            default:
                return false;


        }

    }



    private void sendToOrganizer() {

        Intent intentorganizer = new Intent(MainActivity.this, OrganizerHome.class);
        intentorganizer.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentorganizer);
        finish();

    }

    private void sendToStudent(){
        Intent intentstudent = new Intent(MainActivity.this, StudentHome.class);
        intentstudent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentstudent);
        finish();
    }

    private void sendToAdmin(){
        Intent intentstudent = new Intent(MainActivity.this, AdminHome.class);
        intentstudent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentstudent);
        finish();
    }

    /*private void validate(){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String RegisteredUserID = currentUser.getUid();

        firebaseFirestore.collection("Users").document(RegisteredUserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                String userType = task.getResult().getString("type");

                if(userType.equals("Student")){
                    sendToStudent();
                }else{
                    sendToOrganizer();
                }

            }
        });

    }*/

    private void sendToRegister(){
        Intent intentregister = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intentregister);
        finish();
    }




}
