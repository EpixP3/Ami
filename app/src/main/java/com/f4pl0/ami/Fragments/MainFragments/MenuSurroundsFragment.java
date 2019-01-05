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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MenuSurroundsFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 121;
    LinearLayout postsContainer;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton postAddTextBtn, postAddGalleryBtn, postAddCameraBtn, postAddWebBtn;
    String session;
    public PostFragment[] postFragments = null;

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
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(pickPhoto , 122);
            }
        });
        postAddCameraBtn = fragmentView.findViewById(R.id.addCameraPostBtn);
        postAddCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    getActivity().startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

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

        ClearPosts();
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public synchronized void run() {
                try{
                    wait(500);

                    GetPosts();
                    //if(!GetPostsLocal()){
                    //}
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
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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

                                }

                                //ADD POSTS

                                int prevnum = 0;
                                boolean left = true;
                                for(int i = 0; i < postFragments.length; i+= 3){

                                    final int min = 1;
                                    final int max = 3;
                                    int random = 0;
                                    while(true) {
                                        random = new Random().nextInt((max - min) + 1) + min;
                                        if(random != prevnum){
                                            prevnum = random;
                                            break;
                                        }
                                    }

                                    switch(random){
                                        case 1:
                                            LinearLayout llyt = new LinearLayout(getContext());
                                            llyt.setOrientation(LinearLayout.HORIZONTAL);
                                            llyt.setId(View.generateViewId());

                                            LinearLayout llyt2 = new LinearLayout(getContext());
                                            llyt2.setOrientation(LinearLayout.VERTICAL);
                                            llyt2.setId(View.generateViewId());

                                            LinearLayout llyt3 = new LinearLayout(getContext());
                                            llyt3.setOrientation(LinearLayout.VERTICAL);
                                            llyt3.setId(View.generateViewId());

                                            LinearLayout.LayoutParams paramsSmall = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                                            LinearLayout.LayoutParams paramsBig = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.MATCH_PARENT);
                                            LinearLayout.LayoutParams paramsParent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);

                                                //Add first post
                                            if(left){
                                                paramsParent.gravity = Gravity.RIGHT;
                                                llyt.getRootView().setLayoutParams(paramsParent);
                                                postsContainer.addView(llyt);

                                                paramsBig.gravity = Gravity.LEFT;
                                                paramsBig.weight = 4;
                                                llyt3.getRootView().setLayoutParams(paramsBig);
                                                llyt.addView(llyt3);
                                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                                transaction.add(llyt3.getId(), postFragments[i]);
                                                transaction.commit();




                                                //Add lyt for posts 2 and 3
                                                paramsSmall.gravity = Gravity.RIGHT;
                                                paramsSmall.weight = 1;
                                                llyt2.getRootView().setLayoutParams(paramsSmall);
                                                llyt.addView(llyt2);

                                                //Add posts 2 and 3 if they exist

                                                if (postFragments[i + 1] != null) {
                                                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                                                    transaction1.add(llyt2.getId(), postFragments[i + 1]);
                                                    transaction1.commit();
                                                }
                                                if (postFragments[i + 2] != null) {
                                                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                                                    transaction1.add(llyt2.getId(), postFragments[i + 2]);
                                                    transaction1.commit();
                                                }
                                                left = false;
                                            }else{
                                                paramsParent.gravity = Gravity.LEFT;
                                                llyt.getRootView().setLayoutParams(paramsParent);
                                                postsContainer.addView(llyt);

                                                //Add lyt for posts 2 and 3
                                                paramsSmall.gravity = Gravity.LEFT;
                                                paramsSmall.weight = 1;
                                                llyt2.getRootView().setLayoutParams(paramsSmall);
                                                llyt.addView(llyt2);

                                                //Add posts 2 and 3 if they exist

                                                if (postFragments[i + 1] != null) {
                                                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                                                    transaction1.add(llyt2.getId(), postFragments[i + 1]);
                                                    transaction1.commit();
                                                }
                                                if (postFragments[i + 2] != null) {
                                                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                                                    transaction1.add(llyt2.getId(), postFragments[i + 2]);
                                                    transaction1.commit();
                                                }
                                                paramsBig.gravity = Gravity.RIGHT;
                                                paramsBig.weight = 4;
                                                llyt3.getRootView().setLayoutParams(paramsBig);
                                                llyt.addView(llyt3);
                                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                                transaction.add(llyt3.getId(), postFragments[i]);
                                                transaction.commit();
                                                left = true;
                                            }

                                            break;
                                        case 2:
                                            FragmentTransaction transactionc = getFragmentManager().beginTransaction();
                                            transactionc.add(postsContainer.getId(), postFragments[i]);
                                            transactionc.commit();
                                            i -= 2;
                                            break;
                                        case 3:
                                            LinearLayout linearLayout = new LinearLayout(getContext());
                                            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                            linearLayout.setId(View.generateViewId());
                                            LinearLayout linearLayouta = new LinearLayout(getContext());
                                            linearLayouta.setOrientation(LinearLayout.VERTICAL);
                                            linearLayouta.setId(View.generateViewId());

                                            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(postsContainer.getWidth()/2 , ViewGroup.LayoutParams.WRAP_CONTENT);
                                            linearLayouta.getRootView().setLayoutParams(params1);
                                            linearLayout.getRootView().setLayoutParams(new LinearLayout.LayoutParams(postsContainer.getWidth() , ViewGroup.LayoutParams.WRAP_CONTENT));

                                            postsContainer.addView(linearLayout);

                                            //Add first post
                                            FragmentTransaction transactiona = getFragmentManager().beginTransaction();
                                            transactiona.add(linearLayout.getId(), postFragments[i]);
                                            transactiona.commit();

                                            //Add lyt for posts 2 and 3
                                            linearLayout.addView(linearLayouta);

                                            //Add posts 2 and 3 if they exist

                                            if (postFragments[i + 1] != null) {
                                                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                                                transaction1.add(linearLayouta.getId(), postFragments[i + 1]);
                                                transaction1.commit();
                                            }
                                            i -= 1;
                                            break;
                                    }

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
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean GetPostsLocal(){
        if(postFragments != null){
            for(int i=0; i < postFragments.length; i+=3){
                final int min = 1;
                final int max = 3;
                final int random = new Random().nextInt((max - min) + 1) + min;
                switch(random){
                    case 1:
                        LinearLayout llyt = new LinearLayout(getContext());
                        llyt.setOrientation(LinearLayout.HORIZONTAL);
                        llyt.setId(View.generateViewId());

                        LinearLayout llyt2 = new LinearLayout(getContext());
                        llyt2.setOrientation(LinearLayout.VERTICAL);
                        llyt2.setId(View.generateViewId());

                        postsContainer.addView(llyt);
                        //Add first post
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.add(llyt.getId(), postFragments[i]);
                        transaction.commit();

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(postsContainer.getWidth()/2 , ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.gravity = Gravity.LEFT;
                        llyt2.getRootView().setLayoutParams(params);

                        //Add lyt for posts 2 and 3
                        llyt.addView(llyt2);

                        //Add posts 2 and 3 if they exist

                        if (postFragments[i + 1] != null) {
                            FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                            transaction1.add(llyt2.getId(), postFragments[i + 1]);
                            transaction1.commit();
                        }
                        if (postFragments[i + 2] != null) {
                            FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                            transaction1.add(llyt2.getId(), postFragments[i + 2]);
                            transaction1.commit();
                        }
                        break;
                    case 2:
                        FragmentTransaction transactionc = getFragmentManager().beginTransaction();
                        transactionc.add(postsContainer.getId(), postFragments[i]);
                        transactionc.commit();
                        i -= 2;
                        break;
                    case 3:
                        LinearLayout linearLayout = new LinearLayout(getContext());
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout.setId(View.generateViewId());
                        LinearLayout linearLayouta = new LinearLayout(getContext());
                        linearLayouta.setOrientation(LinearLayout.VERTICAL);
                        linearLayouta.setId(View.generateViewId());

                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(postsContainer.getWidth()/2 , ViewGroup.LayoutParams.WRAP_CONTENT);
                        linearLayouta.getRootView().setLayoutParams(params1);
                        linearLayout.getRootView().setLayoutParams(new LinearLayout.LayoutParams(postsContainer.getWidth() , ViewGroup.LayoutParams.WRAP_CONTENT));

                        postsContainer.addView(linearLayout);

                        //Add first post
                        FragmentTransaction transactiona = getFragmentManager().beginTransaction();
                        transactiona.add(linearLayout.getId(), postFragments[i]);
                        transactiona.commit();

                        //Add lyt for posts 2 and 3
                        linearLayout.addView(linearLayouta);

                        //Add posts 2 and 3 if they exist

                        if (postFragments[i + 1] != null) {
                            FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                            transaction1.add(linearLayouta.getId(), postFragments[i + 1]);
                            transaction1.commit();
                        }
                        break;
                }
                    /*if (!postFragments[i].isAdded()) {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.add(R.id.postsContainer, postFragments[i]);
                        transaction.commit();
                    } else {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.show(postFragments[i]);
                        transaction.commit();
                        Log.d("FRAGMENT ADDED = ", ""+postFragments[i].isAdded());
                    }*/
            }
            return true;
        }else{
            return false;
        }
    }
}
