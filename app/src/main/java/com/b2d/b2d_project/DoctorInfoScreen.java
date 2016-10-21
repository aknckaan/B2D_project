package com.b2d.b2d_project;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by buseburcu on 20.10.2016.
 */

public class DoctorInfoScreen extends AppCompatActivity {

    public DoctorInfoScreen() {
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_doctor_info);

        TableLayout tl= (TableLayout) findViewById(R.id.DoctorTable);

        ArrayList<View> arr=new ArrayList<View>();


       tl.addView(new Row(this),0);
        tl.addView(new Row(this),1);

    }

    public class Row extends TableRow
    {
        Button btn;
        ImageView img;


        public Row(Context context) {

            super(context);
            this.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            setContentView(R.layout.table_row);
            btn =(Button) findViewById(R.id.btnRow);
            img=(ImageView)findViewById(R.id.imgvRow);
        }
    }
}
