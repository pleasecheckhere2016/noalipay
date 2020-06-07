package com.ctrun.alipayhome;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ZhangdanDetailActivity extends Activity {

    TextView payTime;
    TextView payNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zhuanzhangdetail);

        payTime = findViewById(R.id.payTime);
        payNo = findViewById(R.id.payNo);


        //2020-3-10 12:59
        SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        payTime.setText(myFmt2.format(new Date()));
        //202002037200
        SimpleDateFormat myFmt3=new SimpleDateFormat("yyyyMMddHHmm");
//        payNo.setText(myFmt3.format(new Date()) + "040011100980014779964");
    }
}
