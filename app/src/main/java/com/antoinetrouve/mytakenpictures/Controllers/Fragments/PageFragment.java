package com.antoinetrouve.mytakenpictures.Controllers.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.antoinetrouve.mytakenpictures.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    // Create keys for Bundle
    private static final String KEY_POSITION = "position";
    private static final String KEY_BITMAP  = "picture";
    private static final String KEY_IS_LAST_PICTURE  = "isLastPicture";

    public PageFragment() { }

    // Method that will create a new instance of PageFragment, add data to its bundle
    public static PageFragment newInstance(int position, Bitmap bitmap, boolean lastPicture) {
        // Create new Fragment
        PageFragment pageFragment = new PageFragment();

        // Create Bundle and add some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putParcelable(KEY_BITMAP, bitmap);
        args.putBoolean(KEY_IS_LAST_PICTURE, lastPicture);
        pageFragment.setArguments(args);

        return(pageFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the layout of PageFragment
        View result = inflater.inflate(R.layout.fragment_page, container, false);
        ImageView imageView = (ImageView) result.findViewById(R.id.fragment_page_image);

        // Get data from Bundle (created in method newInstance)
        int position = getArguments().getInt(KEY_POSITION, -1);
        Bitmap bitmap = getArguments().getParcelable(KEY_BITMAP);
        boolean isLastPicture = getArguments().getBoolean(KEY_IS_LAST_PICTURE);

        // If picture is the last one, available sending action
        if (isLastPicture == true){
            FloatingActionButton sendButton = result.findViewById(R.id.fragment_page_send_button);
            sendButton.setVisibility(View.VISIBLE);
        }

        Log.e(getClass().getSimpleName(), "onCreateView called page fragment current "+ position);

        // Update the view
        imageView.setImageBitmap(bitmap);

        return result;

    }

}
