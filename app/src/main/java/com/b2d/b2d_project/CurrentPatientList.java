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

public class CurrentPatientList extends AppCompatActivity {

    TableLayout tblCurrPatients;
    public ArrayList<String> arr;
    public ArrayList<String> arr2;
    public ArrayList<CurrentPatient> currp;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_patient_list);

        ProgressDialog pd = new ProgressDialog(CurrentPatientList.this);


        tblCurrPatients = (TableLayout) findViewById(R.id.tblCurrPatients);
        id = getIntent().getIntExtra("ID", 0);

        new ArrayList<String>();


        final GetCurrentPatientList getCurrPatients = new GetCurrentPatientList(id, pd);
        getCurrPatients.execute();

        try {
            arr = getCurrPatients.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        currp = new ArrayList<CurrentPatient>();
        for (int i = 0; i < arr.size(); i = i + 5) {

            CurrentPatient cp = new CurrentPatient(arr.get(i), arr.get(i + 1), arr.get(i + 2), arr.get(i + 3), arr.get(i + 4));

            currp.add(cp);

        }

        for (int i = 0; i < currp.size(); i=i+1) {
            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            // Creation textView

            final TextView text = new TextView(this);

            Boolean requested=false;

            text.setText(currp.get(i).id+" "+ currp.get(i).name +" "+ currp.get(i).surname +" " + currp.get(i).gender);
            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            final Button button = new Button(this);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow tr=(TableRow) view.getParent();
                    ProgressDialog pd= new ProgressDialog(CurrentPatientList.this);
                }
            });
            // Creation  button

            button.setText("Reject");

            button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.addView(text);
            tableRow.addView(button);



            tblCurrPatients.addView(tableRow);
        }

    }

}
class CurrentPatient {
    String id, name, surname, age, gender;


    public CurrentPatient(String id, String name, String surname, String age, String gender) {

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
    }

}



