package com.b2d.b2d_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
        //görsel
        final TableRow tableRow1 = new TableRow(this);
        TableRow.LayoutParams lp = new  TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRow1.setLayoutParams(lp);
        TableRow.LayoutParams lp1 = new  TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams lp2;
        lp2=new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1);
        lp2.span=6;
        lp1.span=3;

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
        //line white
        View v0 = new View(this);
        v0.setLayoutParams(lp2);
        v0.setBackgroundColor(Color.rgb(255, 255, 255));

        tableRow2.addView(v0);

        tblNewPatients.setGravity(Gravity.LEFT);

        tblNewPatients.addView(tableRow1);
        tblNewPatients.addView(tableRow2);


        //

        for (int i = 0; i < newp.size(); i=i+1) {
            // Creation row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            // Creation textView

            //line grey
            final TableRow tableRow22 = new TableRow(this);
            tableRow22.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            View v = new View(this);
            v.setLayoutParams(lp2);
            v.setBackgroundColor(Color.rgb(192, 192, 192));
            tableRow22.addView(v);

            //


            final TextView text = new TextView(this);

            Boolean requested=false;
            final TextView textName = new TextView(this);
            final TextView textSurname = new TextView(this);
            final TextView textId = new TextView(this);
            final TextView textGender = new TextView(this);

            textGender.setText(newp.get(i).gender);
            textName.setText(newp.get(i).name);
            textSurname.setText(newp.get(i).surname);
            textId.setText(newp.get(i).id);

            textGender.setTextSize(17);
            textName.setTextSize(17);
            textSurname.setTextSize(17);
            textId.setTextSize(17);


            textGender.setLayoutParams(lp);
            textId.setLayoutParams(lp);
            textName.setLayoutParams(lp);
            textSurname.setLayoutParams(lp);

            final ImageButton button = new ImageButton(this);
            final ImageButton button2 = new ImageButton(this);

            final NewPatient np2 = newp.get(i);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow tr=(TableRow) view.getParent();
                    ProgressDialog pd= new ProgressDialog(NewPatientList.this);

                    AcceptRequest ar= new AcceptRequest("1",np2.id+"",id+"",pd);
                    ar.execute();

                    final TableRow parent = (TableRow) view.getParent();
                    tblNewPatients.removeView(parent);


                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayDialog(np2,view);
                }
            });

            // Creation  button


            //button.setText("Accept");
            //button2.setText("Reject");

            //button.setTextColor(Color.WHITE);
            //button2.setTextColor(Color.WHITE);

            Bitmap myBm= BitmapFactory.decodeResource(getResources(), R.drawable.confirmation);
            myBm = Bitmap.createScaledBitmap(myBm,100,100,true);
            button.setImageBitmap(myBm);

            Bitmap myBm2= BitmapFactory.decodeResource(getResources(), R.drawable.cross);
            myBm2 = Bitmap.createScaledBitmap(myBm2,100,100,true);
            button2.setImageBitmap(myBm2);

            button.setBackgroundColor(Color.TRANSPARENT);
            button2.setBackgroundColor(Color.TRANSPARENT);

            button.setLayoutParams(new TableRow.LayoutParams(100, 100));

            tableRow.addView(textName);
            tableRow.addView(textSurname);
            tableRow.addView(textGender);
            tableRow.addView(textId);
            tableRow.addView(button);

            TableRow.LayoutParams par = new TableRow.LayoutParams(100,100);
            par.leftMargin =  (int) ((float) 10 * scale);
            button2.setLayoutParams(par);
            tableRow.addView(button2);


            tblNewPatients.addView(tableRow);
            tblNewPatients.addView(tableRow22);
        }

    }
    public void displayDialog(final NewPatient np2, final View view)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        TableRow tr=(TableRow) view.getParent();
                        ProgressDialog pd= new ProgressDialog(NewPatientList.this);

                        AcceptRequest ar= new AcceptRequest("0",np2.id+"",id+"",pd);
                        ar.execute();

                        final TableRow parent = (TableRow) view.getParent();
                        tblNewPatients.removeView(parent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        return;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to reject patient: "+np2.name+" "+np2.surname+" ( id "+np2.id+")"+"?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

}
    class NewPatient {
        public String id, name, surname, age, gender;


        public NewPatient(String id, String name, String surname, String age, String gender) {

            this.id = id;
            this.name = name;
            this.surname = surname;
            this.age = age;
            this.gender = gender;
        }

    }


