package com.company.betterme;

import java.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.company.betterme.beans.Input;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.company.betterme.adapters.AdopterInputs.TAG;

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

    //TODO process date

    private void addAction() {

        //get user's input
        String what = mInputWhat.getText().toString();
        String date = mInputWhen.getDayOfMonth() + "/" + mInputWhen.getMonth() + "/" + mInputWhen.getYear();
        //Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, mInputWhen.getDayOfMonth());
        calendar.set(Calendar.MONTH, mInputWhen.getMonth());
        calendar.set(Calendar.YEAR, mInputWhen.getYear());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);


        //get the time
        long now = System.currentTimeMillis();

        //add these two to an object and add them to realm

        Realm realm = Realm.getDefaultInstance();

        Input input = new Input(what, now, calendar.getTimeInMillis(), false);

        realm.beginTransaction();
        realm.copyToRealm(input);
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, "aaa");



    }


}
