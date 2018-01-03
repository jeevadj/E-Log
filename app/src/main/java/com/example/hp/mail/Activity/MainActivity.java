package com.example.hp.mail.Activity;


import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;


import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.hp.mail.CardView.myact;
import com.example.hp.mail.CardView.newsfeed;
import com.example.hp.mail.R;
import com.example.hp.mail.adapterclass.Adapter;
import com.example.hp.mail.contacts;
import com.example.hp.mail.help;
import com.example.hp.mail.recycleradapters.itemAdapter;
import com.example.hp.mail.scanqr;

import java.util.ArrayList;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public  static final int RequestPermissionCode  = 1 ;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    Toolbar toolbar;
    public  ArrayList<String> array2=new ArrayList<String>();
    public ArrayList<Adapter> adapters=new ArrayList<Adapter>();
    public itemAdapter itemArrayAdapter = new itemAdapter(R.layout.row,adapters);
        public Iterator<String> iterator=array2.listIterator();
        public FragmentManager fragmentManager=getFragmentManager();
    int lastAction;
    float dX,dY;
    CircleImageView imag;
    TextView profile;
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedit;
    public final int requestPermission=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences("data",MODE_PRIVATE);
        sharedit=sharedPref.edit();


//        Toast.makeText(this, sharedPref.getString("email",null)+" "+sharedPref.getString("pass",null), Toast.LENGTH_SHORT).show();
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        Toast toast = Toast.makeText(this,"Enable permissions in settings", Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//        String url=getIntent().getExtras().getString("url");
//        String username=getIntent().getExtras().getString("username");
        NavigationView navigation=(NavigationView)findViewById(R.id.nav_view);
        View header=navigation.getHeaderView(0);
        imag=(CircleImageView)header.findViewById(R.id.profile_image);
        profile=(TextView)header.findViewById(R.id.profilename);
        System.out.println("bow2"+sharedPref.getString("email",null));
        System.out.println("bow2"+sharedPref.getString("pass",null));
        System.out.println("bow"+sharedPref.getString("user",null));
        System.out.println("bow"+sharedPref.getString("url",null));
        profile.setText(sharedPref.getString("user",null));
        String url=sharedPref.getString("url",null);



         Glide.with(getApplicationContext()).load(url).into(imag);
//        profile.setText(username);

                  fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                fab.setX(Gravity.CENTER);
//                return true;
//            }
//        });

        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Context context=view.getContext();
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                         dX = view.getX() - event.getRawX();
                         dY = view.getY() - event.getRawY();

                         lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.setY(event.getRawY() + dY);
                        view.setX(event.getRawX() + dX);
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN)

                            //startActivity(new Intent(getApplicationContext(), help.class));
                        getFragmentManager().beginTransaction().replace(R.id.frame_container,new create_event()).commit();
                            toolbar.setTitle("Create Event");

                            break;


                }
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //Toast.makeText(view.getContext(), "Clicked!", Toast.LENGTH_SHORT).show();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        //array2.add("dhamu");
//        array2.add(1,"dhamu");
//        array2.add(2,"dhamu");
//        array2.add(3,"dhamu");
//        array2.add(4,"dhamu");
        //ad.setDate1(DT.startD);
        array2 = getIntent().getStringArrayListExtra("MASS");





            //array2=getIntent().getStringArrayListExtra("MY_LIST");

            // StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3,1);
       //RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//       adapters.add(new Adapter("dhamu","hi"));
//       adapters.add(new Adapter("Jeeva","hi"));
//       adapters.add(new Adapter("Naveen","hi"));


//          while(iterator.hasNext()){
//               adapters.add(new Adapter(iterator.next()));
////
//                }
        //fragmentManager.beginTransaction().replace(R.id.frame_container,new newsfeed()).commit();

        //fragmentManager.beginTransaction().replace(R.id.frame_container,new share()).commit();
        fragmentManager.beginTransaction().replace(R.id.frame_container,new myact()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
//            AlertDialog.Builder alert=new AlertDialog.Builder(this);
//            alert.setMessage("Do you want to Exit");
//            alert.setPositiveButton("Yes",)
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.my_act) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_CONTACTS))
            {

                Toast.makeText(this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

            } else {

                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

            }
            fragmentManager.beginTransaction().replace(R.id.frame_container,new myact()).commit();
            toolbar.setTitle("My Activity");


        }
        else if (id == R.id.share) {
            fragmentManager.beginTransaction().replace(R.id.frame_container,new contacts()).commit();
            //fab.setEnabled(false);
            toolbar.setTitle("Share With Friends");


      } else if (id == R.id.newsfeed) {
            fragmentManager.beginTransaction().replace(R.id.frame_container,new newsfeed()).commit();
            toolbar.setTitle("NewsFeed");
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {

        }
        else if (id == R.id.create) {
            fragmentManager.beginTransaction().replace(R.id.frame_container,new create_event()).commit();
            toolbar.setTitle("Create Event");
        }
        else if(id==R.id.help){
            fragmentManager.beginTransaction().replace(R.id.frame_container,new help()).commit();
            toolbar.setTitle("Help");
        }
        else if(id==R.id.scanQr)
        {
            fragmentManager.beginTransaction().replace(R.id.frame_container,new scanqr()).commit();
            toolbar.setTitle("Scan Qr");
        }
        else if(id==R.id.logout){
            sharedPref=getSharedPreferences("data",MODE_PRIVATE);
            sharedit=sharedPref.edit();
             sharedit.clear();
            sharedit.commit();
             Intent intent=new Intent(MainActivity.this,firstLogin.class);
            startActivity(intent);
            finish();
             }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
