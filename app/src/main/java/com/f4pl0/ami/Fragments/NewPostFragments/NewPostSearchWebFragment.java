package com.f4pl0.ami.Fragments.NewPostFragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.f4pl0.ami.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NewPostSearchWebFragment extends Fragment {

    EditText urlEdit;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_new_post_search_web, container, false);

        //INIT
        urlEdit = fragmentView.findViewById(R.id.urlEditText);
        webView = fragmentView.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        urlEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String text = urlEdit.getText().toString();
                    if(URLUtil.isValidUrl(text)){
                        webView.loadUrl(text);
                    }else{
                        try {
                            webView.loadUrl("https://www.google.com/search?q="+URLEncoder.encode(text, "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return false;
            }
        });

        return fragmentView;
    }
    public String getUrl(){
        return webView.getUrl();
    }
    public String getTitle(){
        return webView.getTitle();
    }
}
