package com.f4pl0.ami.Fragments.NewPostFragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.f4pl0.ami.NewPostActivity;
import com.f4pl0.ami.R;

import java.io.ByteArrayOutputStream;

@SuppressLint("ValidFragment")
public class NewPostPictureTitleFragment extends Fragment {

    EditText titleEdit;
    ImageView previewImg;
    Bitmap previewBmp;

    public NewPostPictureTitleFragment(Bitmap img){
        this.previewBmp = img;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_new_post_picture_title, container, false);

        titleEdit = fragmentView.findViewById(R.id.giveTitleEditText);
        previewImg = fragmentView.findViewById(R.id.previewPhotoImg);

        titleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((NewPostActivity)getActivity()).SetPostTitle(s.toString());
            }
        });
        previewImg.setImageBitmap(previewBmp);
        return fragmentView;
    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
