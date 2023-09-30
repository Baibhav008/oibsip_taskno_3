package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int seconds=0;
    boolean running;
    boolean wasRunning;
   // Animation rotation;
   ObjectAnimator rotation;
    ImageView hand,play,pause,stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        if(savedInstanceState!=null)
        {
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
            wasRunning=savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();

        hand=findViewById(R.id.hand);
        play=findViewById(R.id.play);
        pause=findViewById(R.id.pause);
        stop=findViewById(R.id.stop);

        rotation=ObjectAnimator.ofFloat(hand,"rotation",0,360);
       // rotation = ObjectAnimator.ofFloat(hand, "rotation", 0, 360);
        rotation.setDuration(1000);
        rotation.setRepeatCount(ValueAnimator.INFINITE);
        rotation.setRepeatMode(ValueAnimator.RESTART);

        //rotation= AnimationUtils.loadAnimation(this,R.anim.rounding);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!running)
                {
                    //hand.startAnimation(rotation);
                    rotation.start();
                    running=true;
                    play.setEnabled(false);
                }

                else
                {
                    Toast.makeText(MainActivity.this, "already running", Toast.LENGTH_SHORT).show();
                }


            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(running)
                {
                    rotation.pause();
                    //  hand.clearAnimation();
                    running=false;
                    wasRunning=true;
                    play.setEnabled(true);
                }


            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                rotation.end();
                running=false;
                seconds=0;
                play.setEnabled(true);
            }
        });
    }

    private void runTimer()
    {
        TextView time = findViewById(R.id.time);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                int hours = seconds/3600;
                int minute = (seconds%3600)/60;
                int sec = seconds%60;

                String timeT = String.format(Locale.getDefault(),"%d : %02d : %02d",hours,minute,sec);
                time.setText(timeT);

                if(running)
                {
                    seconds++;
                }
                handler.postDelayed(this,1000);


            }
        });

    }


}