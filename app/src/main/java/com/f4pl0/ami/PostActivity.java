package com.f4pl0.ami;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PostActivity extends AppCompatActivity {

    String posterName, posterLocation, title, content;
    Bitmap posterImgBmp, contentImgBmp;
    TextView posterNameTxt, posterLocationTxt, titleTxt, contentTxt;
    ImageView posterImg, contentImg;
    ImageButton backBtn;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //Init compontnts
        posterNameTxt = findViewById(R.id.postActivity_name);
        posterLocationTxt = findViewById(R.id.postActivity_location);
        titleTxt = findViewById(R.id.postActivity_title);
        contentTxt = findViewById(R.id.postActivity_contentTxt);
        posterImg = findViewById(R.id.postActivity_profileImg);
        contentImg = findViewById(R.id.postActivity_contentImg);
        backBtn = findViewById(R.id.postActivity_backBtn);
        webView = findViewById(R.id.post_webView);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            //Get data needed for the post
            Intent intent = getIntent();
            posterImgBmp = decodeBase64(intent.getStringExtra("posterImg"));
            contentImgBmp = loadImageFromStorage(intent.getStringExtra("contentImg"));

            posterName = intent.getStringExtra("posterName");
            title = intent.getStringExtra("title");
            posterLocation = intent.getStringExtra("posterLocation");
            content = intent.getStringExtra("contentTxt");

            //Set the data to the views
            posterNameTxt.setText(posterName);
            if (posterImgBmp != null) {
                posterImg.setImageBitmap(posterImgBmp);
            } else {
                //posterImg.setVisibility(View.GONE);
            }
            titleTxt.setText(title);
            contentTxt.setText(content);
            if (contentImgBmp != null) {
                contentImg.setImageBitmap(contentImgBmp);
            } else {
                contentImg.setVisibility(View.GONE);
            }
            posterLocationTxt.setText(posterLocation);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(URLUtil.isValidUrl(content)){
            contentTxt.setVisibility(View.GONE);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
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
            webView.loadUrl(content);
        }else{
            webView.setVisibility(View.GONE);
        }
    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        try {
            byte[] decodedBytes = Base64.decode(input, 0);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        }catch (Exception e){
            return null;
        }
    }
    private Bitmap loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
