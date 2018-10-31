package com.antoinetrouve.mytakenpictures.Services;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 *  @author Antoine Trouvé
 */

public interface ApiInterface {

    @Multipart
    @POST("{user_id}/shootings")
    Call<ResponseBody> uploadMultipleFiles(
            @Path("user_id") int userId,
            @Part List<MultipartBody.Part> files);
}
