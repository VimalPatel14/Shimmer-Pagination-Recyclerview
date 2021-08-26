/**
 * Created by Vimal on August-2021.
 */
package com.vimal.recyclerviewpaginationloader.Api.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.vimal.recyclerviewpaginationloader.Api.Api;
import com.vimal.recyclerviewpaginationloader.Api.model.SourceMain;
import com.vimal.recyclerviewpaginationloader.BuildConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SourceViewModel extends ViewModel {

    //this is the data that we will fetch asynchronously 
    private MutableLiveData<SourceMain> sourcelist;

    //we will call this method to get the data
    public LiveData<SourceMain> getSource(int catid, int Pageno) {
        //if the list is null 
//        if (sourcelist == null) {
            sourcelist = new MutableLiveData<SourceMain>();
            //we will load it asynchronously from server in this method
            loadSource(catid,Pageno);
//        }

        //finally we will return the list
        return sourcelist;
    }



    //This method is using Retrofit to get the JSON data from URL 
    private void loadSource(int catid,int Pageno) {

        Log.e("vml",  Pageno+" Pageno");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
//        Call<SourceMain> call = api.getCategory();
        Call<SourceMain> call = api.getSource(catid+"",Pageno+"");
        call.enqueue(new Callback<SourceMain>() {
            @Override
            public void onResponse(Call<SourceMain> call, Response<SourceMain> response) {
                if (response.isSuccessful()) {
                    Log.e("vml",  " isSuccessful");
                    sourcelist.setValue(response.body());


                } else {
                    Log.e("vml",  " isSuccessful_No");
                }
                //finally we are setting the list to our MutableLiveData
            }

            @Override
            public void onFailure(Call<SourceMain> call, Throwable t) {
                Log.e("vml", t.getMessage() + " error");
            }
        });
    }
}
 