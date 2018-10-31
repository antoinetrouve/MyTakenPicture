package com.antoinetrouve.mytakenpictures.Services;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * @author Antoine Trouvé
 */
public class ApiClient {

    /** @var BASE_URL the base url to resquet API */
    public static final String BASE_URL ="https://api.stampyt.io/rec/users/";

    /** @var JSON_TYPE the type json to resquet API */
    public static final String JSON_TYPE = "application/json; charset=utf-8";

    /** @var IMAGE_TYPE the type image to resquet API */
    public static final String IMAGE_TYPE = "image/*";

    /** @var PROCESSES_PART_KEY the multiform key for processes entry*/
    public static final String PROCESSES_PART_KEY = "processes";

    /** @var IMAGES_DETAILS_PART_KEY the multiform key for imagesDetails entry*/
    public static final String IMAGES_DETAILS_PART_KEY = "imagesDetails";

    /** @var API_KEY the API key to resquet API */
    public static final String API_KEY = "XXXXXXXX";

    private static Retrofit retrofit;

    /**
     * Create the client to make request
     * @return
     */
    public static Retrofit getClient(){

        // Use retrofit Interceptor to create the same header for each call
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // Add logger
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                // Add authentification header for each call to the webservice
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        final Request request = chain.request();

                        final Request newRequest = request.newBuilder()
                                .addHeader("x-api-key",API_KEY)
                                .addHeader("'content-type","multipart/form-data")
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    /**
     * Create the multipart form to post file
     * @param filename
     * @param file
     * @return The Multipart form
     */
    public static MultipartBody.Part prepareFilePart(String filename, File file){
        RequestBody requestBody = RequestBody.create(MediaType.parse(IMAGE_TYPE), file);
        return MultipartBody.Part.createFormData(filename, filename, requestBody);
    }

    /**
     * Create the multipart form to post processes
     * @param processes
     * @return The Multipart form
     */
    public static MultipartBody.Part prepareProcessPart(String processes){
        return MultipartBody.Part.createFormData(PROCESSES_PART_KEY, processes);
    }

    /**
     * Create the multipart form to post images details
     * @param imagesDetails
     * @return The Multipart form
     */
    public static MultipartBody.Part prepareImagesDetailsPart(String imagesDetails){
        RequestBody requestBody = RequestBody.create(MediaType.parse(JSON_TYPE), imagesDetails);
        return MultipartBody.Part.createFormData(IMAGES_DETAILS_PART_KEY, imagesDetails);
    }

}
