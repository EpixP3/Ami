package com.f4pl0.ami.Fragments.MainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.f4pl0.ami.NewPostActivity;
import com.f4pl0.ami.R;
import com.github.clans.fab.FloatingActionButton;

public class MenuSurroundsFragment extends Fragment {

    LinearLayout postsContainer;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton postAddTextBtn, postAddGalleryBtn, postAddCameraBtn, postAddWebBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_menu_surrounds, container, false);

        // Initialize the components
        postsContainer = fragmentView.findViewById(R.id.postsContainer);
        swipeRefreshLayout = fragmentView.findViewById(R.id.swipeRefreshLyt);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Refresh
                ClearPosts();
                GetPosts();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        postAddTextBtn = fragmentView.findViewById(R.id.addTextPostBtn);
        postAddTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewPostActivity.class);
                intent.putExtra("TYPE", "1");
                startActivity(intent);
            }
        });
        postAddGalleryBtn = fragmentView.findViewById(R.id.addGalleryPostBtn);
        postAddGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewPostActivity.class);
                intent.putExtra("TYPE", "2");
                startActivity(intent);
            }
        });
        postAddCameraBtn = fragmentView.findViewById(R.id.addCameraPostBtn);
        postAddCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewPostActivity.class);
                intent.putExtra("TYPE", "3");
                startActivity(intent);
            }
        });
        postAddWebBtn = fragmentView.findViewById(R.id.addWebPostBtn);
        postAddWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewPostActivity.class);
                intent.putExtra("TYPE", "4");
                startActivity(intent);
            }
        });
        return fragmentView;
    }
    private void ClearPosts(){
        postsContainer.removeAllViews();
    }
    private void GetPosts(){
        for(int i=0; i < 15; i++){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.postsContainer, new PostFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
