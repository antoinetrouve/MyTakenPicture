package com.antoinetrouve.mytakenpictures.Controllers.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.antoinetrouve.mytakenpictures.Adapters.PageAdapter;
import com.antoinetrouve.mytakenpictures.R;

import java.util.ArrayList;

/**
 * Created by DEV on 29/10/2018.
 */

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        this.configureViewPager();
    }

    private void configureViewPager(){
        // GetViewPager from layout
        ViewPager viewPager = (ViewPager)findViewById(R.id.activity_display_view_pager);

        // Get data from Main Activity
        Intent intent = getIntent();
        ArrayList<Bitmap> bitmaps = intent.getParcelableArrayListExtra("pictures");
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), bitmaps));
    }


}
