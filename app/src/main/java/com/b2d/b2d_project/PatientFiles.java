package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class PatientFiles extends AppCompatActivity {

    TextView name;
    TextView surname;
    TextView address;
    TextView age;
    TextView gender;
    TextView phone;
    TableLayout tblFiles;
    String pId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_files);
        name = (TextView) findViewById(R.id.etPFName);
        surname = (TextView) findViewById(R.id.etPFSurname);
        gender = (TextView) findViewById(R.id.etPFGender);
        phone = (TextView) findViewById(R.id.etPFPhone);
        address = (TextView) findViewById(R.id.etPFAddress);
        age = (TextView) findViewById(R.id.etPFAge);
        tblFiles = (TableLayout) findViewById(R.id.tblPF);

        pId= getIntent().getStringExtra("PId");

        if(pId!=null)
        {
            ProgressDialog pd = new ProgressDialog(this);

            GetInfo gi = new GetInfo(pId,pd);
            ArrayList arr;
            gi.execute();
            try {
               arr= gi.get();
                name.setText((String)arr.get(1));
                surname.setText((String)arr.get(2));
               gender.setText((String)arr.get(5));
                age.setText((String)arr.get(6));
                address.setText((String)arr.get(9)+" / "+(String)arr.get(8)+" / "+(String)arr.get(7));
                phone.setText((String)arr.get(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            GetFileContents gf = new GetFileContents(pId,pd);
            ArrayList arr2;
            gf.execute();
            try {
                arr2=gf.get();

                Collections.sort(arr2);
                Collections.reverse(arr2);

            TableRow.LayoutParams lp1 = new  TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams lp2;
            lp2=new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1);
            lp2.span=1;
            lp1.span=1;
            for (int i = 0; i < arr2.size(); i=i+1) {
                // Creation row

                final TableRow tableRow = new TableRow(this);
                final TableRow tableRow22 = new TableRow(this);

                tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.FILL_PARENT));

                // Creation textView
                //line grey
                tableRow22.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.FILL_PARENT));

                View v1 = new View(this);
                lp2.span=1;
                v1.setLayoutParams(lp2);
                v1.setBackgroundColor(Color.rgb(192, 192, 192));
                tableRow22.addView(v1);


                final TextView textFile = new TextView(this);
                textFile.setTextSize(22);
                textFile.setText((String)arr2.get(i));

                tableRow.addView(textFile);

                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TableRow r = (TableRow)view;
                        TextView tv =(TextView) r.getChildAt(0);
                        String file=tv.getText().toString();

                        Intent i  = new Intent(PatientFiles.this,DataChart.class);
                        i.putExtra("File",file);
                        i.putExtra("PId",pId);
                        startActivity(i);

                    }
                });

                tblFiles.addView(tableRow);
                tblFiles.addView(tableRow22);
            }


            pd.dismiss();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


    }
}
