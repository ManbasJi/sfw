package com.kysfw.www.sfw.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kysfw.www.sfw.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2017/5/23.
 */

public class HouseDetailOneFragment extends Fragment {
    View view;
    LinearLayout rightLayout,leftLayout;
    String response="";

    public static HouseDetailOneFragment getInstance(String response){
        HouseDetailOneFragment fragment=new HouseDetailOneFragment();
        Bundle b=new Bundle();
        b.putString("response",response);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_housedetailone,container,false);
        rightLayout= (LinearLayout) view.findViewById(R.id.right_layout);
        leftLayout= (LinearLayout) view.findViewById(R.id.left_layout);
        response=getArguments().getString("response");

        JSONObject jsonObject=null;
        try {
            JSONArray array=new JSONArray(response);
            jsonObject=array.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            setData(jsonObject);
        return view;
    }

    private void setData(JSONObject jsonObject){
        for(int i = 0;i<14;i++){
            String str = ((TextView)leftLayout.getChildAt(i)).getText().toString();
            try {
                ((TextView)rightLayout.getChildAt(i)).setText(jsonObject.getString(
                        str.substring(0,str.length()-1)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
