package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Integer turn=0;

    TableLayout field;
    static ConstraintLayout main;
    static Integer redWon=0;
    static Integer blueWon=0;
    static Integer redCount=0;
    static Integer blueCount=0;
    Integer rowx=3;
    Integer columnx=3;
    ArrayList<ArrayList<Block>> buttonx = new ArrayList<>();
    TextView turnCounter;
    TextView player1;
    TextView player2;
    ProgressBar timer1;
    ProgressBar timer2;
    TextView turnCounter2;
    CountDownTimer countDownTimer1;
    CountDownTimer countDownTimer2;
    long timex1=0;
    long timex2=0;
    Integer highscore;
    static boolean flag=true;

    public void setTurn() {
        this.turn = this.turn+1;

        if((this.turn+getIntent().getIntExtra("LastWon",0))%2==0){
            main.setBackgroundColor(Color.parseColor("#4287F5"));
        }
        else{
            main.setBackgroundColor(Color.parseColor("#FE5F57"));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Game Cancel?");
                builder.setMessage("Do you wish to Proceed");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        };
        Button setgrid = findViewById(R.id.button2);
        setgrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
        main=findViewById(R.id.main);
        int bool = (getIntent().getIntExtra("LastWon",0));
        if ((bool)==3){
            main.setBackgroundColor(ContextCompat.getColor(this, R.color.lightred));
        }
        else{
            main.setBackgroundColor(ContextCompat.getColor(this, R.color.evenLigherBlue));
        }
        turnCounter= findViewById(R.id.turnCo);
        turnCounter2=findViewById(R.id.turnCo2);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        rowx=getIntent().getIntExtra("row",5);
        columnx=getIntent().getIntExtra("column",5);
        int x= getIntent().getIntExtra("ifTime",0);

        String p1 = getIntent().getStringExtra("player1");
        if(p1.isEmpty()){
            p1="Player 1";
        }
        String p2 = getIntent().getStringExtra("player2");
        if(p2.isEmpty()){
            p2="Player 2";
        }
        player1.setText(p1);
        player1.setTextColor(ContextCompat.getColor(this, R.color.evenLigherBlue));
        player2.setText(p2);
        player2.setTextColor(ContextCompat.getColor(this, R.color.lightred));
        field = findViewById(R.id.Field1);
        field.removeAllViews();
        createGrid(field,buttonx);

        if(x!=0){
            ConstraintSet set = new ConstraintSet();
            ConstraintLayout ct1 = findViewById(R.id.constraintLayout2);
            ConstraintLayout ct2 = findViewById(R.id.constraintLayout3);

            LayoutInflater inflater = getLayoutInflater();
            View myLayout = inflater.inflate(R.layout.time_bar, ct1, false);
            myLayout.setId(View.generateViewId());

            ViewGroup.LayoutParams parax= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            myLayout.setLayoutParams(parax);
            myLayout.setRotation(180);
            ct1.addView(myLayout);
            set.clone(ct1);
            set.connect(myLayout.getId(), ConstraintSet.RIGHT, ct1.getId(), ConstraintSet.RIGHT, 5);
            set.applyTo(ct1);
            set.clone(ct1);
            set.connect(myLayout.getId(), ConstraintSet.TOP, ct1.getId(), ConstraintSet.TOP);
            set.clone(ct1);
            set.connect(myLayout.getId(), ConstraintSet.LEFT, ct1.findViewById(R.id.player1).getId(), ConstraintSet.RIGHT);
            set.applyTo(ct1);

            LayoutInflater inflater2 = getLayoutInflater();
            View myLayout2 = inflater2.inflate(R.layout.time_bar, ct2, false);
            myLayout2.setId(View.generateViewId());

            TextView textViewx2 = (TextView) myLayout2.findViewById(R.id.timeText);
            ViewGroup.LayoutParams parax2= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            myLayout2.setLayoutParams(parax2);
            ct2.addView(myLayout2);
            set.clone(ct2);
            set.connect(myLayout2.getId(), ConstraintSet.LEFT, ct2.getId(), ConstraintSet.LEFT, 5);
            set.applyTo(ct2);
            set.clone(ct2);
            set.connect(myLayout2.getId(), ConstraintSet.TOP, ct2.getId(), ConstraintSet.TOP);
            set.clone(ct2);
            set.connect(myLayout2.getId(), ConstraintSet.RIGHT, ct2.findViewById(R.id.player2).getId(), ConstraintSet.LEFT);
            set.applyTo(ct2);
            timer1=ct1.findViewById(R.id.progressBar);
            timer2=ct2.findViewById(R.id.progressBar);
            int timsec=(getIntent().getIntExtra("time",10))*10;
            timer2.setMax(timsec);
            timer2.setProgress(timsec);
            timer1.setMax(timsec);
            timer1.setProgress(timsec);
            long millisec= timsec*100;
            timex1=timex2=millisec;
            if(bool==0){
                startTimer2(millisec,timer2);
                startTimer1(millisec,timer1);
                countDownTimer2.cancel();
            }
            else if(bool==2){
                startTimer2(millisec,timer2);
                startTimer1(millisec+5000,timer1);
                countDownTimer2.cancel();
            }
            else{
                startTimer1(millisec,timer1);
                startTimer2(millisec+5000,timer2);
                countDownTimer1.cancel();
            }
        }
        btnFunc(buttonx);
    }

    public void createGrid(TableLayout field, ArrayList<ArrayList<Block>>buttonx){
        field.removeAllViews();
        for( int i = 0; i<rowx; i++){
            ArrayList<Block> bff = new ArrayList<>();
            for(int j=0;j<columnx;j++){
                Block btx = new Block(this);
                TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                params2.weight = 1;
                btx.setLayoutParams(params2);
                btx.setId((i*columnx)+j);
                System.out.println(btx.getId());
                bff.add(btx);
            }
            buttonx.add(bff);
        }
        for (int row = 0; row < rowx; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.removeAllViews();
            tableRow.setId(row);
            TableRow.LayoutParams params = new
                    TableRow.LayoutParams(TableRow
                    .LayoutParams.MATCH_PARENT, TableRow
                    .LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(params);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int buttonWidthAndHeight;
            if(columnx>5){
                buttonWidthAndHeight = width/ (columnx+2);
            }
            else{
                buttonWidthAndHeight = width/ (6);
            }
            for (int column = 0; column < columnx; column++) {
                Block xbt = buttonx.get(row).get(column);
                TableRow.LayoutParams btf = new TableRow.LayoutParams(buttonWidthAndHeight ,buttonWidthAndHeight, 1f );
                btf.topMargin=btf.bottomMargin= btf.leftMargin=btf.rightMargin=10;
                xbt.setLayoutParams(btf);
                tableRow.addView(xbt);
                ViewGroup.LayoutParams abx = tableRow.getLayoutParams();
                abx.height=buttonWidthAndHeight;
                tableRow.setLayoutParams(abx);
            }
            field.addView(tableRow);
        }
    }
    public void btnFunc(ArrayList<ArrayList<Block>> blocks){
        for (int row = 0; row < rowx; row++) {
            for (int column = 0; column < columnx; column++) {
                Block but = blocks.get(row).get(column);
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int bool = (getIntent().getIntExtra("LastWon",0));
                        Integer color1;
                        Boolean clx;
                        clx=((turn+bool)%2==0);
                        color1=clx?Color.BLUE:Color.RED;
                        but.refresh(blocks,turn,color1,rowx,columnx);

                        boolean bool2 = (getIntent().getIntExtra("time",0))==0;

                        if (turn>1){
                            if((bool==2) && clx && flag && bool2){

                                but.refresh(blocks,turn,color1,rowx,columnx);
                                but.refresh(blocks,turn,color1,rowx,columnx);
                                flag=false;
                            }
                            if((bool==3) && (!clx) && flag && bool2){

                                but.refresh(blocks,turn,color1,rowx,columnx);
                                but.refresh(blocks,turn,color1,rowx,columnx);
                                flag=false;
                            }
                        }
                        final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound4);
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });
                        mp.start();
                        for(int i=0;i<rowx;i++) {
                            for (int k = 0; k < columnx; k++) {
                                Block bxx = blocks.get(i).get(k);
                                if ((bxx.getXolor()-color1)==0) {
                                    blocks.get(i).get(k).setEnabled(false);
                                    if(clx){
                                        redCount+=blocks.get(i).get(k).getCount();
                                    }
                                    else{
                                        blueCount+=blocks.get(i).get(k).getCount();
                                    }
                                    continue;
                                }
                                else  {
                                    if(turn>0){
                                        if ((bxx.getXolor()==0)) {
                                            blocks.get(i).get(k).setEnabled(false);
                                            continue;
                                        }
                                        else{
                                            blocks.get(i).get(k).setEnabled(true);
                                            Block.isClickable=true;
                                            if(clx){
                                                blueCount+=blocks.get(i).get(k).getCount();
                                            }
                                            else{
                                                redCount+=blocks.get(i).get(k).getCount();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(getIntent().getIntExtra("ifTime",0)!=0){
                            if((turn+bool)%2==0){
                                countDownTimer1.cancel();
                                startTimer2(timex2,findViewById(R.id.constraintLayout3).findViewById(R.id.progressBar));
                            }
                            else{
                                countDownTimer2.cancel();
                                startTimer1(timex1,findViewById(R.id.constraintLayout2).findViewById(R.id.progressBar));
                            }
                        }

                        turnCounter.setText(redCount.toString());
                        turnCounter2.setText(blueCount.toString());
                        setTurn();
                        if(turn>1){

                            if(Block.isClickable){
                                Block.isClickable=false;
                            }
                            else{
                                winDialog();
                            }
                        }
                        redCount=blueCount=0;
                    }
                });
            }
        }
    }
    public void winDialog(){
        Dialog winBox = new Dialog(MainActivity.this);
        winBox.setContentView(R.layout.win_dialog);
        winBox.setCancelable(false);
        winBox.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        TextView score = winBox.getWindow().findViewById(R.id.score);
        Button plyAg= winBox.getWindow().findViewById(R.id.playButton);
        Button homeBut= winBox.getWindow().findViewById(R.id.homeButton);
        Button textBut= winBox.getWindow().findViewById(R.id.nameField);
        Integer scorx = (int) ((Math.abs(timex1-timex2))/1000 + 1) * (redCount>blueCount?redCount:blueCount);
        int lastWon = getIntent().getIntExtra("LastWon",0);
        Integer scoreFromBefore;
        if((turn+lastWon)%2==0){
            scoreFromBefore = getIntent().getIntExtra("Highscorered",0);
        }
        else{
            scoreFromBefore = getIntent().getIntExtra("Highscoreblue",0);
        }
        System.out.println(";;;;;;;;;;;;;;;;;;;");
        System.out.println(scorx);
        scorx=scorx+scoreFromBefore;
        System.out.println(scoreFromBefore);
        System.out.println(scorx);
        System.out.println(";;;;;;;;;;;;;;;;;;;");
        score.setText("Score: " + scorx.toString());
        flag=true;
        if(getIntent().getIntExtra("matches",1)>1){
            plyAg.setEnabled(false);
        }
        if((turn+lastWon)%2==1){
            blueWon++;
            if(getIntent().getIntExtra("ifTime",0)!=0){
                countDownTimer2.cancel();
            }
        }
        else{
            redWon++;
            if(getIntent().getIntExtra("ifTime",0)!=0){
                countDownTimer1.cancel();
            }
        }
        if((turn+lastWon)%2==0) {
            System.out.println("Red Won");
            System.out.println(scorx);
            getIntent().putExtra("Highscorered", scorx);
        }
        else {
            System.out.println("Blue Won");
            System.out.println(scorx);
            getIntent().putExtra("Highscoreblue", scorx);
        }
        if((redWon+blueWon)<getIntent().getIntExtra("matches",1)){
            getIntent().putExtra("LastWon",(turn+getIntent().getIntExtra("LastWon",0))%2+2);
            finish();
            startActivity(getIntent());
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        plyAg.setOnClickListener(v -> {
            winBox.dismiss();
            finish();
            startActivity(getIntent());
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        homeBut.setOnClickListener(v -> {
            Intent home = new Intent(MainActivity.this, MainActivity2.class);
            winBox.dismiss();
            finish();
            startActivity(home);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        });
        winBox.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound3);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mp.start();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        if(redWon+blueWon>=getIntent().getIntExtra("matches",1)){
            if(redWon<blueWon){
                textBut.setText(player1.getText().toString()+" WINS");
                myEdit.putString("WonBefore","Blue");
                myEdit.apply();
            }
            else if(blueWon<redWon){
                textBut.setText(player2.getText().toString()+" WINS");
                myEdit.putString("WonBefore","Red");
                myEdit.apply();
            }
            else if(getIntent().getIntExtra("matches",1)>1){
                int scorered = getIntent().getIntExtra("Highscorered",0);
                int scoreblue = getIntent().getIntExtra("Highsoreblue",0);
                System.out.println("#####");
                System.out.println(scorered);
                System.out.println(scoreblue);
                System.out.println("#####");
                if(scorered>scoreblue){
                    textBut.setText(player2.getText().toString()+" WINS");
                    myEdit.putString("WonBefore","Red");
                    myEdit.apply();
                }
                else if (scoreblue>scorered){
                    textBut.setText(player1.getText().toString()+" WINS");
                    myEdit.putString("WonBefore","Blue");
                    myEdit.apply();
                }
                else{
                    getIntent().putExtra("LastWon",(turn+getIntent().getIntExtra("LastWon",0))%2+2);
                    finish();
                    startActivity(getIntent());
                }
            }
            redWon=blueWon=0;
            Integer h1 = sharedPreferences.getInt("Highscore", 0);
            if(h1<scorx) {
                myEdit.putInt("Highscore", scorx);
                score.setText("New HighScore: "+scorx.toString());
            }
            myEdit.apply();
            winBox.show();
        }
    }
    public void startTimer1(long timerStartFrom, ProgressBar barx) {
        countDownTimer1 = new CountDownTimer(timerStartFrom, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished<timerStartFrom/5){
                    barx.setProgressTintList(ColorStateList.valueOf(Color.RED));
                }
                else{
                    barx.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                barx.setProgress((int) ((millisUntilFinished)/100));
                TextView a = findViewById(R.id.constraintLayout2).findViewById(R.id.timeText);
                Integer b =(int) millisUntilFinished/1000;
                a.setText(b.toString());
                timex1 = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                winDialog();
            }

        }.start();
    }
    public void startTimer2(long timerStartFrom, ProgressBar barx) {
        countDownTimer2 = new CountDownTimer(timerStartFrom, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished<timerStartFrom/5){
                    barx.setProgressTintList(ColorStateList.valueOf(Color.RED));
                }
                else{
                    barx.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                barx.setProgress((int) ((millisUntilFinished)/100));
                TextView a = findViewById(R.id.constraintLayout3).findViewById(R.id.timeText);
                Integer b =((int) millisUntilFinished)/1000;
                a.setText(b.toString());
                timex2=millisUntilFinished;
            }

            @Override
            public void onFinish() {
                winDialog();
            }

        }.start();
    }
}
