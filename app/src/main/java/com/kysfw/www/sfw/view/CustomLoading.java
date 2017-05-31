package com.kysfw.www.sfw.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.kysfw.www.sfw.R;


/**
 * Created by admin on 2017/5/19.
 */

public class CustomLoading extends Dialog {
    private Context context;


    public CustomLoading(@NonNull Context context, int theme) {
        super(context,theme);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_loading,null);
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setDialogScreen();
    }

    private void setDialogScreen(){
        Window dialogWindow=getWindow();
        WindowManager manager=((Activity)context).getWindowManager();
        WindowManager.LayoutParams params=dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        Display d=manager.getDefaultDisplay();
        params.width= (int) (d.getWidth()*0.5);
        dialogWindow.setAttributes(params);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void show() {
        super.show();
    }

    public void dismiss(String message){
        dismiss();
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
