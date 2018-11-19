package com.f4pl0.ami.Structures;

public class Contact {
    String name;
    String number;
    public Contact(String name, String number){
        this.name = name;
        this.number = number;
    }
    public String GetName(){
        return this.name;
    }
    public String GetNumber(){
        return this.number;
    }
}
