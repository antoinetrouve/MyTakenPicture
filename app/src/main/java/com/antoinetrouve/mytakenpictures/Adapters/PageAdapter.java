package com.antoinetrouve.mytakenpictures.Adapters;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.antoinetrouve.mytakenpictures.Controllers.Fragments.PageFragment;

import java.util.ArrayList;

/**
 * Created by DEV on 29/10/2018.
 */

public class PageAdapter extends FragmentPagerAdapter{

    // colors array that will be passed to PageFragment
    // private int[] colors;
    private ArrayList<Bitmap> bitmaps;
    private boolean lastPicture = false;

    /**
     * Default constructor
     * @param fm
     * @param bitmaps
     */
    public PageAdapter(FragmentManager fm, ArrayList<Bitmap> bitmaps) {
        super(fm);
        this.bitmaps = bitmaps;
        Log.e(getClass().getSimpleName(), "pageAdapter called : "+ this.bitmaps.size());
    }

    @Override
    public Fragment getItem(int position) {
        if (this.getCount() -1 == position) {
            lastPicture = true;
        }
        // Page to return
        return PageFragment.newInstance(position, this.bitmaps.get(position), lastPicture);
    }

    @Override
    public int getCount() {
        // Number of page to show
        return this.bitmaps.size();
    }
}
