package com.kysfw.www.sfw.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.config.Constants;
import com.kysfw.www.sfw.model.AreaModel;
import com.kysfw.www.sfw.model.HouseTypeModel;
import com.kysfw.www.sfw.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by admin on 2017/5/22.
 */

public class SearchHouseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_houseType)
    TextView tvHouseType;
    @BindView(R.id.tv_price1)
    EditText tvPrice1;
    @BindView(R.id.tv_price2)
    EditText tvPrice2;
    @BindView(R.id.tv_areaSize1)
    EditText tvAreaSize1;
    @BindView(R.id.tv_areaSize2)
    EditText tvAreaSize2;
    @BindView(R.id.tv_datastart)
    TextView tvDatastart;
    @BindView(R.id.tv_dataend)
    TextView tvDataend;
    @BindView(R.id.cb_all)
    RadioButton cbAll;
    @BindView(R.id.cb_one)
    RadioButton cbOne;
    @BindView(R.id.cb_two)
    RadioButton cbTwo;
    @BindView(R.id.tv_changname)
    TextView tvChangname;
    @BindView(R.id.cb_changone)
    RadioButton cbChangone;
    @BindView(R.id.cb_changtwo)
    RadioButton cbChangtwo;
    @BindView(R.id.layout_change)
    LinearLayout layoutChange;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.edt_houseaddress)
    EditText edtHouseaddress;
    @BindView(R.id.rg1)
    RadioGroup rg1;
    @BindView(R.id.rg2)
    RadioGroup rg2;

    private AreaModel areaModel;
    private String selectProvince="";
    private String selectCity="";
    private String selectHouseType="";
    private List<AreaModel.City> cities;
    private List<HouseTypeModel> houseTypeList;


    private String sale_situation="";
    private String selectHouseTypet="";
    private String dialogType;//provice;city;houseType;
    private String[] housetypeStr={"请选择","一房一","二房一","二房二","三房一","三房二","四房一","四房二","五房一",
                        "五房二","其他"};

    private int provinceIndex,cityIndex,houseTypeIndex;//上一次选中的index

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchhouse);
        ButterKnife.bind(this);
        //解析省市区json文件
        areaModel = new Gson().fromJson(getAreaJson(), AreaModel.class);
        //houseType Source
        houseTypeList=new ArrayList<>();
        for(int i=0;i<housetypeStr.length;i++){
            HouseTypeModel houseTypeModel=new HouseTypeModel();
            houseTypeModel.n=housetypeStr[i];
            houseTypeList.add(houseTypeModel);
        }
        //toolbar
        toolbar.setTitle("房源筛选");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        toolbar.setNavigationIcon(R.mipmap.back_icon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //radiogroup点击事件
        onListener();
        initData();
    }

    private void onListener(){

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.cb_all:
                        layoutChange.setVisibility(View.GONE);
                        selectHouseTypet= (String) cbAll.getText();
                        sale_situation="";
                        break;
                    case R.id.cb_one:
                        selectHouseTypet= (String) cbOne.getText();
                        tvChangname.setText("一手状态:");
                        cbChangone.setText("开发中");
                        cbChangtwo.setText("可预售");
                        layoutChange.setVisibility(View.VISIBLE);
                        break;
                    case R.id.cb_two:
                        selectHouseTypet= (String) cbTwo.getText();
                        layoutChange.setVisibility(View.VISIBLE);
                        tvChangname.setText("交易方式:");
                        cbChangone.setText("出售");
                        cbChangtwo.setText("出租");
                        break;
                }
            }
        });
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.cb_changone:
                        sale_situation= (String) cbChangone.getText();
                        break;
                    case R.id.cb_changtwo:
                        sale_situation= (String) cbChangtwo.getText();
                        break;
                }
            }
        });
    }

    //设置默认数据
    private void initData(){
        provinceIndex=18;//18是根据省份数据进行计算的index,写死 广东，方式不太好
        cities=areaModel.citylist.get(provinceIndex).c;
        cityIndex=4;//写死  汕头

        tvProvince.setText(areaModel.citylist.get(provinceIndex).p);
        tvCity.setText(cities.get(cityIndex).n);

        selectProvince=tvProvince.getText().toString();
        selectCity=tvCity.getText().toString();

        areaModel.citylist.get(provinceIndex).isSelect=true;
        cities.get(cityIndex).isSelect=true;

    }

    //列表dialog
    private void initDialogList(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.dialog_list,null);
        builder.setView(view);
        final Dialog dialog=builder.create();
        ListView lv= (ListView) view.findViewById(R.id.lv_dialoglist);
        final ListAdapter adapter=new ListAdapter();
        lv.setAdapter(adapter);
        //有一个对话框，设置为三种状态，加载三种不同数据
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(dialogType.equals("province")){
                    selectProvince=areaModel.citylist.get(position).p;
                    areaModel.citylist.get(provinceIndex).isSelect=false;
                    areaModel.citylist.get(position).isSelect=true;
                    cities=areaModel.citylist.get(position).c;

                    provinceIndex=position;
                    tvProvince.setText(selectProvince);//设置text

                    cityIndex=0;//选中省份之后 城市数据 会重新 变动 默认是第一个
                    tvCity.setText(cities.get(cityIndex).n);
                    selectCity=tvCity.getText().toString();

                } else if(dialogType.equals("city")){
                    selectCity=cities.get(position).n;
                    cities.get(cityIndex).isSelect=false;
                    cities.get(position).isSelect=true;

                    cityIndex=position;
                    tvCity.setText(selectCity);
                } else if(dialogType.equals("houseType")){
                    selectHouseType=houseTypeList.get(position).n;
                    houseTypeList.get(houseTypeIndex).isSelect=false;//将上次选中的item设置为false
                    houseTypeList.get(position).isSelect=true;//将这次选中的item设置为true

                    houseTypeIndex=position;//
                    tvHouseType.setText(selectHouseType);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //日期选择dialog
    private void initDateDialog(final TextView tv){
        Calendar c=Calendar.getInstance();
        Dialog dialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv.setText(year+"-"+(month+1)+"-"+dayOfMonth+"");
            }
        },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        dialog.setTitle("选择日期");
        dialog.show();
    }


    @OnClick({R.id.tv_province, R.id.tv_city, R.id.tv_houseType, R.id.tv_datastart, R.id.tv_dataend, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_province:
                dialogType="province";
                initDialogList();
                break;
            case R.id.tv_city:
                dialogType="city";
                initDialogList();
                break;
            case R.id.tv_houseType:
                dialogType="houseType";
                initDialogList();
                break;
            case R.id.tv_datastart:
                initDateDialog(tvDatastart);
                break;
            case R.id.tv_dataend:
                initDateDialog(tvDataend);
                break;
            case R.id.tv_search:
                Intent intent=new Intent(SearchHouseActivity.this,SearchResultActivity.class);
                intent.putExtras(initBundle());
                startActivity(intent);
                break;
        }
    }
    private Bundle initBundle(){
        Bundle b=new Bundle();
        b.putString("house_address",selectProvince+selectCity+edtHouseaddress.getText().toString());
       b.putString("house_type",tvHouseType.getText().toString().equals("请选择")?"":tvHouseType.getText().toString());
       b.putString("hope_price1",tvPrice1.getText().toString());
       b.putString("hope_price2",tvPrice2.getText().toString());
       b.putString("house_size1",tvAreaSize1.getText().toString());
       b.putString("house_size2",tvAreaSize2.getText().toString());
       b.putString("create_time",tvDatastart.getText().toString());
       b.putString("finish_time",tvDataend.getText().toString());
       b.putString("house_typet",selectHouseTypet);
       b.putString("sale_situation",sale_situation);
        return b;
    }


    //读取assets中的json文件，获取省市区json
    private String getAreaJson() {
        JSONObject mJsonObj=null;
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("city.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "gbk"));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mJsonObj.toString();
    }


    class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(dialogType.equals("province")){
                return areaModel.citylist.size();
            } else if(dialogType.equals("city")){
                return cities!=null?cities.size():0;
            } else if(dialogType.equals("houseType")){
                return houseTypeList!=null?houseTypeList.size():0;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if(dialogType.equals("province")){
                areaModel.citylist.get(position);
            } else if(dialogType.equals("city")){
                cities.get(position);
            } else if(dialogType.equals("houseType")){
                houseTypeList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv_dialoglist=null;
            if(convertView==null){
                convertView=getLayoutInflater().inflate(R.layout.item_dialoglist,null);
                tv_dialoglist= (TextView) convertView.findViewById(R.id.tv_dialoglist);
                convertView.setTag(tv_dialoglist);
            }else{
                tv_dialoglist= (TextView) convertView.getTag();
            }
            if(dialogType.equals("province")){
                LogUtils.d("province index:"+position);
                tv_dialoglist.setText(areaModel.citylist.get(position).p);
                if(areaModel.citylist.get(position).isSelect){
                    tv_dialoglist.setTextColor(getResources().getColor(R.color.main_blue));
                }else{
                    tv_dialoglist.setTextColor(getResources().getColor(R.color.text_01));
                }
            } else if(dialogType.equals("city")){
                tv_dialoglist.setText(cities.get(position).n);
                if(cities.get(position).isSelect){
                    tv_dialoglist.setTextColor(getResources().getColor(R.color.main_blue));
                }else{
                    tv_dialoglist.setTextColor(getResources().getColor(R.color.text_01));
                }
            } else if(dialogType.equals("houseType")){
                tv_dialoglist.setText(houseTypeList.get(position).n);
                if(houseTypeList.get(position).isSelect){
                    tv_dialoglist.setTextColor(getResources().getColor(R.color.main_blue));
                }else{
                    tv_dialoglist.setTextColor(getResources().getColor(R.color.text_01));
                }
            }
            return convertView;
        }
    }
}
