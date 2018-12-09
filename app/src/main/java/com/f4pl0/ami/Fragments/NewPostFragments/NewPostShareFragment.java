package com.f4pl0.ami.Fragments.NewPostFragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.f4pl0.ami.NewPostActivity;
import com.f4pl0.ami.R;

public class NewPostShareFragment extends Fragment {

    ImageButton shareFamilyBtn, shareCloseFriendsBtn, shareAcquaintancesBtn, shareLikeMindedBtn;
    boolean fam = true, clo = true, acq = true, like = true;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_new_post_share, container, false);

        //Init components
        shareFamilyBtn = fragmentView.findViewById(R.id.newPostShareFamilyBtn);
        shareCloseFriendsBtn = fragmentView.findViewById(R.id.newPostShareCloseFriendsBtn);
        shareAcquaintancesBtn = fragmentView.findViewById(R.id.newPostShareAcquaintancesBtn);
        shareLikeMindedBtn = fragmentView.findViewById(R.id.newPostShareLikeMindedBtn);

        shareFamilyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fam){
                    fam = false;
                    ((NewPostActivity)getActivity()).SetShare(0,fam);
                    shareFamilyBtn.setImageAlpha(100);
                }else{
                    fam = true;
                    ((NewPostActivity)getActivity()).SetShare(0,fam);
                    shareFamilyBtn.setImageAlpha(255);
                }
            }
        });
        shareCloseFriendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clo){
                    clo = false;
                    ((NewPostActivity)getActivity()).SetShare(1, clo);
                    shareCloseFriendsBtn.setImageAlpha(100);
                }else{
                    clo = true;
                    ((NewPostActivity)getActivity()).SetShare(1, clo);
                    shareCloseFriendsBtn.setImageAlpha(255);
                }
            }
        });
        shareAcquaintancesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(acq){
                    acq = false;
                    ((NewPostActivity)getActivity()).SetShare(2, acq);
                    shareAcquaintancesBtn.setImageAlpha(100);
                }else{
                    acq = true;
                    ((NewPostActivity)getActivity()).SetShare(2, acq);
                    shareAcquaintancesBtn.setImageAlpha(255);
                }
            }
        });
        shareLikeMindedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(like){
                    like = false;
                    ((NewPostActivity)getActivity()).SetShare(3, like);
                    shareLikeMindedBtn.setImageAlpha(100);
                }else{
                    like = true;
                    ((NewPostActivity)getActivity()).SetShare(3, like);
                    shareLikeMindedBtn.setImageAlpha(255);
                }
            }
        });

        return fragmentView;
    }
}
