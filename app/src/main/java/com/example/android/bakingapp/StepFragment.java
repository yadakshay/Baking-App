package com.example.android.bakingapp;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.customObjects.StepObject;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import static com.example.android.bakingapp.RecipeActivity.NEXT_KEY;
import static com.example.android.bakingapp.RecipeActivity.PREVIOUS_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {
    private SimpleExoPlayer mPlayer;
    private StepObject currentStep;
    private SimpleExoPlayerView mPlayerView;
    private String mStreamURL, mImageURL;
    private boolean playWhenReady = true;
    private int currentWindow;
    private long playbackPosition;
    private boolean videoUrlExists;
    private OnNextClickListener mCallback;

    public interface OnNextClickListener {
        void onNextSelected(String nextOrPrev);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnNextClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNextClickListener");
        }
    }

    public StepFragment() {
        // Required empty public constructor
    }

    public void setStepObject(StepObject step){
        currentStep = step;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        Button nextButton = (Button) rootView.findViewById(R.id.nextStepButton);
        Button prevButton = (Button) rootView.findViewById(R.id.prevStepButton);
        TextView tv = (TextView) rootView.findViewById(R.id.longDescription);
        TextView sd = (TextView) rootView.findViewById(R.id.shortDescription);
        ImageView stepThumbnail = (ImageView) rootView.findViewById(R.id.stepThumbnailHolder);
        tv.setText(currentStep.getDescription());
        sd.setText(currentStep.getShortDescription());
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_view);
        mStreamURL= currentStep.getVideoURL();
        mImageURL= currentStep.getThumbnailURL();
        if(mImageURL!=null){
            if(!mImageURL.matches("")){
                if(!mImageURL.substring(mImageURL.length() - 4).matches(".mp4")) {
                    Picasso.with(getContext()).load(mImageURL).into(stepThumbnail);
                }
            }
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onNextSelected(NEXT_KEY);
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onNextSelected(PREVIOUS_KEY);
            }
        });
        if (mStreamURL.matches("")){
            mPlayerView.setVisibility(View.GONE);
        }else if(mStreamURL != null && mStreamURL.length()>0) {
            //Log.i("CHECK URL", mStreamURL);
            initializePlayer();
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
            mStreamURL = "";
        }
    }

    private void initializePlayer() {
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    getContext(),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            mPlayerView.setPlayer(mPlayer);

            mPlayer.setPlayWhenReady(playWhenReady);
            mPlayer.seekTo(currentWindow, playbackPosition);
        }
        MediaSource mediaSource = buildMediaSource(Uri.parse(mStreamURL));
        mPlayer.prepare(mediaSource, true, false);
    }
    private void releasePlayer() {
        if (mPlayer != null) {
            playbackPosition = mPlayer.getCurrentPosition();
            currentWindow = mPlayer.getCurrentWindowIndex();
            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri, new DefaultHttpDataSourceFactory("exoplayer-codelab"),
                new DefaultExtractorsFactory(), null, null);
    }


}
