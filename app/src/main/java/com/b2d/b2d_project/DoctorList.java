package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
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

        ArrayList<Doctor> docs= new ArrayList<Doctor>();
        ArrayList<DoctorAccept> requestList= new ArrayList<DoctorAccept>();

        for(int i=0;i<arr.size();i=i+3)
        {
            docs.add(new Doctor(arr.get(i),arr.get(i+1),arr.get(i+2)));
        }

        for(int i=0;i<arr2.size();i=i+2)
        {
            requestList.add(new DoctorAccept(arr2.get(i),arr2.get(i+1)));
        }
        final TextView text0 = new TextView(this);
        final TextView text11 = new TextView(this);
        final TextView text22 = new TextView(this);
        final TextView text33 = new TextView(this);

        text0.setText("Name");
        text11.setText("Surname");
        text22.setText("ID");
        text33.setText("STATUS");

        TableRow.LayoutParams layout0 =new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        Float scale0;
        scale0 = this.getResources().getDisplayMetrics().density;
        layout0.leftMargin =  (int) ((float) 15 * scale0);

        text0.setLayoutParams(layout0);
        text11.setLayoutParams(layout0);
        text22.setLayoutParams(layout0);
        text33.setLayoutParams(layout0);

        text0.setTypeface(null, Typeface.BOLD_ITALIC);
        text11.setTypeface(null, Typeface.BOLD_ITALIC);
        text22.setTypeface(null, Typeface.BOLD_ITALIC);
        text33.setTypeface(null, Typeface.BOLD_ITALIC);

        text0.setTextSize(20);
        text11.setTextSize(20);
        text22.setTextSize(20);
        text33.setTextSize(20);

        final TableRow tableRow0 = new TableRow(this);
        tableRow0.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        tableRow0.addView(text0);
        tableRow0.addView(text11);
        tableRow0.addView(text22);
        tableRow0.addView(text33);

        final TableRow tableRow2 = new TableRow(this);
        //line black
        View v0 = new View(this);
        TableRow.LayoutParams lp2 =new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        lp2.span=4;
        v0.setLayoutParams(lp2);
        v0.setBackgroundColor(Color.rgb(255, 255, 255));
        tableRow2.addView(v0);

        tblDocList.addView(tableRow0);
        tblDocList.addView(tableRow2);

        for (int i = 0; i < docs.size(); i++) {
            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            // Creation textView

            final TextView text = new TextView(this);
            final TextView text1 = new TextView(this);
            final TextView text2 = new TextView(this);


            Boolean requested=false;
            Boolean accepted=false;

            for(int j= 0;j<requestList.size();j++)
            {
                if(requestList.get(j).dId.equals(docs.get(i).dId))
                {
                    arr2.remove(j);

                    if(requestList.get(j).accept.equals("1"))
                        accepted=true;
                    else
                        requested=true;

                    text.setTextColor(Color.GRAY);
                    text1.setTextColor(Color.GRAY);
                    text2.setTextColor(Color.GRAY);

                }
            }


           text.setText(docs.get(i).name);
           text1.setText(docs.get(i).surname);
           text2.setText(docs.get(i).dId);
            TableRow.LayoutParams layout =new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            Float scale;
            scale = this.getResources().getDisplayMetrics().density;
            layout.leftMargin =  (int) ((float) 15 * scale);
            text.setLayoutParams(layout);
            text1.setLayoutParams(layout);
            text2.setLayoutParams(layout);

            final Button button = new Button(this);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow tr=(TableRow) view.getParent();

                    TextView tw=(TextView) tr.getChildAt(2);
                    String str= (String) tw.getText();

                    ProgressDialog pd= new ProgressDialog(DoctorList.this);
                    final SendRequest sendReq = new SendRequest(Integer.parseInt(str),id,pd);

                    try {
                        sendReq.execute();
                        pd.show();
                        String res=sendReq.get();
                        if(res.equals("1"))
                        {
                            ((Button)view).setText("APPLIED");
                            tw.setTextColor(Color.GRAY);
                            ((Button)view).setTextColor(Color.GRAY);
                            ((Button)view).setBackgroundColor(Color.TRANSPARENT);
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
                button.setBackgroundColor(Color.TRANSPARENT);
            }
            else
                button.setText("Apply");

            if(requested || accepted)
            {
                button.setClickable(false);
                button.setEnabled(false);

                if(accepted)
                {
                    button.setBackgroundColor(Color.TRANSPARENT);
                    button.setTextColor(Color.GREEN);
                    button.setText("ACCEPTED");
                }
            }
            else
            {
                button.setClickable(true);
                button.setEnabled(true);
            }


            button.setLayoutParams(layout);


            TableRow tableRow22 = new TableRow(this);
            tableRow22.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            View v1 = new View(this);
            lp2.span=4;
            v1.setLayoutParams(lp2);
            v1.setBackgroundColor(Color.rgb(192, 192, 192));
            tableRow22.addView(v1);



            tableRow.addView(text);
            tableRow.addView(text1);
            tableRow.addView(text2);

            tableRow.addView(button);
            tblDocList.addView(tableRow);
            tblDocList.addView(tableRow22);
        }


    }

    public class Doctor{
        String name,dId,surname;

        public Doctor(String name, String surname, String dId)
        {
            this.name=name;
            this.dId=dId;
            this.surname=surname;
        }
    }

    public class DoctorAccept{
        String dId,accept;

        public DoctorAccept(String dId, String accept)
        {
            this.dId=dId;
            this.accept=accept;
        }
    }

}
