package com.ctrun.alipayhome;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyAssertsActivity extends AppCompatActivity {

    private String zongzichan = "";
    private String zuorishouyi = "";
    private String yue = "";
    private String yuebao = "";
    private String yuebaoshouyi = "";
    private String huangjin = "";
    private String huangjinshouyi = "";
    private String shouyi = "";
    private String shouyilv = "";
    private String jijin = "";

    private String jijinshouyi = "";

    private TextView[] textViews = new TextView[11];

    private TextView riqi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_assets);

        textViews[0] = (TextView) findViewById(R.id.zongzichan);
        textViews[1] = (TextView) findViewById(R.id.zuorishouyi);
        textViews[2] = (TextView) findViewById(R.id.yue);
        textViews[3] = (TextView) findViewById(R.id.yuebao);
        textViews[4] = (TextView) findViewById(R.id.yuebaoshouyi);
        textViews[5] = (TextView) findViewById(R.id.huangjin);
        textViews[6] = (TextView) findViewById(R.id.huangjinshouyi);
        textViews[7] = (TextView) findViewById(R.id.shouyi);
        textViews[8] = (TextView) findViewById(R.id.shouyilv);
        textViews[9] = (TextView) findViewById(R.id.jijin);
        textViews[10] = (TextView) findViewById(R.id.jijinshouyi);


        riqi = (TextView) findViewById(R.id.riqi);


        riqi.setText(String.format("2020年理财成绩（截止到%s）",new SimpleDateFormat("MM-dd").format(new Date())));



        SharedPreferences preferences = getSharedPreferences("asserts", MODE_PRIVATE);
        String[] keys = preferences.getString("key", "////////").split("/");

        if(keys.length == 11) {
            zongzichan = keys[0];
            zuorishouyi = keys[1];
            yue = keys[2];
            yuebao = keys[3];
            yuebaoshouyi = keys[4];
            huangjin = keys[5];
            huangjinshouyi = keys[6];
            shouyi = keys[7];
            shouyilv = keys[8];
            jijin = keys[9];
            jijinshouyi = keys[10];

            for (int i = 0; i < keys.length; i++) {
                textViews[i].setText(keys[i]);
                String replace = keys[i]
                        .replace(",", "")
                        .replace(".","");

                String color = null;

                if(replace.startsWith("+")) {
                    color = "#E05723";

                } else if(replace.startsWith("-")) {

                    color = "#228B22";
                }

                if(color != null) {
                    textViews[i].setTextColor(Color.parseColor(color));
                }
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }


}
