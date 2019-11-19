package com.company.betterme.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.betterme.R;
import com.company.betterme.beans.Input;

import java.util.ArrayList;

import io.realm.RealmResults;

public class AdopterInputs extends RecyclerView.Adapter<AdopterInputs.InputHolder> {

    private LayoutInflater mInflater;

    //test
    private RealmResults<Input> mResults;

    public static final  String TAG = "T";

    public AdopterInputs(Context context, RealmResults<Input> results){
        mInflater = LayoutInflater.from(context);
       // mResults = results; bejae in mishe update function ro Ntekhab kard
        update(results);
    }


    public void update(RealmResults<Input> results){
        mResults = results;
        //notify the adopter that data base has changed
        notifyDataSetChanged();
    }

    //test
    public static ArrayList<String> generateValues(){
        ArrayList<String> values = new ArrayList<>();
        for (int i = 1; i < 101; i++){
            values.add("item" + i);
        }
        return values;
    }


    @NonNull
    @Override
    public InputHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view =  mInflater.inflate(R.layout.row_input,viewGroup,false);
        InputHolder holder = new InputHolder(view);
        Log.d(TAG, "onCreateViewHolder: ");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InputHolder inputHolder, int i) {
        Input input = mResults.get(i);
        inputHolder.mTextWhat.setText(input.getWhat());
        Log.d(TAG, "onBindViewHolder: " + i);

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public static class InputHolder extends RecyclerView.ViewHolder{

        TextView mTextWhat;
        public InputHolder(@NonNull View itemView) {
            super(itemView);
            mTextWhat = itemView.findViewById(R.id.tv_what);
        }
    }
}
