package com.example.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class gridChecker extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grid_checker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.up1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound4);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();
                TextView ab = findViewById(R.id.count1);
                Integer a =Integer.parseInt(ab.getText().toString())+1;
                ab.setText(a.toString());
            }
        });
        findViewById(R.id.up2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound4);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();
                TextView ab = findViewById(R.id.count2);
                Integer a =Integer.parseInt(ab.getText().toString())+1;
                ab.setText(a.toString());
            }
        });
        findViewById(R.id.down1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound4);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();
                TextView ab = findViewById(R.id.count1);
                Integer a =Integer.parseInt(ab.getText().toString())-1;
                if(a<0){
                    a=0;
                }
                ab.setText(a.toString());
            }
        });
        findViewById(R.id.down2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound4);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();
                TextView ab = findViewById(R.id.count2);
                Integer a =Integer.parseInt(ab.getText().toString())-1;
                if(a<0){
                    a=0;
                }
                ab.setText(a.toString());
            }
        });
        findViewById(R.id.up3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound4);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();
                TextView ab = findViewById(R.id.matchCount);
                Integer a =Integer.parseInt(ab.getText().toString())+1;
                ab.setText(a.toString());
            }
        });
        findViewById(R.id.down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound4);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();
                TextView ab = findViewById(R.id.matchCount);
                Integer a =Integer.parseInt(ab.getText().toString())-1;
                if(a<1){
                    a=1;
                }
                ab.setText(a.toString());
            }
        });
        CheckBox ab = findViewById(R.id.checkBox);
        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound4);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();
            }
        });
        Button start = findViewById(R.id.startGrid);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound5);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();
                CheckBox ifTimed = findViewById(R.id.checkBox);
                EditText timerBox = findViewById(R.id.timer);
                String timex = timerBox.getText().toString().trim();
                TextView count1 = findViewById(R.id.count1);
                TextView count2 = findViewById(R.id.count2);
                TextView count3 = findViewById(R.id.matchCount);
                Integer c1,c2,c3;
                c3 = Integer.parseInt(count3.getText().toString());
                c1 = Integer.parseInt(count1.getText().toString());
                c2 = Integer.parseInt(count2.getText().toString());
                if(c1==0)
                    c1=5;
                if(c2==0)
                    c2=5;
                if(c3==0)
                    c3=1;
                Integer time;
                if(timex.isEmpty()){
                    time = 20;
                }
                else{
                    time =Integer.parseInt(timex);
                }
                Intent gameStart = new Intent(gridChecker.this,MainActivity.class);
                if(ifTimed.isChecked()) {
                    gameStart.putExtra("ifTime",1);
                    gameStart.putExtra("time",time);
                }
                else{
                    gameStart.putExtra("IfTime",0);
                    gameStart.putExtra("time",0);
                }
                gameStart.putExtra("player1",getIntent().getStringExtra("player1"));
                gameStart.putExtra("player2",getIntent().getStringExtra("player2"));
                gameStart.putExtra("row",c1);
                gameStart.putExtra("column",c2);
                gameStart.putExtra("matches",c3);
                gameStart.putExtra("Highscore",0);
                startActivity(gameStart);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }
}