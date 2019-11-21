package com.company.betterme.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.company.betterme.R;

public class Divider extends RecyclerView.ItemDecoration {

    public static final String TAG = "mm";
    private Drawable mDivider;
    private int mOrientation;

    public Divider(Context context, int orientation){
        mDivider = context.getDrawable(R.drawable.divider);
        Log.d(TAG, "Divider");
        //check if its vertical
        if(orientation != LinearLayoutManager.VERTICAL){
            throw new IllegalArgumentException("This Item Decoration can be used only with a RecyclerView that a LinearLayoutManager with vertical orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //if the orientation is vertical draw a horizontal line
        if (mOrientation == LinearLayoutManager.VERTICAL){
            drawHorizontalDivider(c, parent, state);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left, top, right, bottom;
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight(); //recyclerView width
        int count = parent.getChildCount();
        //loo through each child
        for (int i = 0; i < count; i++){
            //not draw a divider if the item is the footer
            if(AdopterInputs.FOOTER != parent.getAdapter().getItemViewType(i)){
                View current = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) current.getLayoutParams();
                top = current.getTop() - params.topMargin;
                Log.d(TAG, "draw "+ top);
                bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
                Log.d(TAG, "drawHorizontalDivider: l: "+ left + " t:" + top + " r:" + right + " b:" + bottom);
            }

        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL){
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }


    }
}
