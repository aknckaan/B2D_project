package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DoctorList extends AppCompatActivity {


    TableLayout tblDocList;
    public ArrayList<String>arr;
    public ArrayList<String>arr2;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        ProgressDialog pd= new ProgressDialog(DoctorList.this);


       tblDocList = (TableLayout) findViewById(R.id.tblDocList);
        id=getIntent().getIntExtra("ID",0);

        new ArrayList<String>();

        final GetDoctorList getDoc = new GetDoctorList(this,pd);
        final GetRequestList reqs = new GetRequestList(id,pd);


        reqs.execute();
        getDoc.execute();
        pd.show();

        try {
            arr2=reqs.get();
            arr=getDoc.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });
        for (int i = 0; i < arr.size(); i=i+3) {
            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            // Creation textView

            final TextView text = new TextView(this);


            Boolean requested=false;

            for(int j= 0;j<arr2.size();j++)
            {
                if(arr2.get(j).equals(arr.get(i+2)))
                {
                    arr2.remove(j);
                    requested=true;

                    text.setTextColor(Color.GRAY);

                }
            }

            //tableRow.setClickable(!requested);


            text.setText(arr.get(i)+" "+ arr.get(i+1)+" "+arr.get(i+2)+"   ");
            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            final Button button = new Button(this);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow tr=(TableRow) view.getParent();

                    TextView tw=(TextView) tr.getChildAt(0);
                    String str= (String) tw.getText();

                    ProgressDialog pd= new ProgressDialog(DoctorList.this);
                    final SendRequest sendReq = new SendRequest(Integer.parseInt(str.split(" ")[2]),id,pd);

                    try {
                        sendReq.execute();
                        pd.show();
                        String res=sendReq.get();
                        if(res.equals("1"))
                        {
                            ((Button)view).setText("APPLIED");
                            tw.setTextColor(Color.GRAY);
                            ((Button)view).setTextColor(Color.GRAY);
                            Toast.makeText(getApplicationContext(),"Request sent!",Toast.LENGTH_LONG).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                                e.printStackTrace();
                    }
                }
            });

            // Creation  button

            if(requested)
            {
                button.setTextColor(Color.GRAY);
                button.setText("APPLIED");
            }
            else
                button.setText("Apply");

            button.setClickable(!requested);
            button.setEnabled(!requested);

            button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            /*button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        //final TableRow parent = (TableRow) v.getParent();
                        //tblDocList.removeView(parent);

                    final TableRow parent = (TableRow) v.getParent();
                    tblDocList.getChildAt();


                }
            });*/

            tableRow.addView(text);
            tableRow.addView(button);

            tblDocList.addView(tableRow);
        }


    }


}
