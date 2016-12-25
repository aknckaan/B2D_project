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
    public ArrayList<String> arr2;
    public ArrayList<NewPatient> newp;
    int id;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient_list);
        ProgressDialog pd = new ProgressDialog(NewPatientList.this);


        tblNewPatients = (TableLayout) findViewById(R.id.tblNewPatients);
        id = getIntent().getIntExtra("ID", 0);

        new ArrayList<String>();


        final GetNewPatients getNewPatients = new GetNewPatients(id, pd);
        getNewPatients.execute();

        try {
            arr = getNewPatients.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        newp = new ArrayList<NewPatient>();
        for (int i = 0; i < arr.size(); i = i + 5) {

            NewPatient np = new NewPatient(arr.get(i), arr.get(i + 1), arr.get(i + 2), arr.get(i + 3), arr.get(i + 4));

            newp.add(np);

        }

        for (int i = 0; i < newp.size(); i=i+1) {
            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            // Creation textView

            final TextView text = new TextView(this);

            Boolean requested=false;

            text.setText(newp.get(i).id+" "+ newp.get(i).name +" "+ newp.get(i).surname +" " + newp.get(i).gender);
            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            final Button button = new Button(this);
            final Button button2 = new Button(this);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow tr=(TableRow) view.getParent();
                    ProgressDialog pd= new ProgressDialog(NewPatientList.this);
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow tr=(TableRow) view.getParent();
                    ProgressDialog pd= new ProgressDialog(NewPatientList.this);
                }
            });

            // Creation  button


            button.setText("Accept");
            button2.setText("Reject");

            button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.addView(text);
            tableRow.addView(button);

            button2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.addView(button2);

            tblNewPatients.addView(tableRow);
        }

    }

}
    class NewPatient {
        String id, name, surname, age, gender;


        public NewPatient(String id, String name, String surname, String age, String gender) {

            this.id = id;
            this.name = name;
            this.surname = surname;
            this.age = age;
            this.gender = gender;
        }

    }


