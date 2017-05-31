package com.kysfw.www.sfw.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.kysfw.www.sfw.R;
import com.kysfw.www.sfw.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by manbas on 17-5-27.
 */

public class BigPhotoActivity extends AppCompatActivity {


    @BindView(R.id.vp_bigphoto)
    CustomViewPager vpBigphoto;
    @BindView(R.id.tv_num)
    TextView tvNum;

    private List<String> imgPath = new ArrayList<>();
    private int allnum;
    private int index;

    private List<View> views;
    private PhotoView[] photoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigphoto);
        ButterKnife.bind(this);
        imgPath = getIntent().getStringArrayListExtra("imgPath");
        allnum = imgPath.size();
        index = getIntent().getIntExtra("index", 0);
        tvNum.setText(index + "/" + allnum);
        initImage();
    }

    private void initImage() {
        photoView = new PhotoView[allnum];
        for (int i = 0; i < imgPath.size(); i++) {
            photoView[i] = new PhotoView(this);
            photoView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            Glide.with(this).load(imgPath.get(i)).placeholder(R.mipmap.defaulthouse_bg).into(photoView[i]);

        }
        vpBigphoto.setAdapter(adapter);
        vpBigphoto.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                tvNum.setText("" + (index + 1) + "/" + allnum);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return photoView.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((CustomViewPager) container).removeView(photoView[position % photoView.length]);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((CustomViewPager) container).addView(photoView[position % photoView.length], 0);
            return photoView[position % photoView.length];
        }
    };
}
