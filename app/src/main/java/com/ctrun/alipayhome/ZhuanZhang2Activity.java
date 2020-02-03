package com.ctrun.alipayhome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.ctrun.alipayhome.widget.MPayPassDialog;
import com.ctrun.alipayhome.widget.MdStyleProgress;
import com.ctrun.alipayhome.widget.PaySuccessDialog;
import com.lzj.pass.dialog.PayPassDialog;
import com.lzj.pass.dialog.PayPassView;

public class ZhuanZhang2Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuanzhang);
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void showMima(View view) {
        payDialog();
    }

    private void payDialog() {
        final MPayPassDialog dialog=new MPayPassDialog(this);

        PayPassView payViewPass = dialog.getPayViewPass();
        payViewPass.setForgetText("找回密码并支付                                    ");
        payViewPass.setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        dialog.dismiss();
                        PaySuccessDialog paySuccessDialog = new PaySuccessDialog(ZhuanZhang2Activity.this);

                        new Thread(() -> {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ZhuanZhang2Activity.this.runOnUiThread(() -> {
                                paySuccessDialog.setFinish();

                            });

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            paySuccessDialog.dismiss();

                            Intent intent = new Intent(getApplicationContext(),PaySuccessActivity.class);
                            startActivity(intent);

                        }).start();

                    }
                    @Override
                    public void onPayClose() {
                        dialog.dismiss();
                        //关闭弹框
                    }
                    @Override
                    public void onPayForget() {
                        dialog.dismiss();
                        //点击忘记密码回调
                    }
                }
                );
    }
}
