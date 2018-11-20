package com.antoinetrouve.mytakenpictures.Adapters;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.antoinetrouve.mytakenpictures.Controllers.Fragments.PageFragment;

import java.util.ArrayList;

/**
 * @author Antoine Trouvé
 */

public class PageAdapter extends FragmentPagerAdapter{

    /** @var ArrayList<Bitmap> The list of Bitmaps will be passed to PageFragment */
    private ArrayList<Bitmap> bitmaps;

    /** @var boolean true if is the last picture is processing */
    private boolean lastPicture = false;

    /**
     * Default constructor
     * @param fm
     * @param bitmaps
     */
    public PageAdapter(FragmentManager fm, ArrayList<Bitmap> bitmaps) {
        super(fm);
        this.bitmaps = bitmaps;

        // For debug purpose
        Log.d(
            getClass().getSimpleName(),
            "pageAdapter called with total Bitmap to process: " + this.bitmaps.size()
        );
    }

    @Override
    public Fragment getItem(int position) {

        // If last picture is processing
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
