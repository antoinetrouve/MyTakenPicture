package com.antoinetrouve.mytakenpictures.Controllers.Fragments;


import android.content.Context;
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
 * @author Antoine Trouvé
 */
public class PageFragment extends Fragment implements View.OnClickListener {

    // Create keys for Bundle
    /** @var KEY_POSITION The bundle key position */
    private static final String KEY_POSITION = "position";

    /** @var KEY_BITMAP The bundle key bitmap */
    private static final String KEY_BITMAP  = "picture";

    /** @var KEY_IS_LAST_PICTURE The bundle key isLastPicture */
    private static final String KEY_IS_LAST_PICTURE  = "isLastPicture";

    /** @var mCallback The callback from PageFragment on click event */
    private OnButtonClickedListener mCallback;

    /**
     * Interface to create a callback on click event
     */
    public interface OnButtonClickedListener{
        public void onButtonClicked(View view);
    }

    /**
     * Default constructor
     */
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
        if (isLastPicture == true ){
            Log.e(getClass().getSimpleName(), "onCreateView last picture " + String.valueOf(isLastPicture));
            FloatingActionButton sendButton = result.findViewById(R.id.fragment_page_send_button);
            sendButton.setVisibility(View.VISIBLE);
            sendButton.setOnClickListener(this);
        }

        // Update the view
        imageView.setImageBitmap(bitmap);

        return result;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    /**
     * Create callback to parent activity
     */
    private void createCallbackToParentActivity() {
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }

    @Override
    public void onClick(View view) {
        // Spread the click to the parent activity
        mCallback.onButtonClicked(view);
    }

}
