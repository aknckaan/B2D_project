package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DoctorList extends AppCompatActivity {

    TableLayout tblDocList;
    public ArrayList<String>arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

       tblDocList = (TableLayout) findViewById(R.id.tblDocList);
        ProgressDialog pd= new ProgressDialog(DoctorList.this);
      new ArrayList<String>();

        final GetDoctorList getDoc = new GetDoctorList(this,pd);
        pd.show();
        try {
            getDoc.execute();
            arr=getDoc.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < arr.size(); i=i+3) {
            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            // Creation textView
            final TextView text = new TextView(this);
            text.setText(arr.get(i)+" "+ arr.get(i+1)+" "+arr.get(i+2));
            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Creation  button
            final Button button = new Button(this);
            button.setText("Delete");
            button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TableRow parent = (TableRow) v.getParent();
                    tblDocList.removeView(parent);
                }
            });

            tableRow.addView(text);
            tableRow.addView(button);

            tblDocList.addView(tableRow);
        }


    }


}
