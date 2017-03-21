package com.cxy.firststudio.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxy.firststudio.R;

import java.util.Locale;

/**
 * Created by hasee on 2016/11/1.
 */
public class PagerFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number" ;

    public static PagerFragment newInstance ( int sectionNumber ) {

        PagerFragment fragment = new PagerFragment ( ) ;

        Bundle args = new Bundle ( ) ;

        args . putInt ( ARG_SECTION_NUMBER , sectionNumber ) ;

        fragment . setArguments ( args ) ;

        return fragment ;

    }

    public PagerFragment ( ) {

    }

    private View mRootView = null ;

    public void setText ( int sectionNumber ) {

        getArguments ( ) . putInt ( ARG_SECTION_NUMBER , sectionNumber ) ;

        if ( mRootView != null ) {

            TextView textView = ( TextView ) mRootView . findViewById ( R . id . section_label ) ;

            textView . setText ("Section ". toUpperCase ( Locale. getDefault ( ) ) + " " + sectionNumber ) ;

        }

    }

    @Override

    public View onCreateView ( LayoutInflater inflater , ViewGroup container ,

                               Bundle savedInstanceState ) {

        mRootView = inflater . inflate ( R . layout . fragment_pager , container , false ) ;

        setText ( getArguments ( ) . getInt ( ARG_SECTION_NUMBER ) ) ;

        return mRootView ;

    }
}
