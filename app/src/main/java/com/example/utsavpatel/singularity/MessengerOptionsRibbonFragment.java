package com.example.utsavpatel.singularity;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by utsavpatel on 11/26/15.
 */
public class MessengerOptionsRibbonFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.top_chat_fragment, container, false);
        ((MainApplication) getActivity().getApplication()).setCurrentFragmentTag("messenger_fragment");

        return view;
    }

}
