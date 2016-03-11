package com.cxy.firststudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2015/12/17.
 */
public class SecendActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secend);
        initViews();
    }

    private void initViews(){
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, viewShowActivity.class);
        switch(v.getId()){
            case R.id.button5:
                intent.putExtra("type", 0);
                break;
            case R.id.button6:
                intent.putExtra("type", 1);
                break;
            case R.id.button7:
                intent.putExtra("type", 2);
                break;
        }
        startActivity(intent);
    }
}
