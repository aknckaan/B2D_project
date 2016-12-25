package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NewPatientList extends AppCompatActivity {

    TableLayout tblNewPatients;
    public ArrayList<String> arr;
    public ArrayList<String>arr2;
    int id;
    @Override

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_new_patient_list);
            ProgressDialog pd= new ProgressDialog(NewPatientList.this);


            tblNewPatients = (TableLayout) findViewById(R.id.tblNewPatients);
            id=getIntent().getIntExtra("ID",0);

            new ArrayList<String>();


            final GetNewPatients getNewPatients = new GetNewPatients(this,pd);


            try {
                arr=getNewPatients.get();
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


                final Button button = new Button(this);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TableRow tr=(TableRow) view.getParent();

                        TextView tw=(TextView) tr.getChildAt(0);
                        String str= (String) tw.getText();

                        ProgressDialog pd= new ProgressDialog(NewPatientList.this);
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

                tableRow.addView(text);
                tableRow.addView(button);

                tblNewPatients.addView(tableRow);
            }
    }
}
