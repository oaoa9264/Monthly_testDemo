package com.bwie.monthly_testdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bwie.monthly_testdemo.Adapter.Myadapter;
import com.bwie.monthly_testdemo.bean.Bean;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;

import android.text.format.Formatter;

import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends AppCompatActivity {

    private Button bnt, clean;
    private RecyclerView recycler;
    private String path = "http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=10&gender=2&ts=1871746850&page=1";
    private List<Bean.DataBean> dataBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnt = (Button) findViewById(R.id.bnt);
        clean = (Button) findViewById(R.id.clean);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recycler.setLayoutManager(layoutManager);

        getData();

        bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, LocationDemo.class);
                startActivity(in);
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.getInstance().clearDiskCache();
                ImageLoader.getInstance().clearMemoryCache();
                clean.setText("清除缓存0KB");
            }
        });

    }

    private void getData() {
        OkHttpUtils.get().url(path).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Bean bean = new Gson().fromJson(response, Bean.class);
                dataBeanList.addAll(bean.getData());

                Myadapter mAdapter = new Myadapter(MainActivity.this, dataBeanList);

                recycler.setAdapter(mAdapter);

                File file = getExternalStorageDirectory();

                long len = file.length();
                String s = Formatter.formatFileSize(MainActivity.this, Long.valueOf(len));
                clean.setText("清除缓存" + s);

            }
        });

    }


}
