package com.company.betterme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import  android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity  {

    Toolbar mToolbar;
    Button  mBtnAdd;
    RecyclerView mRecycker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnAdd = findViewById(R.id.btn_add);
        mRecycker = findViewById(R.id.rv_inputs);

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(0xFFFFFFFF);

        //Addjust the image by Glide library so that it can be shown perfectly on any device
        initBackgroundImage();

    }

    private void showDialogAdd() {
        //create an object of dialog
        DialogAdd dialog = new DialogAdd();
        //show the dialog by passing fragment manager
        dialog.show(getSupportFragmentManager(), "Add");

    }

    private void initBackgroundImage(){
        ImageView background = findViewById(R.id.iv_background);
        Glide.with(this)
                .load(R.drawable.background)
                .centerCrop()
                .into(background);
    }


}
