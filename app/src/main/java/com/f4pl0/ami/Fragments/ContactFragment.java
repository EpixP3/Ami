package com.f4pl0.ami.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.f4pl0.ami.R;
import com.f4pl0.ami.Structures.Contact;

@SuppressLint("ValidFragment")
public class ContactFragment extends Fragment {

    public Contact contact;
    TextView nameText;

    @SuppressLint("ValidFragment")
    public ContactFragment(Contact contact){
        this.contact = contact;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_contact, container, false);

        // Initialize components
        nameText = fragmentView.findViewById(R.id.ContactNameText);
        nameText.setText(contact.GetName());

        return fragmentView;
    }

}
