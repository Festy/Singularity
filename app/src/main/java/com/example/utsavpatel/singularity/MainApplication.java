package com.example.utsavpatel.singularity;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

/**
 * Created by utsavpatel on 12/5/15.
 */
public class MainApplication extends Application {
    private String currentFragmentTag;
    private View view;
    private Context context;
    private RotationUtils rotationUtils;

    public void initRotationUnits(){
        rotationUtils = new RotationUtils();
    }

    public String getCurrentFragmentTag() {
        return currentFragmentTag;
    }

    public void setCurrentFragmentTag(String currentFragmentTag) {
        Log.d("MainApplication", "New Current Fragment:" + currentFragmentTag);
        this.currentFragmentTag = currentFragmentTag;
    }

    public void setCurrentFragmentTagAndView(String currentFragmentTag, View view, Context context, int[] rotationIdList) {
        Log.d("MainApplication", "setCurrentFragmentTagAndView New Current Fragment:" + currentFragmentTag);
        this.currentFragmentTag = currentFragmentTag;
        this.view = view;
        this.context = context;
        rotationUtils.stopRotation();
        rotationUtils.startRotation(view, context,rotationIdList);
    }

    public void stopRotation(){
        rotationUtils.stopRotation();
    }
}
