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
        int row=j/columnx;
        int col=j%columnx;
        System.out.println("====");
        System.out.println(j);
        System.out.println(row);
        System.out.println(col);
        System.out.println("****");
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
            if(col>0&&col<columnx-1){
                System.out.println(1);
                btf=blocks.get(row).get(col-1);
                btf.refresh(blocks,tn,color,rowx,columnx);
                btf=blocks.get(row).get(col+1);
                btf.refresh(blocks,tn,color,rowx,columnx);
            }
            else {
                System.out.println("****\n");
                System.out.println(columnx);
                System.out.println("====");
                if(columnx>1) {
                    if (col == 0) {
                        System.out.println(2);
                        System.out.println(col);
                        System.out.println(row);
                        btf = blocks.get(row).get((col)+1);
                        btf.refresh(blocks, tn, color, rowx, columnx);
                    } else if (col == (columnx - 1)) {
                        System.out.println(3);
                        btf = blocks.get(row).get((col)-1);
                        btf.refresh(blocks, tn, color, rowx, columnx);
                    }
                }
            }
            if(row>0&&row<rowx-1){
                System.out.println(4);
                btf=blocks.get(row-1).get(col);
                btf.refresh(blocks,tn,color,rowx,columnx);
                btf=blocks.get(row+1).get(col);
                btf.refresh(blocks,tn,color,rowx,columnx);
            }
            else {
                if(rowx>1) {
                    if (row == 0) {
                        System.out.println(5);
                        btf = blocks.get((row)+1).get(col);
                        btf.refresh(blocks, tn, color, rowx, columnx);
                    } else if (row == rowx-1) {
                        System.out.println(6);
                        btf = blocks.get((row)-1).get(col);
                        btf.refresh(blocks, tn, color, rowx, columnx);
                    }
                }
            }
        }
        blocks.get(row).set(col,but);
    }





}
