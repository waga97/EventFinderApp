package com.example.arshad.uea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arshad.uea.Student.StudentHome;
import com.example.arshad.uea.Student.StudentScorun;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegStudent;
    private Button btnRegOrganizer;
    private Button btnLogin;

    private Toolbar organizercreateToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        organizercreateToolbar = (Toolbar) findViewById(R.id.organizercreate_toolbar);
        setSupportActionBar(organizercreateToolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRegStudent = findViewById(R.id.btn_reg_student);
        btnRegOrganizer = findViewById(R.id.btn_reg_organizer);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToLogin();
            }
        });

        btnRegStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToRegStudent();
            }
        });

        btnRegOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToRegOrganizer();
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
                Intent backIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(backIntent);
                return true;
        }

    }

    private void  sendToRegStudent (){

        Intent loginintent = new Intent(RegisterActivity.this, StudentRegisterActivity.class);
        startActivity(loginintent);
        finish();


    }

    private void  sendToRegOrganizer (){

        Intent loginintent = new Intent(RegisterActivity.this, OrganizerRegisterActivity.class);
        startActivity(loginintent);
        finish();

    }

    private void  sendToLogin (){

        Intent loginintent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(loginintent);
        finish();
    }
}
