package com.kysfw.www.sfw.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.config.Constants;
import com.kysfw.www.sfw.view.GlideCircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/5/23.
 */

public class HouseDetailThreeFragment extends Fragment {
    public static HouseDetailThreeFragment getInstance(Bundle b){
        HouseDetailThreeFragment f=new HouseDetailThreeFragment();
        if(b!=null){
            f.setArguments(b);
        }
        return f;
    }

    View v;
    @BindView(R.id.right_image)
    ImageView rightImage;
    @BindView(R.id.right_text2)
    TextView rightText2;
    @BindView(R.id.right_text3)
    TextView rightText3;
    Unbinder unbinder;

    Bundle b=new Bundle();

    //经纪人信息
    private String photo;
    private String name;
    private String phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_housedetailthree, container, false);
        unbinder = ButterKnife.bind(this, v);
        b=getArguments();
        phone=b.getString("photo");
        name=b.getString("name");
        phone=b.getString("phone");

        Glide.with(getActivity()).load(Constants.HOST+photo)
                .transform(new GlideCircleTransform(getActivity()))
                .error(R.mipmap.default_photo)
                .into(rightImage);
        rightText2.setText(name);
        rightText3.setText(phone);
        return v;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
