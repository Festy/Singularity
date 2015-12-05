package com.example.utsavpatel.singularity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements KeyboardFragment.OnHeadlineSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MainApplication) this.getApplication()).initRotationUnits();
        ((MainApplication) this.getApplication()).setCurrentFragmentTag("none");
        {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            MessengerFragment messengerFragment = new MessengerFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            messengerFragment.setArguments(getIntent().getExtras());

            MainActivityRibbonFragment mainActivityRibbonFragment = new MainActivityRibbonFragment();
            mainActivityRibbonFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.top_frag_container, mainActivityRibbonFragment,"main_activity_ribbon_fragment");
            fragmentTransaction.add(R.id.bottom_frag_container, messengerFragment,"messenger_fragment");


            fragmentTransaction.commit();

        }
        Intent intent = new Intent(this, UserListFragment.class);
        //startActivity(intent);

    }

    public void onKeyboardClick(int type, String msg){
        Log.d("KeyBoard", msg);
        FragmentManager parent;

        ConversationFragment conversationFragment = (ConversationFragment)
                getSupportFragmentManager().findFragmentByTag("conversation_fragment");

        if(conversationFragment==null || !conversationFragment.isVisible()) {
            Log.e("Keyboard", "Fragment Null");
        } else {
            if (msg.equals("Enter"))
                conversationFragment.ListenFromKeyboard(1, msg);
            else
                conversationFragment.ListenFromKeyboard(0, msg);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
