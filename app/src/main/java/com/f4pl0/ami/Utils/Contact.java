package com.f4pl0.ami.Utils;

public class Contact {

    public int TYPE_ACQUAINTANCE = 0;
    public int TYPE_CLOSE_FRIEND = 1;
    public int TYPE_RELATIVE = 2;
    public int TYPE_PARTNER = 3;

    private String name;
    private String number;
    private int type = 0;

    public Contact(String name, String number){
        this.name = name;
        this.number = number;
    }

    public Contact(String name, String number, int type){
        this.name = name;
        this.number = number;
        this.type = type;
    }

    public String GetName(){
        return this.name;
    }
    public String GetNumber(){
        return this.number;
    }
    public int GetType(){
        return this.type;
    }
    public void SetType(int type){
        this.type = type;
    }
}
