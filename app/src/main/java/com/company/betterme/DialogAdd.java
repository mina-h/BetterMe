package com.company.betterme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.company.betterme.beans.Input;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DialogAdd extends DialogFragment {

    private ImageButton mBtnClose;
    private EditText mInputWhat;
    private DatePicker mInputWhen;
    private Button mBtnAdd;

    //empty constructor
    public DialogAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnClose = view.findViewById(R.id.btn_close);
        mInputWhat = view.findViewById(R.id.et_add);
        mInputWhen = view.findViewById(R.id.date_picker);
        mBtnAdd = view.findViewById(R.id.btn_add_it);

        mBtnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    dismiss();
                                           }
                });

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAction();
                dismiss();
            }
        });

    }

    private void addAction() {

        //get user's input
        String what = mInputWhat.getText().toString();

        //get the time
        long now = System.currentTimeMillis();

        //add these two to an object and add them to realm
        Realm.init(getActivity());
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
        Realm realm = Realm.getDefaultInstance();
        Input input = new Input(what, now, 0, false);

        realm.beginTransaction();
        realm.copyToRealm(input);
        realm.commitTransaction();
        realm.close();



    }


}
