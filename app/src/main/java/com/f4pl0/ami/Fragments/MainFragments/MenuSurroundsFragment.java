package com.f4pl0.ami.Fragments.MainFragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.f4pl0.ami.MainActivity;
import com.f4pl0.ami.NewPostActivity;
import com.f4pl0.ami.R;
import com.f4pl0.ami.SetupActivity;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuSurroundsFragment extends Fragment {

    LinearLayout postsContainer;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton postAddTextBtn, postAddGalleryBtn, postAddCameraBtn, postAddWebBtn;
    String session;
    PostFragment[] postFragments = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_menu_surrounds, container, false);
        session = getContext().getSharedPreferences("shared", MainActivity.MODE_PRIVATE).getString("SessionID", "");
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
        swipeRefreshLayout.setRefreshing(true);
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
                //TODO GALLERY PICK
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);

                //Intent intent = new Intent(getContext(), NewPostActivity.class);
                //intent.putExtra("TYPE", "2");
                //startActivity(intent);
            }
        });
        postAddCameraBtn = fragmentView.findViewById(R.id.addCameraPostBtn);
        postAddCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO CAMERA PICK
                Intent pickPhoto = new Intent(Intent.ACTION_CAMERA_BUTTON);
                startActivityForResult(pickPhoto , 1);
                //Intent intent = new Intent(getContext(), NewPostActivity.class);
                //intent.putExtra("TYPE", "3");
                //startActivity(intent);
            }
        });
        postAddWebBtn = fragmentView.findViewById(R.id.addWebPostBtn);
        postAddWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO WEB PICK
                Intent intent = new Intent(getContext(), NewPostActivity.class);
                intent.putExtra("TYPE", "4");
                startActivity(intent);
            }
        });
        ClearPosts();
        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                try{
                    wait(500);
                    if(!GetPostsLocal()){
                        GetPosts();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        return fragmentView;
    }
    private void ClearPosts(){
        if(postFragments != null){
            for(int i=0; i < postFragments.length; i++){
                if(postFragments[i].isAdded()) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(postFragments[i]);
                    //transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        }
        postsContainer.removeAllViews();
    }
    private void GetPosts(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://ami.earth/android/api/getPostsSurroundings.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.contains("invalid")) {
                                //Session is invalid, go to setup and delete stored session
                                getContext().getSharedPreferences("shared", MainActivity.MODE_PRIVATE).edit().putString("SessionID", "").commit();
                                Intent myIntent = new Intent(getContext(), SetupActivity.class);
                                startActivity(myIntent);
                            } else if(response.contains("null")){

                            }else{
                                //GOT POSTS
                                Log.d("RESPONSE", response);
                                JSONObject postsObj = new JSONObject(response);
                                JSONArray posts = postsObj.getJSONArray("postsArray");
                                postFragments = new PostFragment[posts.length()];
                                for(int i=0; i < posts.length(); i++){
                                    JSONObject post = posts.getJSONObject(i);
                                    postFragments[i] = new PostFragment(
                                            post.getString("title"),
                                            post.getString("content"),
                                            post.getString("image"),
                                            post.getString("posterName"),
                                            post.getString("posterLocation"),
                                            post.getString("posterImage"));
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.add(R.id.postsContainer, postFragments[i]);
                                    //transaction.addToBackStack(null);
                                    transaction.commit();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "There was an error connecting to the server.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //Put the POST parameters to the request
                Map<String, String> params = new HashMap<String, String>();
                params.put("sessionTXT", session);
                return params;
            }
        };
        queue.add(postRequest);
    }
    private boolean GetPostsLocal(){
        if(postFragments != null){
            for(int i=0; i < postFragments.length; i++){
                    if (!postFragments[i].isAdded()) {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.add(R.id.postsContainer, postFragments[i]);
                        transaction.commit();
                    } else {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.show(postFragments[i]);
                        transaction.commit();
                    }
            }
            return true;
        }else{
            return false;
        }
    }
}
