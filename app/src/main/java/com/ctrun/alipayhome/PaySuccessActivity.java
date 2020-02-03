package com.ctrun.alipayhome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PaySuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_success);
    }


    public void toDetail(View view) {

        Intent intent = new Intent(view.getContext(),ZhangdanDetailActivity.class);
        startActivity(intent);
    }
}
