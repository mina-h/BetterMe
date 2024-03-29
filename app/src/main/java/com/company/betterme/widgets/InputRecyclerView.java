package com.company.betterme.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.company.betterme.extras.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InputRecyclerView extends RecyclerView {


    private static final String TAG = "tag";
    private List<View> mNonEmptyViews = Collections.emptyList();
    private List<View> mEmptyViews = Collections.emptyList();

    //instance of adapter adopterdataobserver
    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            toggleViews();
            Log.d(TAG, "toggleViews");

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            toggleViews();

        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();

        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();

        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();

        }
    };

    private void toggleViews() {
        if(getAdapter()!=null && !mEmptyViews.isEmpty() && !mNonEmptyViews.isEmpty()){
            if(getAdapter().getItemCount()==0){

                //show all the empty views
                Util.showViews(mEmptyViews);


                //hide recyclerview itself when there is no item
                setVisibility(View.GONE);

                //hide all the views which are meant to be hidden
                Util.hideViews(mNonEmptyViews);
            }
            else{

                //hide all the empty views
                Util.showViews(mNonEmptyViews);


                //show the recycler view when there are items
                setVisibility(View.VISIBLE);

                //hide all the views which are meant to be hidden
                Util.hideViews(mEmptyViews);
//                Log.d(TAG, "toggleViews");

            }
        }
    }

    //initialize the recyclerview from code
    public InputRecyclerView(@NonNull Context context) {
        super(context);
    }

    //initialize the recyclerview from xml
    public InputRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    //initialize the recyclerview from xml
    public InputRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter){
        super.setAdapter(adapter);
            if(adapter != null){
                adapter.registerAdapterDataObserver(mObserver);
            }
            mObserver.onChanged();
    }


    public void hideIfEmpty(View ...views) {
        mNonEmptyViews = Arrays.asList(views);

    }

    public void showIfEmpty(View ...emptyViews) {
        mEmptyViews = Arrays.asList(emptyViews);
    }
}
