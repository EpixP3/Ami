package com.f4pl0.ami.Fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;
import com.f4pl0.ami.Utils.Contact;

@SuppressLint("ValidFragment")
public class ContactFragment extends Fragment {

    int arrayID;
    public Contact contact;
    TextView nameText;
    ImageButton contactClosefriendBtn, contactRelativeBtn, contactPartnerBtn;

    @SuppressLint("ValidFragment")
    public ContactFragment(Contact contact, int arrayID){
        this.contact = contact;
        this.arrayID = arrayID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_contact, container, false);

        // Initialize components and configure them
        nameText = fragmentView.findViewById(R.id.ContactNameText);
        nameText.setText(contact.GetName());
        contactClosefriendBtn = fragmentView.findViewById(R.id.contactClosefriendBtn);
        contactRelativeBtn = fragmentView.findViewById(R.id.contactRelativeBtn);
        contactPartnerBtn = fragmentView.findViewById(R.id.contactPartnerBtn);
        contactClosefriendBtn.setOnClickListener(setClosefriend);
        contactRelativeBtn.setOnClickListener(setRelative);
        contactPartnerBtn.setOnClickListener(setPartner);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            contactClosefriendBtn.setImageAlpha(100);
            contactRelativeBtn.setImageAlpha(100);
            contactPartnerBtn.setImageAlpha(100);
        }

        //Return the view
        return fragmentView;
    }
    View.OnClickListener setClosefriend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (contact.GetType() != contact.TYPE_CLOSE_FRIEND) {
                    contactClosefriendBtn.setImageAlpha(255);
                    contactRelativeBtn.setImageAlpha(100);
                    contactPartnerBtn.setImageAlpha(100);
                    contact.SetType(contact.TYPE_CLOSE_FRIEND);
                    ((SetupActivity)getActivity()).setContactType(arrayID, contact.TYPE_CLOSE_FRIEND);
                }else{
                    contactClosefriendBtn.setImageAlpha(100);
                    contactRelativeBtn.setImageAlpha(100);
                    contactPartnerBtn.setImageAlpha(100);
                    contact.SetType(contact.TYPE_ACQUAINTANCE);
                    ((SetupActivity)getActivity()).setContactType(arrayID, contact.TYPE_ACQUAINTANCE);
                }
            }
        }
    };
    View.OnClickListener setRelative = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (contact.GetType() != contact.TYPE_RELATIVE) {
                    contactClosefriendBtn.setImageAlpha(100);
                    contactRelativeBtn.setImageAlpha(255);
                    contactPartnerBtn.setImageAlpha(100);
                    contact.SetType(contact.TYPE_RELATIVE);
                    ((SetupActivity)getActivity()).setContactType(arrayID, contact.TYPE_RELATIVE);
                }else{
                    contactClosefriendBtn.setImageAlpha(100);
                    contactRelativeBtn.setImageAlpha(100);
                    contactPartnerBtn.setImageAlpha(100);
                    contact.SetType(contact.TYPE_ACQUAINTANCE);
                    ((SetupActivity)getActivity()).setContactType(arrayID, contact.TYPE_ACQUAINTANCE);
                }
            }
        }
    };
    View.OnClickListener setPartner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (contact.GetType() != contact.TYPE_PARTNER) {
                    contactClosefriendBtn.setImageAlpha(100);
                    contactRelativeBtn.setImageAlpha(100);
                    contactPartnerBtn.setImageAlpha(255);
                    contact.SetType(contact.TYPE_PARTNER);
                    ((SetupActivity)getActivity()).setContactType(arrayID, contact.TYPE_PARTNER);

                } else {
                    contactClosefriendBtn.setImageAlpha(100);
                    contactRelativeBtn.setImageAlpha(100);
                    contactPartnerBtn.setImageAlpha(100);
                    contact.SetType(contact.TYPE_ACQUAINTANCE);
                    ((SetupActivity)getActivity()).setContactType(arrayID, contact.TYPE_ACQUAINTANCE);
                }
            }
        }
    };
}
