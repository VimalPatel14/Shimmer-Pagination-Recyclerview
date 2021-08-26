/**
 * Created by Vimal on August-2021.
 */
package com.vimal.recyclerviewpaginationloader.Api;


import com.vimal.recyclerviewpaginationloader.Api.model.SourceMain;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {


    @POST("Source.php")
    Call<SourceMain> getSource(@Query("Id") String Id, @Query("PageIdx") String PageIdx);
}