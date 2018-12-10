package com.f4pl0.ami.Fragments.MainFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f4pl0.ami.R;

@SuppressLint("ValidFragment")
public class PostFragment extends Fragment {
    private String title, content, image, posterName, posterLocation, posterImage;

    public PostFragment(String title, String content, String image, String posterName, String posterLocation, String posterImage){
        this.title = title;
        this.content = content;
        this.image = image;
        this.posterName = posterName;
        this.posterLocation = posterLocation;
        this.posterImage = posterImage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_post, container, false);
        Log.d("POST:from", posterName);
        Log.d("POST:title", title);

        return fragmentView;
    }
}
