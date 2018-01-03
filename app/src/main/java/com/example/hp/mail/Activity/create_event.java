package com.example.hp.mail.Activity;

import android.app.Fragment;
import android.app.Notification;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mail.CardView.myact;
import com.example.hp.mail.Activity.ImageCompressor;
import com.example.hp.mail.R;
import com.example.hp.mail.adapterclass.Adapter;
import com.example.hp.mail.adapterclass.DT;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 7/20/2017.
 */

public class create_event extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    int i,c=0;


    //public ArrayList<Adapter> adapters=new ArrayList<Adapter>();
    //public itemAdapter itemArrayAdapter = new itemAdapter(R.layout.row,adapters);
    Firebase fb;
    StorageReference storageReference;
    String url="https://fir-18ecb.firebaseio.com/";
    TextView starttime, startdate, enddate, endtime, en,des;
    public static TextView loc;
    Spinner spinner;
    RecyclerView recyclerView1;
    FloatingActionButton fab1, fab2;
    String sdate, edate;
    Button setevent;
    public Adapter ad;
    CircleImageView imageView;
    public ArrayList<Adapter> adap2=new ArrayList<Adapter>();
    public ArrayList<String> sr=new ArrayList<String>();
    NotificationManager Nm;
    public PendingIntent pi;
    //Context CurrentObj=getActivity();
    public Context context1;
    public Iterator<String> iterator=sr.iterator();
    public FragmentManager fragmentManager=getFragmentManager();
    View view;
    String locat="";
    ProgressDialog progressDialog;
    DateFormat df;
    String date;
    public int a=0;
    Uri imageUri,selectedImageUri;
    private static final int PICK_Camera_IMAGE=2;
    private static final int PICK_IMAGE=1;


    myact et=new myact();

    public create_event()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Firebase.setAndroidContext(getContext());
