/**
 * Created by Vimal on August-2021.
 */
package com.vimal.recyclerviewpaginationloader.Api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SourceMain {

    @SerializedName("data")
    @Expose
    private List<Source> data = null;
    @SerializedName("sucess")
    @Expose
    private Boolean sucess;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Source> getData() {
        return data;
    }

    public void setData(List<Source> data) {
        this.data = data;
    }

    public Boolean getSucess() {
        return sucess;
    }

    public void setSucess(Boolean sucess) {
        this.sucess = sucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

