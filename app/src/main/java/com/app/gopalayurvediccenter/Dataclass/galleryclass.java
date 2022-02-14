package com.app.gopalayurvediccenter.Dataclass;

public class galleryclass {

    public String imgUrl;
    public String timestamp;
    public String byClass;
    public String byCaption;
    public long views;

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getByClass() {
        return byClass;
    }

    public void setByClass(String byClass) {
        this.byClass = byClass;
    }

    public String getByCaption() {
        return byCaption;
    }

    public void setByCaption(String byCaption) {
        this.byCaption = byCaption;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public galleryclass()
    {

    }


    public galleryclass(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }


}
