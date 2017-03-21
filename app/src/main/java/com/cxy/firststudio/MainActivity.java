package com.cxy.firststudio;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxy.firststudio.Utils.PlanListBean;
import com.cxy.firststudio.myviews.AnimDownloadProgressButton;
import com.cxy.firststudio.myviews.ArcMenu;
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
    private TextView mtv;
    private AnimDownloadProgressButton mProgressBtn;
    String[] ITEM_DRAWABLES = {"私人","医生","未读","咨询","全部"};

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
        mtv = (TextView) findViewById(R.id.text);
        mProgressBtn = (AnimDownloadProgressButton) findViewById(R.id.progressbutton);
        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,null , MediaStore.Images.Media.DATA+" DESC");
                while (cursor.moveToNext()) {
                    //获取图片的名称
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    //获取图片的生成日期
                    String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    //获取图片的详细信息
                    String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
                    Log.d("chen", "onClick: "+data);
                }
            }
        });
        btnredcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myview.setColor(Color.RED);
                Intent intent = new Intent(MainActivity.this, MyViewpagerTestActivity.class);
                startActivity(intent);
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

//        m_McolumnChart = (McolumnChart1) findViewById(R.id.mcolumnchart);

//        List<PlanListBean> mlist = new ArrayList<PlanListBean>();
//        for(int k = 0; k < 10 ; k++){
            PlanListBean mPlanListBean = new PlanListBean();
            mPlanListBean.setDateline(String.valueOf(20160303));
            mPlanListBean.setXiaolv((float) (0.65));
//            mlist.add(mPlanListBean);
//        }
//        m_McolumnChart.setValue(mPlanListBean);



        mtv.setText(createSpannable("nishi", "ibvhn    obw pwbvinioboiubn "));




        mWaterWaveView = (WaterWaveView) findViewById(R.id.wave_view);
        mWaterWaveView.setmWaterLevel(0.8f);
        mWaterWaveView.startWave();

        myClock = (MyClock) findViewById(R.id.my_oclock);
        myClock.setTime(10, 45, true);
        mProgressBtn.setMaxProgress(100);
        mProgressBtn.setProgress(0f);
        mProgressBtn.setButtonRadius(10);
        mProgressBtn.setState(0);
        mProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBtn.setState(AnimDownloadProgressButton.DOWNLOADING);
                mProgressBtn.setProgressText("下载中", mProgressBtn.getProgress() + 8);
                Log.d("mProgressBtn", "showTheButton: " + mProgressBtn.getProgress());
                if (mProgressBtn.getProgress() + 10 > 100) {
                    mProgressBtn.setState(AnimDownloadProgressButton.INSTALLING);
                    mProgressBtn.setCurrentText("下载完成");
                    mProgressBtn.setGravity(Gravity.LEFT);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mProgressBtn.setState(AnimDownloadProgressButton.NORMAL);
                            mProgressBtn.setCurrentText("");
                            mProgressBtn.setGravity(Gravity.LEFT);
                        }
                    }, 2000);   //2秒
                }
            }
        });

        ArcMenu menu = (ArcMenu) findViewById(R.id.ray_menu);

        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            TextView item = new TextView(this);
            item.setText(ITEM_DRAWABLES[i]);
            item.setBackground(ContextCompat.getDrawable(this,R.drawable.custom_cricle_white));
            item.setTextColor(ContextCompat.getColor(this,R.color.red));
            item.setGravity(Gravity.CENTER);
            final int position = i;
            menu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });// Add a menu item
        }
    }

    private SpannableStringBuilder createSpannable(String name, String context) {
        SpannableStringBuilder builder = new SpannableStringBuilder(name.concat(context));
        ForegroundColorSpan redSpan = new ForegroundColorSpan(ContextCompat.getColor(this,R.color.red));
        builder.setSpan(redSpan, 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
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
