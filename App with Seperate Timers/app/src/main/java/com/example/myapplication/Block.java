package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.jar.Attributes;

public class Block extends AppCompatButton {
    private Integer count=0;
    static boolean isClickable=false;
    public int xolor;

    static Integer redCount;

    public Block(Context context) {
        super(context);
        this.setCount(count);
        this.setBackgroundResource(R.drawable.custombutton);
    }

    public Block(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setCount(count);
        this.setBackgroundResource(R.drawable.custombutton);
    }


    public int getXolor() {
        return this.xolor;
    }

    public void setXolor(int xolor) {
        this.xolor = xolor;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
        this.setText(this.count.toString());
        if(count==0){
            this.setText(" ");
        }
    }

    public void tint(int color){
        if(color==Color.RED){
            this.setBackgroundResource(R.drawable.button_red);
            this.setRotation(0);
        }
        if(color==Color.BLUE){
            this.setBackgroundResource(R.drawable.button_blue);
            this.setRotation(180);
        }
        if(color==0){
            this.setBackgroundResource(R.drawable.custombutton);
        }
    }
    public void refresh(ArrayList<ArrayList<Block>> blocks,int tn,int color,Integer rowx,Integer columnx){
        Block but =this;
        int x=but.getCount();
        int j=but.getId();
        if(tn<2){
            x=x+2;
        }
        x=x+1;
        but.setEnabled(false);
        but.setCount(x);
        but.setXolor(color);
        but.tint(color);
        but.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.move_down));
        if(x>3){
            but.setCount(0);
            Block btf;
            but.setXolor(0);
            but.setEnabled(true);
            but.tint(0);
            ArrayList<Block> abc=blocks.get(j/rowx);
            if(j%rowx>0&&j%rowx<columnx-1){
                btf=abc.get(j%rowx-1);
                btf.refresh(blocks,tn,color,rowx,columnx);
                btf=abc.get(j%rowx+1);
                btf.refresh(blocks,tn,color,rowx,columnx);
            }
            if(j%rowx==0){
                btf=abc.get(j%rowx+1);
                btf.refresh(blocks,tn,color,rowx,columnx);
            }
            if(j%rowx==columnx-1){
                btf=abc.get(j%rowx-1);
                btf.refresh(blocks,tn,color,rowx,columnx);
            }
            if(j/rowx>0&&j/rowx<rowx-1){
                btf=blocks.get(j/rowx-1).get(j%rowx);
                btf.refresh(blocks,tn,color,rowx,columnx);
                btf=blocks.get(j/rowx+1).get(j%rowx);
                btf.refresh(blocks,tn,color,rowx,columnx);
            }
            if(j/rowx==0){
                btf=blocks.get(j/rowx+1).get(j%rowx);
                btf.refresh(blocks,tn,color,rowx,columnx);
            }
            if(j/rowx==rowx-1){
                btf=blocks.get(j/rowx-1).get(j%rowx);
                btf.refresh(blocks,tn,color,rowx,columnx);
            }
        }
        blocks.get(j/rowx).set(j%rowx,but);
    }





}
