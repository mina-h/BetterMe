package com.company.betterme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import  android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.company.betterme.adapters.AddListener;
import com.company.betterme.adapters.AdopterInputs;
import com.company.betterme.adapters.CompleteListener;
import com.company.betterme.adapters.Divider;
import com.company.betterme.adapters.MarkListener;
import com.company.betterme.adapters.SimpleTouchCallback;
import com.company.betterme.beans.Input;
import com.company.betterme.widgets.InputRecyclerView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity  {


    private static final String TAG = "MainActivity";
    Toolbar mToolbar;
    Button  mBtnAdd;
    InputRecyclerView mRecycler;
    //create a realm object to query data
    Realm mRealm;
    RealmResults<Input> mResults;
    View mEmptyView;
    AdopterInputs mAdopter;
    private AddListener mAddListener = new AddListener() {
        @Override
        public void add() {
            showDialogAdd();

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing realm
        mRealm = Realm.getDefaultInstance(); //but it must be set first / by making a specific class (App) for app and
        // add the values there, there is no need to do it every time

        //making a query
        mResults = mRealm.where(Input.class).findAllAsync();

        mBtnAdd = findViewById(R.id.btn_add);
        mToolbar = findViewById(R.id.toolbar);
        mEmptyView = findViewById(R.id.empty);

        mRecycler = findViewById(R.id.rv_inputs);
        mRecycler.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        //hide toolbar when the recycle view is empty
        mRecycler.hideIfEmpty(mToolbar);
        //take the empty layout and show it when recycler view is empty
        mRecycler.showIfEmpty(mEmptyView);

        mAdopter = new AdopterInputs(this, mRealm, mResults, mAddListener, mMarkListener);
        mRecycler.setAdapter(mAdopter);
        SimpleTouchCallback callback = new SimpleTouchCallback(mAdopter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecycler);


//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        mRecycler.setLayoutManager(manager);



       // mRecycler.setAdapter(new AdopterInputs(this, mResults));

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(0xFFFFFFFF);



        //Addjust the image by Glide library so that it can be shown perfectly on any device
        initBackgroundImage();

    }

    // to get notified when a Realm instance has been updated
    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {
            Log.d(TAG, "onChange: ");
            mAdopter.update(mResults);
        }
    };


    private MarkListener mMarkListener = new MarkListener() {
        @Override
        public void onMark(int position) {
            showDialogMark(position);
        }
    };

    private CompleteListener mCompleteListener = new CompleteListener() {
        @Override
        public void onComplete(int position) {
           // Toast.makeText(MainActivity.this, "position in activity" + position, Toast.LENGTH_SHORT).show();
            mAdopter.markComplete(position);
        }
    };

    private void showDialogAdd() {
        //create an object of dialog
        DialogAdd dialog = new DialogAdd();
        //show the dialog by passing fragment manager
        dialog.show(getSupportFragmentManager(), "Add");

    }

    private void showDialogMark(int position){
        DialogMark dialog = new DialogMark();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        dialog.setArguments(bundle);
        dialog.setCompleteListener(mCompleteListener);
        dialog.show(getSupportFragmentManager(), "Mark");
    }

    @Override
    protected void onStart(){
        super.onStart();
        // The listeners will be executed when changes are committed
        mResults.addChangeListener(mChangeListener);

    }

    @Override
    protected void onStop(){
        super.onStop();
        //Removes the change listener
        mResults.removeChangeListener(mChangeListener);
    }

    private void initBackgroundImage(){
        ImageView background = findViewById(R.id.iv_background);
        Glide.with(this)
                .load(R.drawable.background)
                .centerCrop()
                .into(background);
    }


}
