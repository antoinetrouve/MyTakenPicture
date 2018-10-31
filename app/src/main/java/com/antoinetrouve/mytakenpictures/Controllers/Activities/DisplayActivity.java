package com.antoinetrouve.mytakenpictures.Controllers.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.antoinetrouve.mytakenpictures.Adapters.PageAdapter;
import com.antoinetrouve.mytakenpictures.Controllers.Fragments.PageFragment;
import com.antoinetrouve.mytakenpictures.R;
import com.antoinetrouve.mytakenpictures.Services.ApiClient;
import com.antoinetrouve.mytakenpictures.Services.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Antoine Trouvé.
 */

public class DisplayActivity extends AppCompatActivity implements PageFragment.OnButtonClickedListener{

    /** @var ArrayList<Bitmap> The stamped bitmap list */
    ArrayList<Bitmap> stampedBitmaps = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        // Configure the view and set data
        this.configureViewPager();
    }

    private void configureViewPager(){
        // GetViewPager from layout
        ViewPager viewPager = (ViewPager)findViewById(R.id.activity_display_view_pager);

        // Get data from Main Activity
        Intent intent = getIntent();
        stampedBitmaps = intent.getParcelableArrayListExtra("pictures");
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), stampedBitmaps);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onButtonClicked(final View view) {
        // TODO WIP : Send process
        Log.e(getClass().getSimpleName(),"Sending process : START");
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (Bitmap stampedBitmap: stampedBitmaps) {

            // Create temp file to store pictures to send
            String filename = "imageStamped_" + String.valueOf(stampedBitmaps.indexOf(stampedBitmap));
            File tempFile = this.prepareFile(stampedBitmap, filename);

            // Prepare Multipart form for file to send
            MultipartBody.Part multipartBody = ApiClient.prepareFilePart(filename, tempFile);
            parts.add(multipartBody);
        }

        // Prepare multipart form specifique
        MultipartBody.Part processMultipartBody = ApiClient.prepareProcessPart("{'cropping':[]}");
        parts.add(processMultipartBody);

        // TODO: Create a Model : ImageDetail , set data and convert to json Object with gson library
        MultipartBody.Part ImgDetailsmultipartBody = ApiClient.prepareImagesDetailsPart(
                "{[{“name”: “imageStamped_0”, “maskId”: “”, “stampId”: String}, {“name”: “imageStamped_0”, “maskId”: “”, “stampId”: String}]}"
        );
        parts.add(ImgDetailsmultipartBody);

        // TODO Add control to check access netwok before make request api
        // Prepare the request
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface request = retrofit.create(ApiInterface.class);

        // Make the request
        Call<ResponseBody> call = request.uploadMultipleFiles(85, parts);
        // TODO: Make AsyncTask to manage a progressBar
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // TODO: Manage the answer
                Snackbar.make(
                        view,
                        getResources().getString(R.string.app_text_send),
                        Snackbar.LENGTH_LONG
                ).setAction("Action", null).show();
                Log.d(getClass().getSimpleName(),"response ok");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Prepare The bitmap to make request
     * @param bitmap The picture
     * @param filename The picture name
     * @return The file created
     */
    private File prepareFile(Bitmap bitmap, String filename){
        // Create temporary file to save pictures using cache
        File tempFile = this.createTemporaryFile(filename);
        Log.e(getClass().getSimpleName(), "create file " + stampedBitmaps.indexOf(bitmap));

        // Convert bitmap to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,0, byteArrayOutputStream);
        byte[] bitmapData = byteArrayOutputStream.toByteArray();

        // Write into file
        try {
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            outputStream.write(bitmapData);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile;
    }

    /**
     * Create temporary file using cache to save Bitmap
     * @param filename The file name wanted
     * @return The file created
     */
    private File createTemporaryFile(String filename) {

        //create a file to write bitmap data
        Log.e(getClass().getSimpleName(), "cache dir here : " + String.valueOf(this.getCacheDir()));
        File tempFile = new File(this.getCacheDir(),filename);
        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }
}
