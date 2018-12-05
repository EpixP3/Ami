package com.f4pl0.ami.Fragments.NewPostFragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f4pl0.ami.R;


public class NewPostPicFragment extends Fragment {

    TabLayout chosePictureSourceTabLayout;
    choseGalleryPhotoFragment choseGalleryPhotoFragment;
    choosePixabayPhotoFragment choosePixabayPhotoFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_new_post_pic, container, false);

        //Initialize Components
        choseGalleryPhotoFragment = new choseGalleryPhotoFragment();
        choosePixabayPhotoFragment = new choosePixabayPhotoFragment();
        chosePictureSourceTabLayout = fragmentView.findViewById(R.id.chosePictureSourceTabLayout);
        chosePictureSourceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                switch(tab.getPosition()){
                    case 0:
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                        transaction.replace(R.id.pictureContainerFragment, choseGalleryPhotoFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case 1:
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.pictureContainerFragment, choosePixabayPhotoFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.pictureContainerFragment, choseGalleryPhotoFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        return fragmentView;
    }
}
