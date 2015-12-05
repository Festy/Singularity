package com.example.utsavpatel.singularity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utsavpatel.singularity.Conversation;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by utsavpatel on 11/30/15.
 */
public class ConversationFragment extends Fragment implements View.OnClickListener {
    /** The Conversation list. */
    private ArrayList<Conversation> convList;

    /** The chat adapter. */
    private ChatAdapter adp;

    /** The Editext to compose the message. */
    private EditText txt;
    private Button buddyNameET;

    /** The user name of buddy. */
    private String buddy;

    /** The date of last message in conversation. */
    private Date lastMsgDate;

    /** Flag to hold if the activity is running or not. */
    private boolean isRunning;

    /** The handler. */
    private static Handler handler;

    FragmentManager parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.chat, container, false);

        //parent = getParentFragment().getFragmentManager();

        convList = new ArrayList<Conversation>();
        ListView list = (ListView) view.findViewById(R.id.list);
        adp = new ChatAdapter();
        list.setAdapter(adp);
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setStackFromBottom(true);

        txt = (EditText) view.findViewById(R.id.txt);
        txt.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        buddy = getArguments().getString("extraData");
        buddyNameET = (Button) view.findViewById(R.id.buddyname);
        buddyNameET.setText(buddy);
        //Log.d("Scroll",buddyNameET.isScrollbarFadingEnabled()+"  "+buddyNameET.isScrollContainer()+"  "+buddyNameET)


        handler = new Handler();
        loadConversationList();

        Button sendbt = (Button) view.findViewById(R.id.btnSend);
        sendbt.setOnClickListener(this);

        Button backbt = (Button) view.findViewById(R.id.backbt);
        backbt.setOnClickListener(this);

        buddyNameET.setOnClickListener(this);

        return view;
    }


    /**
     * Call this method to Send message to opponent. It does nothing if the text
     * is empty otherwise it creates a Parse object for Chat message and send it
     * to Parse server.
     */
    private void sendMessage(View view)
    {
        Log.e("SEND MESSAGE", "I am Called!");
        if (txt.length() == 0)
            return;

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);

        String s = txt.getText().toString();
        final Conversation c = new Conversation(s, new Date(),
                UserListFragment.user.getUsername(),buddy);
        c.setStatus(Conversation.STATUS_SENT);
        convList.add(c);
        UserListFragment.user.saveConversation(c);
        adp.notifyDataSetChanged();
        txt.setText(null);
    }

    /**
     * Load the conversation list from Parse server and save the date of last
     * message that will be used to load only recent new messages
     */
    private void loadConversationList()
    {
        if(convList.size()==0) {
            for (Conversation c : UserListFragment.user.getConversations(buddy)) {
                convList.add(c);
                if (lastMsgDate == null
                        || lastMsgDate.before(c.getDate()))
                    lastMsgDate = c.getDate();
                adp.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSend){
            Log.d("OnClick","Send Button");
            sendMessage(v);
        } else if(v.getId() == R.id.backbt){
            Log.d("OnClick","Back Button");
            goBack();
        } else if(v.getId() == R.id.buddyname){
            Log.e("Onclick","");
        } else {
            Log.e("OnClick","404");
        }
    }

    private void goBack() {
        //getFragmentManager().beginTransaction().add(R.id.chatbox_fragment_container,new UserListFragment()).commit();
        getFragmentManager().beginTransaction().remove(this).commit();
    }


    private class ChatAdapter extends BaseAdapter
    {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount()
        {
            return convList.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Conversation getItem(int arg0)
        {
            return convList.get(arg0);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int pos, View v, ViewGroup arg2)
        {
            Conversation c = getItem(pos);
            Log.d("GetConversation", pos + "  " + c.getSender() + "  " + c.getMsg());
            if (c.isSent())
                v = getActivity().getLayoutInflater().inflate(R.layout.chat_item_sent, null);
            else
                v = getActivity().getLayoutInflater().inflate(R.layout.chat_item_rcv, null);

            TextView lbl = (TextView) v.findViewById(R.id.lbl1);
            lbl.setText(DateUtils.getRelativeDateTimeString(getActivity(), c
                            .getDate().getTime(), DateUtils.SECOND_IN_MILLIS,
                    DateUtils.DAY_IN_MILLIS, 0));

            lbl = (TextView) v.findViewById(R.id.lbl2);
            lbl.setText(c.getMsg());

            lbl = (TextView) v.findViewById(R.id.lbl3);
            if (c.isSent())
            {
                if (c.getStatus() == Conversation.STATUS_SENT)
                    lbl.setText("Delivered");
                else if (c.getStatus() == Conversation.STATUS_SENDING)
                    lbl.setText("Sending...");
                else
                    lbl.setText("Failed");
            }
            else
                lbl.setText("");

            return v;
        }

    }

}
