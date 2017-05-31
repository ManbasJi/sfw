package com.kysfw.www.sfw.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.config.Constants;
import com.kysfw.www.sfw.model.UserInfo;
import com.kysfw.www.sfw.utils.LogUtils;
import com.kysfw.www.sfw.utils.SPUtils;
import com.kysfw.www.sfw.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by admin on 2017/5/19.
 */

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.edt_userName)
    EditText edtUserName;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cb_login)
    CheckBox checkBox;

    private String userName,password;
    private UserInfo userInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //toolbar设置
        toolbar.setTitle("用户登录");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //获取本地是否有用户登录信息
        String userinfo_str= (String) SPUtils.get(this,Constants.SP.USERINFO,"");
        userInfo=new Gson().fromJson(userinfo_str,UserInfo.class);
        if(userInfo!=null){
            edtUserName.setText(userInfo.userName);
            edtPassword.setText(userInfo.password);
            checkBox.setChecked(true);
        }

    }


    private void onLogin(){
        userName=edtUserName.getText().toString();
        password=edtPassword.getText().toString();

        if(userName.equals("") || password.equals("")){
            ToastUtils.showShort(this,"登录信息不能为空！");
            return;
        }

        //执行网络请求
        OkHttpUtils.post()
                .url(Constants.HOST+Constants.API.MANAGER_LOGIN)
                .addParams("user_id",userName)
                .addParams("user_pw",password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.d(e.getMessage());
                        ToastUtils.showShort(LoginActivity.this,"网络连接失败!");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        userInfo=new Gson().fromJson(response,UserInfo.class);
                        String userinfoStr=new Gson().toJson(userInfo);
                        //记住账户信息
                        if(checkBox.isChecked()){
                            SPUtils.put(LoginActivity.this,Constants.SP.USERINFO,userinfoStr);
                        }else{
                            SPUtils.remove(LoginActivity.this,Constants.SP.USERINFO);
                        }
                        ToastUtils.showShort(LoginActivity.this,"登录成功!");
                    }
                });
    }


    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        onLogin();
    }
}
