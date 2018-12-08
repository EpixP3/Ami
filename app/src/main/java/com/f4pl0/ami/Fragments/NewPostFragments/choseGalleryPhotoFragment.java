package com.f4pl0.ami.Fragments.NewPostFragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.f4pl0.ami.NewPostActivity;
import com.f4pl0.ami.R;

import java.io.File;
import java.util.ArrayList;


public class choseGalleryPhotoFragment extends Fragment {

    GridLayout gridGalleryPhotoLayout;
    ScrollView galleryScrollView;
    String[] images;
    int i=0;
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_chose_gallery_photo, container, false);
        i=0;
        //Initialize components
        gridGalleryPhotoLayout = fragmentView.findViewById(R.id.gridGalleryPhotoLayout);
        galleryScrollView = fragmentView.findViewById(R.id.galleryScrollView);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                try {
                    ((NewPostActivity)getActivity()).showLoading("Loading pictures...");
                    wait(500);
                    for (int a = 0; a < 15; a++) {
                        final ImageButton imageButton = new ImageButton(getContext());
                        GridLayout.LayoutParams parem = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f));
                        //imageButton.getRootView().setLayoutParams(parem);
                        imageButton.getRootView().setLayoutParams(new AbsListView.LayoutParams(220, 220));
                        imageButton.setBackground(getResources().getDrawable(R.drawable.text_box_input_bg));
                        imageButton.setImageBitmap(getBitmapFromPath(images[i]));
                        imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog settingsDialog = new Dialog(getContext());
                                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                ImageView imageView = new ImageView(getContext());
                                imageView.setImageBitmap(((BitmapDrawable) imageButton.getDrawable()).getBitmap());
                                settingsDialog.addContentView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                Button confirmBtn = new Button(getContext());
                                confirmBtn.setText("Use");
                                confirmBtn.setAllCaps(false);
                                confirmBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ((NewPostActivity) getActivity()).SetPostImage(((BitmapDrawable) imageButton.getDrawable()).getBitmap());
                                        settingsDialog.dismiss();
                                    }
                                });
                                settingsDialog.addContentView(confirmBtn, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                settingsDialog.show();
                            }
                        });
                        addViewToGridView(imageButton);
                        i++;
                    }
                    ((NewPostActivity)getActivity()).dismissLoading();
                }catch (Exception e){
                    ((NewPostActivity)getActivity()).dismissLoading();
                    e.printStackTrace();
                }
            }
        });
        images = getImagesPath(getActivity()).toArray(new String[0]);
        galleryScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View view = galleryScrollView.getChildAt(galleryScrollView.getChildCount() - 1);
                int diff = (view.getBottom() - (galleryScrollView.getHeight() + galleryScrollView.getScrollY()));

                // if diff is zero, then the bottom has been reached
                if (diff == 0) {
                    for (int a=0; a<15; a++){
                        gridGalleryPhotoLayout.removeViewAt(0);
                    }
                    thread.run();
                    galleryScrollView.setScrollY(0);
                    galleryScrollView.setScrollX(0);
                }
            }
        });
        thread.run();

        return fragmentView;
    }
    private Bitmap getBitmapFromPath(String path){
        /*File sd = Environment.getExternalStorageDirectory();
        File image = new File(path);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);*/


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmapa = BitmapFactory.decodeFile(path, options);
        return bitmapa;
    }
    public static ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(PathOfImage);
        }
        return listOfAllImages;
    }
    public void addViewToGridView(View viewToAdd){
        gridGalleryPhotoLayout.addView(viewToAdd);
    }
}
