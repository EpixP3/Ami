package com.f4pl0.ami.Fragments.SetupFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.f4pl0.ami.Fragments.ContactFragment;
import com.f4pl0.ami.R;
import com.f4pl0.ami.Utils.Contact;

@SuppressLint("ValidFragment")
public class SetupContactsFragment extends Fragment {

    LinearLayout contactsList;
    Contact[] contacts;

    @SuppressLint("ValidFragment")
    public SetupContactsFragment(Contact[] contacts){
        this.contacts = contacts;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_setup_contacts, container, false);

        // Initialize Components
        contactsList = fragmentView.findViewById(R.id.contactsList);
        for (int i=0;i<contacts.length; i++){
            FragmentManager fragmentManager = getFragmentManager ();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
            fragmentTransaction.add (R.id.contactsList, new ContactFragment(contacts[i], i));
            fragmentTransaction.commit ();
        }

        return fragmentView;
    }

}
