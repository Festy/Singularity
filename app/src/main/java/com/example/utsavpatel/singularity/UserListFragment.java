package com.example.utsavpatel.singularity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by utsavpatel on 11/30/15.
 */
public class UserListFragment extends Fragment{

    ProgressDialog dia;

    /** The Chat list. */
    private ArrayList<User> uList;

    /** The user. */
    public static User user;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_list, container, false);

        creatTempUser();
        loadUserList(view);
        ((MainApplication) getActivity().getApplication()).setCurrentFragmentTag("user_list_fragment");
        return view;
    }

    private void creatTempUser(){
        user = new User();
        user.setUserName("utsav");
        user.setOnline(true);
        user.saveTempConversations();
    }

//    public void StartChatActivity(String friendId) {
//        if(dia!=null)
//            dia.dismiss();
//        Intent mainIntent = new Intent(getActivity(), Chat.class).putExtra(Const.EXTRA_DATA, friendId);
//        startActivity(mainIntent);
//        finish();
//    }

    public void startDia(){
        dia = ProgressDialog.show(getActivity(), null,
                getString(R.string.alert_wait));
    }

    public void stopDia(){
        if(dia!=null)
            dia.dismiss();
        dia = null;
    }


    /**
     * Update user status.
     *
     * @param online
     *            true if user is online
     */
    private void updateUserStatus(boolean online)
    {
		/*user.put("online", online);
		user.saveEventually();*/
        user.setOnline(online);
    }

    /**
     * Load list of users.
     */
    private void loadUserList(View view)
    {
        final ProgressDialog dia = ProgressDialog.show(getActivity(), null,
                getString(R.string.alert_loading));					//	We get the list of users in li. to get a list of users we want just change the query

        List<User> li = user.getFriends();
        dia.dismiss();
        if (li != null) {
            if (li.size() == 0)
                Toast.makeText(getActivity(),
                        R.string.msg_no_user_found,
                        Toast.LENGTH_SHORT).show();

            uList = new ArrayList<User>(li);
            ListView list = (ListView) view.findViewById(R.id.list);
            list.setAdapter(new UserAdapter());
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0,
                                        View arg1, int pos, long arg3) {
                    Log.d("StartChat", uList.get(pos).getUsername());
                    Bundle bundle = new Bundle();
                    bundle.putString("extraData", uList.get(pos)
                            .getUsername());
                    ConversationFragment conversationFragment = new ConversationFragment();
                    conversationFragment.setArguments(bundle);
                    FragmentManager parent;
                    if(getParentFragment()!=null)
                        parent = getParentFragment().getFragmentManager();
                    else
                        parent = getFragmentManager();
                    FragmentTransaction fragmentTransaction =  parent.beginTransaction();
                    fragmentTransaction.add(R.id.chatbox_fragment_container, conversationFragment, "conversation_fragment");
                    fragmentTransaction.addToBackStack(null).commit();
                    //parent.executePendingTransactions();
                }
            });
        } else {
            Log.e("UserList", getString(R.string.err_users))	;
        }


		/*User.getQuery().whereNotEqualTo("username", user.getUsername())
				.findInBackground(new FindCallback<User>() {

					@Override
					public void done(List<User> li, ParseException e) {
						dia.dismiss();
						if (li != null) {
							if (li.size() == 0)
								Toast.makeText(UserList.this,
										R.string.msg_no_user_found,
										Toast.LENGTH_SHORT).show();

							uList = new ArrayList<User>(li);
							ListView list = (ListView) findViewById(R.id.list);
							list.setAdapter(new UserAdapter());
							list.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
														View arg1, int pos, long arg3) {
									startActivity(new Intent(UserList.this,
											Chat.class).putExtra(
											Const.EXTRA_DATA, uList.get(pos)
													.getUsername()));
								}
							});
						} else {
							Utils.showDialog(
									UserList.this,
									getString(R.string.err_users) + " "
											+ e.getMessage());
							e.printStackTrace();

						}
					}
				});*/
    }



    /**
     * The Class UserAdapter is the adapter class for User ListView. This
     * adapter shows the user name and it's only online status for each item.
     */
    public void createToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private class UserAdapter extends BaseAdapter
    {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount()
        {
            return uList.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public User getItem(int arg0)
        {
            return uList.get(arg0);
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
            if (v == null)
                v = getActivity().getLayoutInflater().inflate(R.layout.chat_item, null);

            User c = getItem(pos);
            TextView lbl = (TextView) v;
            lbl.setText(c.getUsername());
            lbl.setCompoundDrawablesWithIntrinsicBounds(
                    c.isOnline() ? R.drawable.ic_online
                            : R.drawable.ic_offline, 0, R.drawable.arrow, 0);

            return v;
        }

    }
}
