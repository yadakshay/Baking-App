package com.example.android.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.bakingapp.customObjects.StepObject;

import java.util.ArrayList;

/**
 * Created by Akshay on 23-09-2017.
 */

public class ListAdapterForStepsList extends ArrayAdapter<StepObject> {
    private ArrayList<StepObject> mStepsList;
    private Context mContext;
    public ListAdapterForStepsList (Context context, ArrayList<StepObject> stepsList){
        super(context, 0, stepsList);
        mStepsList = stepsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            //inflate a new view
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.steps_list, parent, false);
        }
        TextView stepsDescListTextView = (TextView) listItemView.findViewById(R.id.stepDesc);
        stepsDescListTextView.setText(mStepsList.get(position).getShortDescription());
        return listItemView;
    }
}

