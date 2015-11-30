package com.example.utsavpatel.singularity;
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
public class KeyboardFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.keyboard_fragment, container, false);
        addKeyboard(getContext(), view, inflater);
        return view;
    }
    public static void addKeyboard(Context context, View view, LayoutInflater inflater) {

        int columns = 6;
        int rows = 6;
        int count=0;

        TableLayout layout = (TableLayout) view.findViewById(R.id.keyboard_fragment);

        for (int r=0; r<rows; r++) {

            TableRow tr = new TableRow(context);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (int c=0; c<columns; c++) {

                Button button = new Button(context.getApplicationContext());
                button.setText(Character.toString((char) (count + 'A')));
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                button.setId(count++);

                tr.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            }
            layout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }

}
