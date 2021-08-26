/**
 * Created by Vimal on August-2021.
 */
package com.vimal.recyclerviewpaginationloader.Api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class Source extends RealmObject implements Serializable {

    @SerializedName("SourceId")
    @Expose
    private String sourceId;
    @SerializedName("AudioId")
    @Expose
    private String audioId;
    @SerializedName("SourceThumbPath")
    @Expose
    private String sourceThumbPath;
    @SerializedName("SourcePath")
    @Expose
    private String sourcePath;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("SourceJsonPath")
    @Expose
    private String sourceJsonPath;
    @SerializedName("Priority")
    @Expose
    private String priority;
    @SerializedName("CategoryId")
    @Expose
    private String categoryId;
    @SerializedName("SourceName")
    @Expose
    private String sourceName;
    @SerializedName("AudioPath")
    @Expose
    private String audioPath;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getSourceThumbPath() {
        return sourceThumbPath;
    }

    public void setSourceThumbPath(String sourceThumbPath) {
        this.sourceThumbPath = sourceThumbPath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSourceJsonPath() {
        return sourceJsonPath;
    }

    public void setSourceJsonPath(String sourceJsonPath) {
        this.sourceJsonPath = sourceJsonPath;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }
}