package com.f4pl0.ami.Fragments.SetupFragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;
import com.f4pl0.ami.Utils.Contact;

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
                ((SetupActivity) getActivity()).showLoading("Getting Your contacts...");
                getContactList();
            }
        });
        return fragmentView;
    }

    private Contact[] getContactList() {
        // Method that gets the contacts and returns it as a array
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                Log.d("PERMISSION","REQUESTING THE PERMISSION");
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        2);
            }
        } else {
            // Permission has already been granted
            Log.d("PERMISSION"," PERMISSION ALREADY GRANTED");
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
                            contacts.add(new Contact(name, phoneNo));
                        }
                        pCur.close();
                    }
                }
            }
            if(cur!=null){
                cur.close();
            }
            ((SetupActivity) getActivity()).showContactsFragment(contacts.toArray(new Contact[contacts.size()]));
        }
        return null;
    }
}
