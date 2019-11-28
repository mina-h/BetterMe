package com.company.betterme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.company.betterme.adapters.CompleteListener;

import io.realm.RealmChangeListener;

public class DialogMark extends DialogFragment {
    private ImageButton mBtnClose;
    private Button mBtnCompleted;
    private View.OnClickListener mBtnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            switch (v.getId()){
                case R.id.btn_completed:
                    //to mark the ite as completed
                markAsComplete();
                break;
            }
            dismiss();
        }
    };
    private CompleteListener mListener;


    private void markAsComplete(){
        Bundle arguments = getArguments();
        if(mListener != null && arguments != null){
                int position = arguments.getInt("POSITION");

                mListener.onComplete(position);
            }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mark, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClose = view.findViewById(R.id.btn_close);
        mBtnCompleted = view.findViewById(R.id.btn_completed);
        mBtnClose.setOnClickListener(mBtnClickListener);
        mBtnCompleted.setOnClickListener(mBtnClickListener);


    }

    public void setCompleteListener(CompleteListener mCompleteListener) {
        mListener = mCompleteListener;
    }
}
