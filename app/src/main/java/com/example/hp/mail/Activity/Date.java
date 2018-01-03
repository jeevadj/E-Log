package com.example.hp.mail.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mail.R;
import com.example.hp.mail.adapterclass.DT;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


/**
 * Created by jeeva on 17-03-2017.
 */

public class Date extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    FloatingActionButton fab1,fab2;
    String sdate,edate;
    Button b1;
    Snackbar snackbar;
    CoordinatorLayout c1;
    int i=0;
    TextView textview,textview2,textview3,textview4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datepicker);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2=(FloatingActionButton)findViewById(R.id.fab2);
//        b1=(Button)findViewById(R.id.button2);

        textview=(TextView)findViewById(R.id.startingtime) ;
        textview2=(TextView)findViewById(R.id.startingdate);
        textview3=(TextView)findViewById(R.id.textView3);
        textview4=(TextView)findViewById(R.id.textView4);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=0;
                Calendar now = Calendar.getInstance();
                DatePickerDialog datepickerdialog = DatePickerDialog.newInstance(
                        Date.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                datepickerdialog.show(getFragmentManager(),"DatePickerdialog"); //show dialog
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=1;
                Calendar now=Calendar.getInstance();
                DatePickerDialog datePickerDialog=DatePickerDialog.newInstance(Date.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show(getFragmentManager(),"DatePickerdailog");
            }
        });
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Date.this,MainActivity.class));
//            }
//        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if(i==0){
            sdate = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
            DT.setD(dayOfMonth);
            DT.setM(++monthOfYear);
            DT.setY(year);
            Toast.makeText(Date.this, sdate, Toast.LENGTH_SHORT).show();
        }
        else
        {
            edate = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
            DT.setEd(dayOfMonth);
            DT.setEm(++monthOfYear);
            DT.setEy(year);
            Toast.makeText(Date.this, edate, Toast.LENGTH_SHORT).show();
        }



        Calendar now = Calendar.getInstance();
        TimePickerDialog timepickerdialog = TimePickerDialog.newInstance(
                Date.this,
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

        timepickerdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Toast.makeText(Date.this, "Cancel choosing time", Toast.LENGTH_SHORT).show();
            }
        });
        timepickerdialog.show(getFragmentManager(), "Timepickerdialog"); //show time picker dialog

    }
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
       // String time = hourString + "h" + minuteString + "m" + secondString + "s";
        if(i==0) {
            String stime = hourString + "h" + minuteString + "m" + secondString + "s";
            DT.setH(hourOfDay);
            DT.setMin(minute);
            textview.setText(sdate);
            textview2.setText(stime);
//            snackbar = Snackbar
//                    .make(c1, "Alarm set for " +stime+" on "+sdate, Snackbar.LENGTH_LONG)
//                    .setAction("Dismiss", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
////                        Snackbar snackbar1 = Snackbar.make(c1, "Message is restored!", Snackbar.LENGTH_SHORT);
////                        snackbar1.show();
//                            snackbar.dismiss();
//                        }
//                    });

//            snackbar.show();
        }
        else{
            String etime = hourString + "h" + minuteString + "m" + secondString + "s";
            DT.setEh(hourOfDay);
            DT.setEmin(minute);
            textview3.setText(edate);
            textview4.setText(etime);
//            snackbar = Snackbar
//                    .make(c1, "Alarm set for " +etime+" on "+edate, Snackbar.LENGTH_LONG)
//                    .setAction("Dismiss", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
////                        Snackbar snackbar1 = Snackbar.make(c1, "Message is restored!", Snackbar.LENGTH_SHORT);
////                        snackbar1.show();
//                            snackbar.dismiss();
//                        }
//                    });
//
//            snackbar.show();
        }

    }
}
