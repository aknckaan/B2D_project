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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CurrentPatientList extends AppCompatActivity {

    TableLayout tblCurrPatients;
    public ArrayList<String> arr;
    public ArrayList<CurrentPatient> currp;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_patient_list);

        final ProgressDialog pd = new ProgressDialog(CurrentPatientList.this);


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

        final TableRow tableRow1 = new TableRow(this);
        TableRow.LayoutParams lp = new  TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRow1.setLayoutParams(lp);

        final TextView textName1 = new TextView(this);
        final TextView textSurname1 = new TextView(this);
        final TextView textId1 = new TextView(this);
        final TextView textGender1 = new TextView(this);

        textGender1.setText("Gender");
        textName1.setText("Name");
        textSurname1.setText("Surname");
        textId1.setText("Id");

        Float scale;
        scale = this.getResources().getDisplayMetrics().density;
        lp.rightMargin =  (int) ((float) 13 * scale);

        textGender1.setTypeface(null, Typeface.BOLD_ITALIC);
        textSurname1.setTypeface(null, Typeface.BOLD_ITALIC);
        textId1.setTypeface(null, Typeface.BOLD_ITALIC);
        textName1.setTypeface(null, Typeface.BOLD_ITALIC);
        textName1.setTextSize(18);
        textGender1.setTextSize(18);
        textSurname1.setTextSize(18);
        textId1.setTextSize(18);

        textName1.setLayoutParams(lp);
        tableRow1.addView(textName1);

        textSurname1.setLayoutParams(lp);
        tableRow1.addView(textSurname1);

        textGender1.setLayoutParams(lp);
        tableRow1.addView(textGender1);

        //lp.span=2;
        textId1.setLayoutParams(lp);
        tableRow1.addView(textId1);

        final TableRow tableRow2 = new TableRow(this);
        //line black
        View v0 = new View(this);
        v0.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
        v0.setBackgroundColor(Color.rgb(0, 0, 0));
        View v11 = new View(this);
        v11.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
        v11.setBackgroundColor(Color.rgb(0, 0, 0));
        View v22 = new View(this);
        v22.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
        v22.setBackgroundColor(Color.rgb(0, 0, 0));
        View v33 = new View(this);
        v33.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
        v33.setBackgroundColor(Color.rgb(0, 0, 0));

        tableRow2.addView(v0);
        tableRow2.addView(v11);
        tableRow2.addView(v22);
        tableRow2.addView(v33);




        tblCurrPatients.addView(tableRow1);
        tblCurrPatients.addView(tableRow2);

        for (int i = 0; i < currp.size(); i=i+1) {
            // Creation row

            final TableRow tableRow = new TableRow(this);
            final TableRow tableRow22 = new TableRow(this);

            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            // Creation textView
            //line grey
            tableRow22.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            View v = new View(this);
            v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
            v.setBackgroundColor(Color.rgb(51, 51, 51));

            View v1 = new View(this);
            v1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
            v1.setBackgroundColor(Color.rgb(51, 51, 51));

            View v2 = new View(this);
            v2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
            v2.setBackgroundColor(Color.rgb(51, 51, 51));

            View v3 = new View(this);
            v3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
            v3.setBackgroundColor(Color.rgb(51, 51, 51));

            tableRow22.addView(v);
            tableRow22.addView(v1);
            tableRow22.addView(v2);
            tableRow22.addView(v3);
            //

            final TextView textName = new TextView(this);
            final TextView textSurname = new TextView(this);
            final TextView textId = new TextView(this);
            final TextView textGender = new TextView(this);

            textGender.setText(currp.get(i).gender);
            textName.setText(currp.get(i).name);
            textSurname.setText(currp.get(i).surname);
            textId.setText(currp.get(i).id);

            textGender.setTextSize(13);
            textName.setTextSize(13);
            textSurname.setTextSize(13);
            textId.setTextSize(13);


            textGender.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            textId.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            textName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            textSurname.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            final Button button = new Button(this);
            final String pid =currp.get(i).id;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AcceptRequest ar = new AcceptRequest("0",pid,id+"",pd);
                    ar.execute();

                    final TableRow parent = (TableRow) view.getParent();
                    tblCurrPatients.removeView(parent);

                }
            });

            //button image
            button.setText("Delete");
            button.setTextColor(Color.WHITE);
            button.setBackgroundColor(Color.rgb(204,0,0));
            //

            button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.addView(textName);
            tableRow.addView(textSurname);
            tableRow.addView(textGender);
            tableRow.addView(textId);
            tableRow.addView(button);



            tblCurrPatients.addView(tableRow);
            tblCurrPatients.addView(tableRow22);
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



