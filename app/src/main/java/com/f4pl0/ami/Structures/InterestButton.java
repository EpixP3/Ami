package com.f4pl0.ami.Structures;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.f4pl0.ami.R;

public class InterestButton extends android.support.v7.widget.AppCompatButton {

    private boolean activated = false;
    private String name = "";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public InterestButton(Context context, String name) {
        super(context);
        this.name = name;
        this.setAllCaps(false);
        this.setText(name);
        this.setTextColor(Color.WHITE);
        this.setTextSize(11);
        this.setBackground(getResources().getDrawable(R.drawable.interest_button_false));
        SetActivated(false);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SetActivated(!GetActivated());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void SetActivated(boolean activated){
        this.activated = activated;
        if(activated) {
            this.setBackground(getResources().getDrawable(R.drawable.interest_button_true));
        }else{
            this.setBackground(getResources().getDrawable(R.drawable.interest_button_false));
        }
    }
    public boolean GetActivated(){
        return this.activated;
    }
    public void SetName(String name){
        this.name = name;
        this.setText(name);
    }
    public String GetName(){
        return this.name;
    }
    public String GetNameIfActivated(){
        if(this.activated){
            return this.name;
        }else{
            return "";
        }
    }
}
