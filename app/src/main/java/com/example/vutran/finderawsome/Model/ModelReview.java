package com.example.vutran.finderawsome.Model;

/**
 * Created by VuTran on 6/16/2017.
 */

public class ModelReview {
    private String authorName;
    private String photoUrl;
    private String rating;
    private String text;

    public ModelReview(String authorName, String photoUrl, String rating, String text) {
        this.authorName = authorName;
        this.photoUrl = photoUrl;
        this.rating = rating;
        this.text = text;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
