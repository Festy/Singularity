package com.example.utsavpatel.singularity;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by utsavpatel on 11/26/15.
 */
public class KeyboardFragment extends Fragment implements View.OnClickListener{
    OnHeadlineSelectedListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.keyboard_fragment, container, false);
        addKeyboard(getContext(), view, inflater);
        ((MainApplication) getActivity().getApplication()).setCurrentFragmentTag("keyboard_fragment");
        return view;
    }

    public void addKeyboard(Context context, View view, LayoutInflater inflater) {

        int columns = 6;
        int rows = 6;
        int count=0;

        TableLayout layout = (TableLayout) view.findViewById(R.id.keyboard_fragment);

        for (int r=0; r<rows-1; r++) {

            TableRow tr = new TableRow(context);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (int c=0; c<columns; c++) {

                Button button = new Button(context.getApplicationContext());
                button.setText(Character.toString((char) (count + 'A')));
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                button.setId(count++);
                button.setOnClickListener(this);
                tr.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            }
            layout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }


            TableRow tr = new TableRow(context);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            count = 0;
            String[] buttons = {"Enter","Space","Back","Bksp",".","?"};
            for (int c=0; c<columns; c++) {

                Button button = new Button(context.getApplicationContext());
                button.setText(buttons[count]);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                button.setId(count++);
                button.setOnClickListener(this);

                tr.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            }
            layout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onClick(View v) {
        if(v.getClass()==Button.class) {
            Button bt = (Button) v;
            mCallback.onKeyboardClick(0, bt.getText() + "");
        }
    }

    public interface OnHeadlineSelectedListener {
        public void onKeyboardClick(int type, String msg);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

}
