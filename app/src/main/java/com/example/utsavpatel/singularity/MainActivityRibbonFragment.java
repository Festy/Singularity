package com.example.utsavpatel.singularity;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.zip.Inflater;

/**
 * Created by utsavpatel on 11/26/15.
 */
public class MainActivityRibbonFragment extends Fragment implements View.OnClickListener{

    int[] rotationIdList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.top_main_fragment, container, false);
        rotationIdList = new int[] {R.id.messengerBt, R.id.webBt, R.id.emergencyBt, R.id.moreBt};

        ((MainApplication) getActivity().getApplication()).setCurrentFragmentTagAndView("main_activity_ribbon_fragment",view,getContext(), rotationIdList);

        ((Button) view.findViewById(R.id.messengerBt)).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.messengerBt){
            ((MainApplication) getActivity().getApplication()).stopRotation();

        }
    }
}
