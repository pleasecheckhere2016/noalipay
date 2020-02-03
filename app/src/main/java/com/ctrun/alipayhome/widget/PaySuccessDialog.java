package com.ctrun.alipayhome.widget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ctrun.alipayhome.R;
import com.lzj.pass.dialog.PayPassView;

public class PaySuccessDialog {

    private AlertDialog mDialog;//弹框
    private Window window;//窗口
    private Context mContext;//上下文
    private int mThemeResId;//主题
    private View mDialogLayout;//布局
    private MyAliPayView myAliPayView;

    private TextView noteText;

    /**
     * 默认样式
     * @param context
     */
    public PaySuccessDialog(Context context) {

        this.mContext = context;
        this.mThemeResId= R.style.dialog_pay_theme;
        this.mDialogLayout =  LayoutInflater.from(mContext).inflate(R.layout.pay_succss_pop,null);
        this.myAliPayView = mDialogLayout.findViewById(R.id.myAliPayView);
        this.noteText = mDialogLayout.findViewById(R.id.noteText);
        myAliPayView.setStatus(MyAliPayView.State.STATUS_PROCESS);
        mDialog=new AlertDialog.Builder(mContext,mThemeResId).create();
        mDialog.setCancelable(true);
        mDialog.show();



        mDialog.getWindow().setDimAmount(0.4f);//设置透明度0.4
        window = mDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setContentView(mDialogLayout);//设置弹框布局
        mDialog.setCanceledOnTouchOutside(false);
        window.setWindowAnimations(R.style.xxxx);  //添加弹框动画
        window.setGravity(Gravity.BOTTOM);//底部

    }

    /**
     * 得到PayPassView控件
     * @return
     */
    public PayPassView getPayViewPass() {
        return  mDialogLayout.findViewById(R.id.pay_View);

    }

    public void setFinish() {
        myAliPayView.setStatus(MyAliPayView.State.STATUS_FINISH);
        noteText.setText("付款成功");
    }

    /**
     *  自定义
     * @param context
     * @param themeResId  主题样式
     */
    public PaySuccessDialog(Context context, int themeResId) {
        this.mContext = context;
        this.mThemeResId=themeResId;
        this.mDialogLayout =   LayoutInflater.from(mContext).inflate(R.layout.view_paypass_dialog,null);
    }

    /**
     * 初始化Dialog
     */
    public PaySuccessDialog setAlertDialog(){
        mDialog=new AlertDialog.Builder(mContext,mThemeResId).create();
        mDialog.setCancelable(true);//按返回键退出
        mDialog.show();
        return this;
    }
    public PaySuccessDialog setAlertDialog(boolean isBack){
        mDialog=new AlertDialog.Builder(mContext,mThemeResId).create();
        mDialog.setCancelable(isBack);//按返回键退出
        mDialog.show();
        return this;
    }

    /**
     * 设置弹框大小 透明度
     */
    public PaySuccessDialog setWindowSize(int width, int height, float amount){
        mDialog.getWindow().setDimAmount(amount);//设置透明度
        window = mDialog.getWindow();
        window.setLayout(width,height);
        window.setContentView(mDialogLayout);//设置弹框布局
        return this;
    }
    /**
     * 设置弹框宽高   透明度
     * custom=2      自适应高度
     * custom=其他   指定高度
     */
    public PaySuccessDialog setWindowSize(int width, int height, int custom, float amount){
        if(custom==2){
            mDialog.getWindow().setDimAmount(amount);//设置透明度
            window = mDialog.getWindow();
            window.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
            window.setContentView(mDialogLayout);//设置弹框布局
            return this;
        }
        else {
            mDialog.getWindow().setDimAmount(amount);//设置透明度
            window = mDialog.getWindow();
            window.setLayout(width,height );
            window.setContentView(mDialogLayout);//设置弹框布局
            return this;
        }

    }
    /**
     * 点击外部消失
     */
    public PaySuccessDialog setOutColse(boolean isOut){
        if (isOut){
            mDialog.setCanceledOnTouchOutside(true);
        }
        else {
            mDialog.setCanceledOnTouchOutside(false);
        }
        return this;
    }

    /**
     * 方式 与位置
     */
    public PaySuccessDialog setGravity(int animation, int gravity){
        window.setWindowAnimations(animation);  //添加动画
        window.setGravity(gravity);             //底部
        return this;
    }

    /**
     * 关闭
     */

    public void dismiss() {
        if(mDialog!=null&&mDialog.isShowing()){
            mDialog.dismiss();
            mDialog=null;//清空对象
            window=null;
        }
    }
}
