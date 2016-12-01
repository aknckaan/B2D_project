package com.b2d.b2d_project;

import android.app.ProgressDialog;
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
    ProgressDialog pd;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

       tblDocList = (TableLayout) findViewById(R.id.tblDocList);
        id=getIntent().getIntExtra("ID",0);
        pd= new ProgressDialog(DoctorList.this);
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
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final TableRow t= (TableRow) view;
                    final TextView tw = (TextView) t.getChildAt(0);

                    Button btn = (Button) t.getChildAt(1);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String str = (String) tw.getText();

                            final SendRequest sendReq = new SendRequest(Integer.parseInt(str.split(" ")[2]),id,pd);
                            pd.show();
                            try {
                                sendReq.execute();
                                String res=sendReq.get();
                                if(res.equals("1"))
                                {
                                    Toast.makeText(getApplicationContext(),"Request sent!",Toast.LENGTH_LONG).show();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            });
            // Creation  button
            final Button button = new Button(this);
            button.setText("Apply");
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
