package com.example.utsavpatel.singularity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by utsavpatel on 11/26/15.
 */
public class MessengerFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction childFragmentTransaction = childFragmentManager.beginTransaction();


        if (childFragmentManager.findFragmentById(R.id.top_chat_fragment) == null) {
            MessengerOptionsRibbonFragment topChatFragment = new MessengerOptionsRibbonFragment();
            childFragmentTransaction = childFragmentTransaction.add(R.id.top_chat_container, topChatFragment,"messenger_options_ribbon_fragment");
        }
        if(childFragmentManager.findFragmentById(R.id.keyboard_fragment)==null){
            KeyboardFragment keyBoardFragment = new KeyboardFragment();
            childFragmentTransaction = childFragmentTransaction.add(R.id.keyboard_container_at_chat, keyBoardFragment,"keyboard_fragment");
        }
        if(childFragmentManager.findFragmentById(R.id.user_list)==null){
            UserListFragment userListFragment = new UserListFragment();
            childFragmentTransaction = childFragmentTransaction.add(R.id.chatbox_fragment_container, userListFragment,"user_list_fragment");
        }

//        if(childFragmentManager.findFragmentById(R.id.chat)==null){
//            ConversationFragment coversationFragment = new ConversationFragment();
//            childFragmentTransaction = childFragmentTransaction.add(R.id.chatbox_fragment_container, userListFragment);
//        }
            childFragmentTransaction.commit();
        return view;
    }

}
