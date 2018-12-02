package com.f4pl0.ami.Fragments.NewPostFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.f4pl0.ami.NewPostActivity;
import com.f4pl0.ami.R;


public class NewPostTitleTextFragment extends Fragment {

    EditText titleEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_new_post_title_text, container, false);

        //Initialize Components
        titleEditText = fragmentView.findViewById(R.id.newPostTitleEditText);
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((NewPostActivity)getActivity()).SetPostTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((NewPostActivity)getActivity()).SetPostTitle(s.toString());
            }
        });

        return fragmentView;
    }
}
