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

import com.example.arshad.uea.Organizer.OrganizerCreate;
import com.example.arshad.uea.Organizer.OrganizerHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Toolbar resetTb;

    private EditText emailEt;
    private Button resetBtn;
    private ProgressBar resetPb;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();

        resetTb = findViewById(R.id.forgotpass_toolbar);
        setSupportActionBar(resetTb);
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailEt = findViewById(R.id.email_et);
        resetBtn = findViewById(R.id.reset_btn);
        resetPb = findViewById(R.id.reset_pb);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEt.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    emailEt.setError ("Email Required");
                    emailEt.requestFocus ();
                    return;

                }else {

                    resetPb.setVisibility(View.VISIBLE);

                    firebaseAuth.sendPasswordResetEmail(email)

                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPasswordActivity.this, "Please check your email. We have sent you instructions to reset your password", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ForgotPasswordActivity.this, "Error : ", Toast.LENGTH_SHORT).show();
                                    }

                                    resetPb.setVisibility(View.INVISIBLE);
                                }
                            });


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
                Intent backIntent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                startActivity(backIntent);
                return true;
        }

    }
}
