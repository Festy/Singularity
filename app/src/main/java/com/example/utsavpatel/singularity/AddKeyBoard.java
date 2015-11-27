package com.example.utsavpatel.singularity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by utsavpatel on 11/27/15.
 */
public class AddKeyBoard extends Activity{
    public static void addKeyboard(Context context, View view, LayoutInflater inflater) {

        int columns = 6;
        int rows = 5;
        int count=0;

        TableLayout layout = (TableLayout) view.findViewById(R.id.table);

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
