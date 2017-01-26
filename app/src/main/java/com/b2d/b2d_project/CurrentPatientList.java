package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
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
        TableRow.LayoutParams lp1 = new  TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams lp2;
        lp2=new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1);
        lp2.span=6;
        lp1.span=3;
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
        lp.rightMargin =  (int) ((float) 10 * scale);

        textGender1.setTypeface(null, Typeface.BOLD_ITALIC);
        textSurname1.setTypeface(null, Typeface.BOLD_ITALIC);
        textId1.setTypeface(null, Typeface.BOLD_ITALIC);
        textName1.setTypeface(null, Typeface.BOLD_ITALIC);
        textName1.setTextSize(20);
        textGender1.setTextSize(20);
        textSurname1.setTextSize(20);
        textId1.setTextSize(20);

        textName1.setLayoutParams(lp);
        tableRow1.addView(textName1);

        textSurname1.setLayoutParams(lp);
        tableRow1.addView(textSurname1);

        textGender1.setLayoutParams(lp);
        tableRow1.addView(textGender1);

        textId1.setLayoutParams(lp1);
        tableRow1.addView(textId1);

        final TableRow tableRow2 = new TableRow(this);
        //line black
        View v0 = new View(this);
        v0.setLayoutParams(lp2);
        v0.setBackgroundColor(Color.rgb(255, 255, 255));
        tableRow2.addView(v0);
        //



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

            View v1 = new View(this);
            lp2.span=5;
            v1.setLayoutParams(lp2);
            v1.setBackgroundColor(Color.rgb(192, 192, 192));
            tableRow22.addView(v1);
            //

            final TextView textName = new TextView(this);
            final TextView textSurname = new TextView(this);
            final TextView textId = new TextView(this);
            final TextView textGender = new TextView(this);

            textGender.setText(currp.get(i).gender);
            textName.setText(currp.get(i).name);
            textSurname.setText(currp.get(i).surname);
            textId.setText(currp.get(i).id);

            textGender.setTextSize(17);
            textName.setTextSize(17);
            textSurname.setTextSize(17);
            textId.setTextSize(17);


            textGender.setLayoutParams(lp);
            textName.setLayoutParams(lp);
            textSurname.setLayoutParams(lp);
            textId.setLayoutParams(lp);

            final ImageButton button = new ImageButton(this);
            final ImageButton button2 = new ImageButton(this);

            final String pid =currp.get(i).id;
            final String patient = currp.get(i).name+" "+currp.get(i).surname+" ( id "+currp.get(i).id+")";

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    displayDialog(pid,pd,view,patient);

                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(CurrentPatientList.this,PatientFiles.class);
                    i.putExtra("PId",pid);
                    startActivity(i);

                }
            });

            //button image
            Bitmap myBm= BitmapFactory.decodeResource(getResources(), R.drawable.cross);
            Bitmap myBm2= BitmapFactory.decodeResource(getResources(), R.drawable.brain);

            myBm = Bitmap.createScaledBitmap(myBm,100,100,true);
            myBm2 = Bitmap.createScaledBitmap(myBm2,100,100,true);

            button.setImageBitmap(myBm);
            button2.setImageBitmap(myBm2);

            button.setBackgroundColor(Color.TRANSPARENT);
            button2.setBackgroundColor(Color.TRANSPARENT);

            TableRow.LayoutParams lpBut= new TableRow.LayoutParams(100,100);
            lpBut.leftMargin=(int) ((float) 10 * scale);

            button.setLayoutParams(lpBut);

            button2.setLayoutParams(new TableRow.LayoutParams(120,120));

            tableRow.addView(textName);
            tableRow.addView(textSurname);
            tableRow.addView(textGender);
            tableRow.addView(textId);
            tableRow.addView(button2);
            tableRow.addView(button);



            tblCurrPatients.addView(tableRow);
            tblCurrPatients.addView(tableRow22);
        }

    }

    public void displayDialog(final String pid, final ProgressDialog pd, final View view, String patient)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        AcceptRequest ar = new AcceptRequest("0",pid,id+"",pd);
                        ar.execute();

                        final TableRow parent = (TableRow) view.getParent();
                        tblCurrPatients.removeView(parent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        return;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete patient: "+patient+"?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
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



