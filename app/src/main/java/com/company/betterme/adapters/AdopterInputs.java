package com.company.betterme.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.company.betterme.R;
import com.company.betterme.beans.Input;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class AdopterInputs extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {


    public static final int ITEM = 0;
    public static final int FOOTER = 1;
    private LayoutInflater mInflater;
    //test
    private RealmResults<Input> mResults;
    public static final  String TAG = "T";
    private AddListener mAddListener;
    //not sure if its the currect usage but it works:
    private Realm mRealm; //= Realm.getDefaultInstance();

    public AdopterInputs(Context context, Realm realm, RealmResults<Input> results){
        mInflater = LayoutInflater.from(context);
        mRealm = realm;
       // mResults = results; bejae in mishe update function ro Ntekhab kard
        update(results);
    }

    public AdopterInputs(Context context, Realm realm, RealmResults<Input> results, AddListener listener){
        mInflater = LayoutInflater.from(context);
        // mResults = results; bejae in mishe update function ro Ntekhab kard
        update(results);
        mRealm = realm;
        mAddListener = listener;
    }




    public void update(RealmResults<Input> results){
        mResults = results;
        //notify the adopter that data base has changed
        notifyDataSetChanged();
    }

    //test
//    public static ArrayList<String> generateValues(){
//        ArrayList<String> values = new ArrayList<>();
//        for (int i = 1; i < 101; i++){
//            values.add("item" + i);
//        }
//        return values;
//    }


    @Override
    public int getItemViewType(int position) {

        if(mResults==null || position<mResults.size()){
            return ITEM;
        }
        else {
            return FOOTER;
        }


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == FOOTER){
            View view =  mInflater.inflate(R.layout.footer,viewGroup,false);
            return new FooteHolder(view, mAddListener);

        }


        View view =  mInflater.inflate(R.layout.row_input,viewGroup,false);
        return new InputHolder(view);
       // Log.d(TAG, "onCreateViewHolder: ");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if(holder instanceof InputHolder){
            InputHolder inputHolder = (InputHolder) holder;
            Input input = mResults.get(i);
            inputHolder.mTextWhat.setText(input.getWhat());

        }

       // Log.d(TAG, "onBindViewHolder: " + i);

    }

    @Override
    public int getItemCount() {
        if(mResults == null || mResults.isEmpty()){
            return 0;
        }else {
            return mResults.size() + 1;
        }
    }

    @Override
    public void onSwipe(int position) {
        if(position<mResults.size()){
            //deleting an item from database
            mRealm.beginTransaction();
            mResults.get(position).deleteFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);
        }

    }

    public static class InputHolder extends RecyclerView.ViewHolder{

        TextView mTextWhat;
        public InputHolder(@NonNull View itemView) {
            super(itemView);
            mTextWhat = itemView.findViewById(R.id.tv_what);
        }
    }

    public static class FooteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button mBtnAdd;
        AddListener mListener;
        public FooteHolder(@NonNull View itemView) {
            super(itemView);
            mBtnAdd = itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this);
        }

        public FooteHolder(@NonNull View itemView, AddListener listener) {
            super(itemView);
            mBtnAdd = itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this);
            mListener = listener;
        }


        @Override
        public void onClick(View v) {
            mListener.add();
        }
    }
}
