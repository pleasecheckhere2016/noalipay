package com.ctrun.alipayhome;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_activity);
        SharedPreferences preferences = getSharedPreferences("asserts", MODE_PRIVATE);
        String[] keys = preferences.getString("key", "////////").split("/");
        ((TextView)findViewById(R.id.yue)).setText(keys[2] + "元");
    }


    public void toMyAsserts(View view) {

        Intent intent = new Intent(view.getContext(),MyAssertsActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(view.getContext(),MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void setting(View view) {
        SharedPreferences preferences = getSharedPreferences("asserts", MODE_PRIVATE);
        final EditText inputServer = new EditText(this);
        inputServer.setText(preferences.getString("key",""));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Server").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                .setNegativeButton("Cancel", null);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                preferences.edit().putString("key",inputServer.getText().toString()).apply();
            }
        });
        builder.show();
    }
}
