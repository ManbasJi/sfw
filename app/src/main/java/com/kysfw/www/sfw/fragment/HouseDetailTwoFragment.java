package com.kysfw.www.sfw.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.utils.BaseUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/5/23.
 */

public class HouseDetailTwoFragment extends Fragment {

    public static HouseDetailTwoFragment getInstance(String response){
        HouseDetailTwoFragment f=new HouseDetailTwoFragment();
        Bundle b=new Bundle();
        b.putString("response",response);
        f.setArguments(b);
        return f;
    }

    View v;
    @BindView(R.id.right_text1)
    TextView rightText1;
    @BindView(R.id.right_text2)
    TextView rightText2;
    @BindView(R.id.right_text3)
    TextView rightText3;
    @BindView(R.id.right_text4)
    TextView rightText4;
    @BindView(R.id.right_text5)
    TextView rightText5;
    @BindView(R.id.tv_situation)
    TextView tvSituation;
    Unbinder unbinder;

    private String response;
    private String[] result;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_housedetailtwo, container, false);
        unbinder = ButterKnife.bind(this, v);
        response=getArguments().getString("response");
        result=response.split(",");
        setData();
        return v;
    }

    private void setData(){
        rightText1.setText(result[0]);
        rightText2.setText(BaseUtils.calculationPrice(Float.parseFloat(result[1])));
        rightText3.setText(result[2]+"㎡");
        rightText4.setText(result[3]);
        rightText5.setText(result[4]);
        tvSituation.setText(result[5]+"：");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
