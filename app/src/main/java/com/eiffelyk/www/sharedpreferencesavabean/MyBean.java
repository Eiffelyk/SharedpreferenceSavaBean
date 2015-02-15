package com.eiffelyk.www.sharedpreferencesavabean;

import java.io.Serializable;

/**
 * Created by 猫 on 2015/2/13.
 */
public class MyBean implements Serializable{
    private static final long serialVersionUID = 2816841601455245188L;
    private int id;
    private int totalSize;
    private int downSize;
    private String title;
    private String imageUrl;
    private String downloadUrl;
    private int version;
    private int status;

    public MyBean(int id, String title, String imageUrl, String downloadUrl, int version) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.downloadUrl = downloadUrl;
        this.version = version;
    }

    public MyBean(int id, int totalSize, int downSize, String title, String imageUrl, String downloadUrl, int version, int status) {
        this.id = id;
        this.totalSize = totalSize;
        this.downSize = downSize;
        this.title = title;
        this.imageUrl = imageUrl;
        this.downloadUrl = downloadUrl;
        this.version = version;
        this.status = status;
    }

    @Override
    public String toString() {
        return "GridViewBean{" +
                "id=" + id +
                ", totalSize=" + totalSize +
                ", downSize=" + downSize +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", version=" + version +
                ", status=" + status +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getDownSize() {
        return downSize;
    }

    public void setDownSize(int downSize) {
        this.downSize = downSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
