package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.customObjects.StepObject;

import java.util.ArrayList;

/**
 * Created by user on 06-01-2018.
 */

public class stepsListRecyclerAdapter extends RecyclerView.Adapter<stepsListRecyclerAdapter.customStepViewHolder> {
    private ArrayList<StepObject> mStepsList;
    private stepListItemClickListener mListener;
    //interface for click listener
    public interface stepListItemClickListener {
        void onStepListItemClick(int clickedItemIndex);
    }
    public stepsListRecyclerAdapter(ArrayList<StepObject> stepsList, stepListItemClickListener listener){
        mStepsList = stepsList;
        mListener = listener;
    }

    @Override
    public stepsListRecyclerAdapter.customStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.steps_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        customStepViewHolder viewHolder = new customStepViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(stepsListRecyclerAdapter.customStepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }

    public class customStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView stepDesc;
        public customStepViewHolder(View itemView) {
            super(itemView);
            stepDesc = (TextView) itemView.findViewById(R.id.stepDesc);
            stepDesc.setOnClickListener(this);
        }

        void bind(int listIndex) {
            stepDesc.setText(mStepsList.get(listIndex).getShortDescription());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mListener.onStepListItemClick(clickedPosition);
        }
    }
}
