package com.cxy.firststudio.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.cxy.firststudio.Fragment.PagerFragment;

/**
 * Created by hasee on 2016/11/1.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public interface UnlimitedPager {

        public void onRefresh ( ) ;

        public void onChanged ( int offset ) ;

        public Fragment getItem ( int position ) ;

    }
    private ViewPager mViewPager = null ;

    private UnlimitedPager mPager = null ;

    private boolean mIsChanged = false ;
    public SectionsPagerAdapter(ViewPager vp, FragmentManager fm) {
        super(fm);
        this.mViewPager = vp;
    }

    public void setPage ( UnlimitedPager pager ) {
        mPager = pager ;
        if ( mPager != null ) {
            mPager . onRefresh ( ) ;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mPager . getItem ( position ) ;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void setPrimaryItem (ViewGroup container , int position , Object object ) {
        super . setPrimaryItem ( container , position , object ) ;
        if ( position == 1 ) {
            if ( mIsChanged ) {
                if ( mPager != null ) {
                    mPager . onRefresh ( ) ;
                }
                mIsChanged = false ;
            }
        }
        else {
            if ( mPager != null ) {
                mPager . onChanged ( position - 1 ) ;
                mIsChanged = true ;
            }
            mViewPager . setCurrentItem ( 1 , false ) ;
        }
    }
}