//        url=url+"Events/";
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view2;
        view=inflater.inflate(R.layout.event1,container,false);
        context1=view.getContext();
        //view2=inflater.inflate(R.layout.fragment,container,false);
        //recyclerView1=(RecyclerView)view2.findViewById(R.id.recyclerView);
        //recyclerView1.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //final GPSTracker gpsTracker = new GPSTracker(view.getContext());
        loc=(TextView)view.findViewById(R.id.location);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);
        Spinner spinner = (Spinner)view. findViewById(R.id.spinner);
        imageView=(CircleImageView)view.findViewById(R.id.eventimage);
        starttime = (TextView)view. findViewById(R.id.startingtime);
        startdate  = (TextView) view.findViewById(R.id.startingdate);
        enddate  = (TextView)view. findViewById(R.id.enddate);
        endtime  = (TextView)view. findViewById(R.id.endtime);
        setevent=(Button)view.findViewById(R.id.setEvent);
        en=(TextView)view.findViewById(R.id.event);
        des=(TextView)view.findViewById(R.id.description);
        List<String> categories = new ArrayList<String>();
        categories.add("No Notification");
        categories.add("Set Notification a day Before");
        categories.add("Set Notification One Week Before");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        df=new SimpleDateFormat("d MMM yyyy,HH:mm:ss a");
        date=df.format(Calendar.getInstance().getTime());
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(view.getContext(),Locationatmap.class));
                Adapter adapter=new Adapter();
                String smn=adapter.getLocation();
                loc.setText(smn);
            }
        });
        Nm=(NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(view.getContext(), Main3Activity.class);
        pi = PendingIntent.getActivity(view.getContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = 0;
                Calendar now = Calendar.getInstance();
                DatePickerDialog datepickerdialog = DatePickerDialog.newInstance(
                        create_event.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                datepickerdialog.show(getFragmentManager(), "DatePickerdialog");
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1;
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(create_event.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show(getFragmentManager(), "DatePickerdailog");
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=1;selectImage();
            }
        });
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = 1;
                context1.startActivity(new Intent(getActivity(),Locationatmap.class));

            }
        });

        setevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=en.getText().toString();
                String desc=des.getText().toString();
                String startd=startdate.getText().toString();
                String startt=starttime.getText().toString();
                String endd=enddate.getText().toString();
                String endt=endtime.getText().toString();
                locat=loc.getText().toString();
                DT.setLoc(locat);

                DT.setEname(name);
                DT.setDes(desc);
                DT.setStartD(startd);
                DT.setStartT(startt);
                DT.setEndD(endd);
                DT.setEndT(endt);



                if(name.isEmpty()&&desc.isEmpty()&&locat.isEmpty()&&startd.equals("Starting Date")&&startt.equals("Starting Time")&&endd.equals("Ending Date")&&endt.equals("Ending time")){
                    Toast.makeText(getContext(),"Enter all the fields...",Toast.LENGTH_SHORT).show();
                }
                else if(a==0){
                    Toast.makeText(getContext(), "Select an Image...", Toast.LENGTH_SHORT).show();
                }
                else {



                    //adapter2.setEname2(DT.getEname());
                    //adapter2.setDesp2(DT.getDes());

                    //ad.setDesp(des.getText().toString());

                    //System.out.print("jjjjjj"+DT.getDes()+"hi"+DT.getEname());

                    //Notification notify=-new Notification.Builder(getApplicationContext()).setContentTitle(DT.getEname())
                    // .setContentText("Event Setted").setContentTitle(DT.getDes()).setSmallIcon(R.drawable.dat1).build();
                    new MyTask().execute();

                    Notification notify = new Notification.Builder
                            (view.getContext()).setContentTitle(DT.getEname()).setContentText(DT.getDes())
                            //.addAction(R.drawable.ic_menu_share,"Speak",pi)
                            .setContentIntent(pi)
                            .setSmallIcon(R.drawable.logo).build();


                    notify.flags|=Notification.FLAG_AUTO_CANCEL;
                    Nm.notify(0,notify);

//                fragmentManager.beginTransaction().replace(R.id.frame_container,new myact()).commit();
                    //System.out.println("Size"+adapters.size());
                    //DT.setCount(DT.getCount()+1);
                    // adapters.add(DT.count-1,new Adapter(DT.getEname(),DT.getDes()));

                    // System.out.println("Count"+DT.getCount());
//                for (int i1 = adap2.size() - 1; i1 >= 0; i1--) {
//                    adap2.add(i1, new Adapter(DT.getEname(), DT.getDes()));
//
//                }


//                    if(DT.getCount()!=0)
//                    {
//                        adap2.add(new Adapter(DT.getEname(),DT.getDes()));
//                    }


//                DT.setAdapters(adap2);
//                Bundle args = new Bundle();
//                args.putString("YourKey", "YourValue");
//                et.setArguments(args);

                   // getFragmentManager().beginTransaction().replace(R.id.frame_container,new myact()).commit();
//                finish();
                    //sendEmail();

                }

            }
        });

        return view;
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose picture..");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    String fileName = "new-photo-name.jpg";
                    //create parameters for Intent with filename
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                    //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                    imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    //create new Intent
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, PICK_Camera_IMAGE);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            PICK_IMAGE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {

            if (requestCode == PICK_Camera_IMAGE) {
                if (resultCode == getActivity().RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;

                } else if (resultCode == getActivity().RESULT_CANCELED) {
                    Toast.makeText(getContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                try {
                    Uri tmpUri=Uri.fromFile(new File(ImageCompressor.with(getContext()).compress(selectedImageUri.toString())));
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), tmpUri);
                    imageView.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (requestCode == PICK_IMAGE) {
                if (resultCode == getActivity().RESULT_OK) {
                    selectedImageUri = data.getData();
                } else if (resultCode == getActivity().RESULT_CANCELED) {
                    Toast.makeText(getContext(), "Picture was not selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Picture was not selected", Toast.LENGTH_SHORT).show();
                }
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver() , selectedImageUri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }}}



    @Override
    public void onDateSet(DatePickerDialog view1, int year, int monthOfYear, int dayOfMonth) {
        if (i == 0) {
            sdate = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
            DT.setD(dayOfMonth);
            DT.setM(++monthOfYear);
            DT.setY(year);
            DT.setStartD(sdate);
            Toast.makeText(view1.getActivity(), sdate, Toast.LENGTH_SHORT).show();
        } else {
            edate = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
            DT.setEd(dayOfMonth);
            DT.setEm(++monthOfYear);
            DT.setEy(year);
            DT.setEndD(edate);
            enddate.setText(edate);
            Toast.makeText(view1.getActivity(), edate, Toast.LENGTH_SHORT).show();
        }


        Calendar now = Calendar.getInstance();
        TimePickerDialog timepickerdialog = TimePickerDialog.newInstance(
                create_event.this,
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

        timepickerdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Toast.makeText(getActivity(), "Cancel choosing time", Toast.LENGTH_SHORT).show();
            }
        });
        timepickerdialog.show(getFragmentManager(), "Timepickerdialog");


    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        // String time = hourString + "h" + minuteString + "m" + secondString + "s";
        if (i == 0) {
            String stime = hourString + "h" + minuteString + "m" + secondString + "s";
            DT.setH(hourOfDay);
            DT.setMin(minute);
            startdate.setText(sdate);
            starttime.setText(stime);
            DT.setStartT(stime);
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
        } else {
            String etime = hourString + "h" + minuteString + "m" + secondString + "s";
            DT.setEh(hourOfDay);
            DT.setEmin(minute);
            enddate.setText(edate);
            endtime.setText(etime);
            DT.setEndT(etime);
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



    public class MyTask extends AsyncTask<String, Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            fb=new Firebase(url);
            storageReference= FirebaseStorage.getInstance().getReference();
            final Adapter event=new Adapter();
            event.setSd(DT.getStartD());
            event.setDesp(DT.getDes());
            event.setName(DT.getEname());
            event.setLocation(DT.getLoc());
            event.setEd(DT.getEndD());

//            eventadapters event=new eventadapters();
//            event.setEname(DT.ename);
//            event.setDesc(DT.des);
//            event.setStartd(DT.startD);
//            event.setStartt(DT.startT);
//            event.setLocation(location);
//            event.setLat(DT.lat);
//            event.setLng(DT.lng);
//            event.setEndd(DT.endD);
//            event.setEndt(DT.endT);





            StorageReference sr=storageReference.child("Events").child(date+"@"+DT.ename);
            sr.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   Uri downloaduri=taskSnapshot.getDownloadUrl();
                    System.out.println("bowbow"+taskSnapshot.getStorage());
                   // Uri ndi=taskSnapshot.getUploadSessionUri();
                   // System.out.println("BOW"+ndi.toString());
                    String DownloadUri=downloaduri.toString();
                    event.setUrl(DownloadUri);
                    fb.child("Events").child(date+DT.ename).setValue(event);
                }
            });

            progressDialog.dismiss();
            a=0;
            getFragmentManager().beginTransaction().add(R.id.frame_container,new myact()).commit();

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(view.getContext());
            progressDialog.setMessage("Creating event...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();

        }
    }
}

