package com.example.zemoso.imageurls;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import Models.ImageData;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements StoreToLocal {
    private List<FeedItem> dList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter mAdapter;
    private final int EXT_STORAGE_PERM = 1234;
    public String imageUrl;
    private Realm realm;
    public int id_val;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new Adapter(dList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareData();
    }

    private void prepareData() {
        FeedItem f = new FeedItem("https://crackberry.com/sites/crackberry.com/files/styles/large/public/topic_images/2013/ANDROID.png?itok=xhm7jaxS");
        dList.add(f);
    }

    @Override
    public void storeToLocal(String url) {
        imageUrl=url;
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXT_STORAGE_PERM);
        } else {
            realm=Realm.getDefaultInstance();
            //realm.beginTransaction();
            RealmResults<ImageData> allTransactions = realm.where(ImageData.class).findAll();
            if(allTransactions.isEmpty())
                id_val=0;
            else{
                id_val=allTransactions.last().getId()+1;
            }
            //realm.commitTransaction();
            realm.close();

            realm=Realm.getDefaultInstance();

            realm.beginTransaction();
            ImageData id = new ImageData(url,id_val);
            realm.copyToRealmOrUpdate(id);
            realm.commitTransaction();
            Glide
                    .with(this)
                    .load(url)
                    .asBitmap()
                    .toBytes(Bitmap.CompressFormat.JPEG, 80)
                    .into(new SimpleTarget<byte[]>() {
                        @Override
                        public void onResourceReady(final byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... params) {
                                    File sdcard = Environment.getExternalStorageDirectory();
                                    File file = new File(sdcard + "/Imageurl/imageName.jpg");
                                    File dir = file.getParentFile();
                                    Log.v("getParentFile", dir.toString());
                                    try {
                                        if (!dir.mkdirs() && (!dir.exists() || !dir.isDirectory())) {
                                            throw new IOException("Cannot ensure parent directory for file " + file);
                                        }
                                        BufferedOutputStream s = new BufferedOutputStream(new FileOutputStream(file));
                                        s.write(resource);
                                        s.flush();
                                        s.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return null;
                                }
                            }.execute();
                        }
                    })
            ;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case EXT_STORAGE_PERM: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    storeToLocal(imageUrl);
                }
            }
            break;
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
