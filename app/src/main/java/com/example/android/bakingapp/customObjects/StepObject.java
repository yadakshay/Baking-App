package com.example.android.bakingapp.customObjects;

/**
 * Created by Akshay on 17-09-2017.
 *
 * This represents each step in the recipe
 */

public class StepObject {
    private String mShortDesc, mDesc, mVideoUrl, mThumbnailURL;

    public StepObject(String shortDescription, String description, String videoURL, String thumbnailURL){
        mShortDesc = shortDescription;
        mDesc = description;
        mVideoUrl = videoURL;
        mThumbnailURL = thumbnailURL;
    }
    //return methods
    public String getShortDescription(){return mShortDesc;}
    public String getDescription(){return mDesc;}
    public String getVideoURL(){return mVideoUrl;}
    public String getThumbnailURL(){return mThumbnailURL;}
}
