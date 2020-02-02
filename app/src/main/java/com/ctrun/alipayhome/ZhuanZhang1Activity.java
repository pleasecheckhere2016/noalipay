package com.ctrun.alipayhome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ZhuanZhang1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shuruzhanghao);
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void toZhuanZhang(View view) {

        Intent intent = new Intent(view.getContext(),ZhuanZhang2Activity.class);
        startActivity(intent);
    }
}
