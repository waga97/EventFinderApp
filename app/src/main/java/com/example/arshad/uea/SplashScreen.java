package com.example.arshad.uea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

   private long delay = 1000;
   private TimerTask task = new TimerTask(){
       @Override
       public void run(){
           startActivity(new Intent(SplashScreen.this, MainActivity.class));
           finish();

        }
    };

   @Override
    protected  void onCreate (Bundle savedInstanceState){

       Timer timer = new Timer();
       timer.schedule(task,delay);

       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_splash_screen);
   }


}
