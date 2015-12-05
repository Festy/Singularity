package com.example.utsavpatel.singularity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by utsavpatel on 12/5/15.
 */
public class RotationUtils extends Activity{

    RotationThread currentThread;
    Context context;

    public void stopRotation(){
        if(context!=null) {
            Log.d("RotationUtils", "stopRotation");
            MainApplication app = ((MainApplication) context.getApplicationContext());
            Log.d("RotationUtils", "StopRotation app isnull:" + (app == null));
            String currentFragment = app.getCurrentFragmentTag();
            if (currentThread != null) {
                currentThread.stopRotation();
            }
        } else{
            Log.e("RotationUtils","StopRotation. COntext Already null");
        }
    }

    public void startRotation(View view, Context context, int[] rotationIdList){
        //stopRotation();
        this.context = context;
        Log.d("RotationUtils","startRotation");
        String currentFragment = ((MainApplication) context.getApplicationContext()).getCurrentFragmentTag();
        currentThread = new RotationThread(view,context,currentFragment,rotationIdList);
        currentThread.start();
        //currentThread.stopRotation();
    }

    class RotationThread extends Thread{

        View view;
        Context context;
        String fragmentTag;
        int[] rotationIdList;
        boolean flagRunning = true;
        public RotationThread(View view, Context context, String fragmentTag, int[] rotationIdList){
            this.view = view;
            this.context = context;
            this.fragmentTag = fragmentTag;
            this.rotationIdList = rotationIdList;
        }

        @Override
        public void run() {
            Log.d("RotationThread", "Run() " + fragmentTag);
            while(flagRunning){
                for (int id : rotationIdList) {
                    if(flagRunning) {
                        final int[] ids = {id};
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setEffect(ids[0]);
                            }
                        });

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                unsetEffect(ids[0]);
                            }
                        });
                    }
                }

            }

        }

        private void unsetEffect(int id) {

            View messengerBT = view.findViewById(id);
            Class c = messengerBT.getClass();
            if(c.equals(Button.class)){
                ((Button)messengerBT).getBackground().setColorFilter(context.getResources().getColor(R.color.gray_light), PorterDuff.Mode.MULTIPLY);
            }
            else if(c.equals(EditText.class)){
                //((EditText)messengerBT).setText(defaultText[0]);
            }

        }

        private void setEffect(int id) {
            View messengerBT = view.findViewById(id);
            Class c = messengerBT.getClass();
            if(c.equals(Button.class)){
                ((Button)messengerBT).getBackground().setColorFilter(context.getResources().getColor(R.color.orage), PorterDuff.Mode.MULTIPLY);

            }
            else if(c.equals(EditText.class)){
                ((EditText)messengerBT).setText("nice text!");
            }
        }

        public void stopRotation(){
            Log.d("RotationThread", "StopRotation() " + fragmentTag);
            flagRunning = false;
        }
    }
}
