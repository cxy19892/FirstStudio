package com.cxy.firststudio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.cxy.firststudio.Utils.PlanListBean;
import com.cxy.firststudio.myviews.McolumnChart;
import com.cxy.firststudio.myviews.McolumnChart1;
import com.cxy.firststudio.myviews.MyClock;
import com.cxy.firststudio.myviews.MyDrawView1;
import com.cxy.firststudio.myviews.WaterWaveView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.red;

public class MainActivity extends AppCompatActivity {

    private MyDrawView1 myview;
    private Button btnclear, btngetbitmap, btnredcolor, btnlines;
    private WaterWaveView mWaterWaveView;
    private ListView LV;
    private ExpandableListView elv;
    private MyClock myClock;
    private McolumnChart1 m_McolumnChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews(){
        myview = (MyDrawView1) findViewById(R.id.myview1);
        btnclear = (Button) findViewById(R.id.button);
        btngetbitmap  = (Button) findViewById(R.id.button2);
        btnredcolor = (Button) findViewById(R.id.button3);
        btnlines = (Button) findViewById(R.id.button4);
        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myview.clear();
            }
        });
        btnredcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myview.setColor(Color.RED);
            }
        });
        btngetbitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveMyBitmap(getViewBitmap(myview));
                Intent intent = new Intent(MainActivity.this, SecendActivity.class);
                startActivity(intent);
            }
        });
        btnlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myview.setBeeLine();
            }
        });

        m_McolumnChart = (McolumnChart1) findViewById(R.id.mcolumnchart);

//        List<PlanListBean> mlist = new ArrayList<PlanListBean>();
//        for(int k = 0; k < 10 ; k++){
            PlanListBean mPlanListBean = new PlanListBean();
            mPlanListBean.setDateline(String.valueOf(20160303));
            mPlanListBean.setXiaolv((float) (0.65));
//            mlist.add(mPlanListBean);
//        }
        m_McolumnChart.setValue(mPlanListBean);








//        mWaterWaveView = (WaterWaveView) findViewById(R.id.wave_view);
//        mWaterWaveView.setmWaterLevel(0.8f);
//        mWaterWaveView.startWave();

//        myClock = (MyClock) findViewById(R.id.my_oclock);
//        myClock.setTime(10, 45, true);
    }


    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("Folder", "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);


        return bitmap;

    }

    public String saveMyBitmap(Bitmap mBitmap)  {

        File file = new File(Environment.getExternalStorageDirectory() + "/data/yzm/compress");
        if(!file.exists())
            file.mkdirs();
        // 创建一个以当前时间为名称的文件
        File tempFile = new File(file.getAbsolutePath(), "temp_publish"+ ("__WOHUDE")
                + (Math.random() * 1000 + 1)+".jpg");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(tempFile);
            boolean isSave = mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            mBitmap.recycle();
            System.gc();
            if(isSave){
                return tempFile.getAbsolutePath();
            }
            else{
                return null;
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (OutOfMemoryError e) {
            return null;
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        mWaterWaveView.stopWave();
        mWaterWaveView=null;
        super.onDestroy();
    }
}
