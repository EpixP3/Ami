package com.f4pl0.ami.Utils;

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
            String inters = getContext().getSharedPreferences("interests", Context.MODE_PRIVATE).getString("user.interests", "");
            getContext().getSharedPreferences("interests", Context.MODE_PRIVATE).edit().putString("user.interests", inters + ","+name).commit();
        }else{
            this.setBackground(getResources().getDrawable(R.drawable.interest_button_false));
            String inters = getContext().getSharedPreferences("interests", Context.MODE_PRIVATE).getString("user.interests", "");
            getContext().getSharedPreferences("interests", Context.MODE_PRIVATE).edit().putString("user.interests", inters.replace(","+name, "")).commit();
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
