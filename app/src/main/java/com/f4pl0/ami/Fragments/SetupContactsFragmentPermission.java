package com.f4pl0.ami.Fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;
import com.f4pl0.ami.Structures.Contact;

import java.util.ArrayList;

public class SetupContactsFragmentPermission extends Fragment {

    ImageButton getContactsBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_setup_contacts_fragment_permission, container, false);

        // Initialize components
        getContactsBtn = fragmentView.findViewById(R.id.getContactsBtn);
        getContactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SetupActivity) getActivity()).showContactsFragment(getContactList());
            }
        });
        return fragmentView;
    }

    private Contact[] getContactList() {
        // Method that gets the contacts and returns it as a array
        ArrayList<Contact> contacts = new ArrayList<>();
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("Contact", "Name: " + name);
                        Log.i("Contact", "Phone Number: " + phoneNo);
                        contacts.add(new Contact(name, phoneNo));
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
        return contacts.toArray(new Contact[contacts.size()]);
    }
}
