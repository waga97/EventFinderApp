package com.example.arshad.uea;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHome extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private Toolbar studentToolbar;
    private BottomNavigationView mainbottomNav;

    private StudentViewFragment studentViewFragment;
    private StudentMyFragment studentMyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String RegisteredUserID = currentUser.getUid();

        firebaseFirestore.collection("Users").document(RegisteredUserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                String userName = task.getResult().getString("name");

                studentToolbar = (Toolbar) findViewById(R.id.student_toolbar);
                setSupportActionBar(studentToolbar);
                getSupportActionBar().setTitle(userName);
            }

        });

        // FRAGMENTS
        mainbottomNav = findViewById(R.id.bottom_nav);
        studentViewFragment = new StudentViewFragment();
        studentMyFragment = new StudentMyFragment();
        initializeFragment();
        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_view:

                        replaceFragment(studentViewFragment);
                        return true;

                    case R.id.action_my:

                        replaceFragment(studentMyFragment);
                        return true;

                    default:
                        return false;


                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {

            sendToMain();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.student_logout:
                logout();
                Toast.makeText(StudentHome.this, "Successfully sign out" , Toast.LENGTH_LONG).show();
                return true;

            case R.id.student_scorun:
                sendToScorun();
                return true;

            default:
                return false;

        }

    }

    private void logout(){


        firebaseAuth.signOut();
        sendToMain();
    }

    private void  sendToMain (){

        Intent logoutintent = new Intent(StudentHome.this, MainActivity.class);
        startActivity(logoutintent);
        finish();
    }

    private void  sendToScorun (){

        Intent scorunintent = new Intent(StudentHome.this, StudentScorun.class);
        startActivity(scorunintent);
        finish();
    }

    private void replaceFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();


    }

    private void initializeFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.main_container, studentViewFragment);
        fragmentTransaction.add(R.id.main_container, studentMyFragment);


        fragmentTransaction.hide(studentMyFragment);


        fragmentTransaction.commit();

    }
}
