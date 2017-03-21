package com.cxy.firststudio.adapters;

import android.support.v4.app.Fragment;

import com.cxy.firststudio.Fragment.PagerFragment;

/**
 * Created by hasee on 2016/11/1.
 */
public class SectionsPager implements SectionsPagerAdapter . UnlimitedPager  {
    private final PagerFragment [ ] sFragments = new PagerFragment [ ] {
            PagerFragment . newInstance ( 0 ) ,
            PagerFragment . newInstance ( 1 ) ,
            PagerFragment. newInstance ( 2 )
    } ;

    private int mData = 1 ;

    @Override

    public void onRefresh ( ) {
        sFragments [ 0 ] . setText ( mData ) ;
        sFragments [ 1 ] . setText ( mData + 1 ) ;
        sFragments [ 2 ] . setText ( mData + 2 ) ;
    }

    @Override
    public void onChanged ( int offset ) {

        mData += offset ;

    }

    @Override
    public Fragment getItem ( int position ) {
        return sFragments [ position ] ;

    }
}
