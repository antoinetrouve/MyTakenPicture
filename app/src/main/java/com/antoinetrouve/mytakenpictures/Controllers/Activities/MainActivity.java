package com.antoinetrouve.mytakenpictures.Controllers.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.antoinetrouve.mytakenpictures.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String TAG_PICTURES = "pictures";
    static final String PICTURE_OK = "Ok";
    static final String PICTURE_CANCEL = "Annuler";

    // View elements
    private ImageView imageView;
    private Button pictureOneButton;
    private Button pictureTwoButton;
    private FloatingActionButton cameraButton;

    // total pictures taken
    private int picturesDone = 0;

    // the picture in process
    private Bitmap pictureCurrent;

    // the pictures taken and validate
    ArrayList<Bitmap> pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pictures = new ArrayList<>();

        // Set button Action
        pictureOneButton = findViewById(R.id.button_picture_1);
        pictureOneButton.setEnabled(true);
        pictureTwoButton = findViewById(R.id.button_picture_2);
        pictureTwoButton.setEnabled(false);

        cameraButton     = findViewById(R.id.camera_button);

        // Set ImageView
        imageView        = findViewById(R.id.taken_picture_image);

        // take picture
        cameraButton.setOnClickListener(this);
        pictureOneButton.setOnClickListener(this);
        pictureTwoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            // Take picture using camera
            case R.id.camera_button :
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
            case R.id.button_picture_1 :
                if (pictureOneButton.getText().equals(PICTURE_OK)){
                    // Valid picture 1
                    pictureOneButton.setBackgroundColor(Color.GREEN);
                    pictureOneButton.setEnabled(false);

                    // Save picture
                    this.pictures.add(this.pictureCurrent);

                    // init view
                    pictureOneButton.setText(getResources().getText(R.string.app_text_picture1));
                    pictureTwoButton.setText(getResources().getText(R.string.app_text_picture2));
                    pictureTwoButton.setBackgroundColor(getResources().getColor(R.color.colorNormalBtn));
                    imageView.setImageResource(R.drawable.stamp);
                    cameraButton.setVisibility(View.VISIBLE);

                    if (this.picturesDone == 1) {
                        // valid picture
//                        pictureOneButton.setBackgroundColor(Color.GREEN);
//                        pictureOneButton.setEnabled(false);

                        // Keep safe the picture
//                        this.pictures.add(this.pictureCurrent);

                        // init view
//                        pictureOneButton.setText(getResources().getText(R.string.app_text_picture1));
//                        pictureTwoButton.setText(getResources().getText(R.string.app_text_picture2));
//                        pictureTwoButton.setBackgroundColor(getResources().getColor(R.color.colorNormalBtn));
//                        imageView.setImageResource(R.drawable.stamp);
//                        cameraButton.setVisibility(View.VISIBLE);

                        //active the second action for picture 2
                        pictureTwoButton.setEnabled(true);

                        Snackbar.make(
                                view,
                                getResources().getString(R.string.app_action_valid),
                                Snackbar.LENGTH_LONG
                        ).setAction("Action", null).show();

                    } else if (this.picturesDone == 2){
                        // valid picture 1
//                        pictureOneButton.setBackgroundColor(Color.GREEN);
//                        pictureOneButton.setEnabled(false);

                        // valid picture 2
                        pictureTwoButton.setBackgroundColor(Color.GREEN);
                        pictureTwoButton.setEnabled(false);
//                        this.pictures.add(this.pictureCurrent);

                        // init view
//                        pictureOneButton.setText(getResources().getText(R.string.app_text_picture1));
//                        pictureTwoButton.setText(getResources().getText(R.string.app_text_picture2));
//                        imageView.setImageResource(R.drawable.stamp);
//                        cameraButton.setVisibility(View.VISIBLE);

                        Snackbar.make(
                                view,
                                getResources().getString(R.string.app_action_valid),
                                Snackbar.LENGTH_LONG
                        ).setAction("Action", null).show();


                    }

                    // init display view when all pictures has been taken
                    if (this.pictures.size() == 2) {
                        Intent intent = new Intent(this, DisplayActivity.class);
                        intent.putParcelableArrayListExtra(TAG_PICTURES, this.pictures);
                        startActivity(intent);
                    }
                }

                break;
            case R.id.button_picture_2 :
                if (pictureTwoButton.getText().equals(PICTURE_CANCEL)){
                    // reverse action
                    if (picturesDone > 0) {
                        picturesDone -= 1;
                    }

                    // re init the view
                    pictureOneButton.setText(getResources().getText(R.string.app_text_picture1));
                    pictureTwoButton.setText(getResources().getText(R.string.app_text_picture2));
                    pictureTwoButton.setBackgroundColor(getResources().getColor(R.color.colorNormalBtn));
                    imageView.setImageResource(R.drawable.stamp);
                    cameraButton.setVisibility(View.VISIBLE);

                    // config button picture 2 as unavailable
                    pictureTwoButton.setEnabled(false);

                    // if already at least one picture has been validated
                    if (picturesDone >= 1) {
                        // config button picture 1 as valid
                        pictureOneButton.setBackgroundColor(Color.GREEN);
                        pictureOneButton.setEnabled(false);
                    }

                    Snackbar.make(
                            view,
                            getResources().getString(R.string.app_action_cancel)
                                    + " "
                                    + getResources().getString(R.string.app_action_retry),
                            Snackbar.LENGTH_LONG
                    ).setAction("Action", null).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If Camera ok
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Save the number of action done
            picturesDone += 1;
            // Get picture
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Apply Filter
            Bitmap stampedImage = this.ApplyStamp(imageBitmap);
            // this.pictureCurrent = imageBitmap;
            this.pictureCurrent = stampedImage;

            // Set image view
            cameraButton.setVisibility(View.GONE);
            //imageView.setImageBitmap(imageBitmap);
            imageView.setImageBitmap(stampedImage);

            // config view
            pictureOneButton.setText(PICTURE_OK);
            pictureTwoButton.setText(PICTURE_CANCEL);
            // Active the cancel action button
            pictureTwoButton.setEnabled(true);
            pictureTwoButton.setBackgroundColor(getResources().getColor(R.color.colorCancelBtn));

            // Resolve action
//            if (picturesDone == 1){
                // Active the cancel action button
//                pictureTwoButton.setEnabled(true);
//                pictureOneButton.setText(PICTURE_OK);
//                pictureOneButton.setBackgroundColor(getResources().getColor(R.color.colorNormalBtn));
//                pictureTwoButton.setText(PICTURE_CANCEL);
//            }

            if (picturesDone == 2){
                // Active the valide action button
                pictureOneButton.setEnabled(true);
                pictureOneButton.setBackgroundColor(getResources().getColor(R.color.colorNormalBtn));
//                pictureTwoButton.setText(PICTURE_CANCEL);
            }
        }
    }

    private Bitmap ApplyStamp(Bitmap bitmapSource){
        Log.e(getClass().getSimpleName(), "Apply stamp - START");

        // Create the new Bitmap
        int w = bitmapSource.getWidth();
        int h = bitmapSource.getHeight();
        Bitmap bitmapDest = Bitmap.createBitmap(w, h, bitmapSource.getConfig());

        // Copy the original bitmap into the new one
        Canvas canvas = new Canvas(bitmapDest);
        canvas.drawBitmap(bitmapSource, 0, 0, null);

        // Load the stamp and apply
        Bitmap waterMark = BitmapFactory.decodeResource(getResources(), R.drawable.ic_stamp_defalult);

        canvas.drawBitmap(
                waterMark,
                bitmapSource.getWidth() - waterMark.getWidth(),
                bitmapSource.getHeight() - waterMark.getHeight(),
                null
        );
        waterMark.recycle();

        Log.e(getClass().getSimpleName(), "Apply stamp - END");
        return bitmapDest;
    }
}
