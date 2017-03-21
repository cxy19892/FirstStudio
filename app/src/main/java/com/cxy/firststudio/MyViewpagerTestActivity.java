package com.cxy.firststudio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cxy.firststudio.adapters.SectionsPager;
import com.cxy.firststudio.adapters.SectionsPagerAdapter;

/**
 * 无限多页面viewpager
 * Created by hasee on 2016/11/1.
 */
public class MyViewpagerTestActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    SectionsPagerAdapter mSectionsPagerAdapter ;
    ViewPager mViewPager ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_activity);

        mViewPager = ( ViewPager ) findViewById ( R . id . pager ) ;
        mSectionsPagerAdapter = new SectionsPagerAdapter (mViewPager , getSupportFragmentManager ( ) ) ;
        mSectionsPagerAdapter . setPage ( new SectionsPager( ) ) ;
        mViewPager.setAdapter(mSectionsPagerAdapter) ;
        mViewPager.addOnPageChangeListener(this);mViewPager.setCurrentItem(mViewPager.getCurrentItem());    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("MyViewpagerTestActivity", "onPageSelected: "+position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
