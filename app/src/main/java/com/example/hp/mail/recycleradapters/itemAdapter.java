package com.example.hp.mail.recycleradapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.mail.QRcode;
import com.example.hp.mail.R;
import com.example.hp.mail.adapterclass.Adapter;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


import static com.example.hp.mail.R.id.qr;
import static com.example.hp.mail.R.id.startingtime;


/**
 * Created by HP on 6/26/2017.
 */

public class itemAdapter extends  RecyclerView.Adapter<itemAdapter.ViewHolder> {
    private int listItemLayout;
    private static ArrayList<Adapter> itemList;
    public static String str1;
    static Context context,context1;
    public static Bitmap bitmap;
    public static StorageReference storageReference;




    //CustomItemClickListener listener;

    // Constructor of the class
    public itemAdapter(int layoutId, ArrayList<Adapter> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }


    @Override
    public itemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(view);


        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(itemAdapter.ViewHolder holder, int position) {
        TextView item = holder.item;
        TextView item1=holder.item1;
        TextView item2=holder.item2;
        TextView item3=holder.item3;
        TextView item4=holder.item4;
        ImageView item5=holder.event;
        //ImageView item1=holder.imageView;
        item.setText(itemList.get(position).getName());
        item1.setText(itemList.get(position).getDesp());
        item2.setText(itemList.get(position).getSd());
        item3.setText(itemList.get(position).getEd());
        item4.setText(itemList.get(position).getLocation());
        Glide.with(context1).load(itemList.get(position).getUrl()).asBitmap().into(item5);

         bitmap=item5.getDrawingCache(true);
          //item1.setImageResource(itemList.get(position).getName());


    }

    @Override
    public int getItemCount() {

        return itemList == null ? 0 : itemList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item,item1,item2,item3,item4;
        public ImageView imageView,share,event;
        //public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            imageView=(ImageView)itemView.findViewById(R.id.qr);
            share=(ImageView)itemView.findViewById(R.id.share);
            event=(ImageView)itemView.findViewById(R.id.eventpic);
            item = (TextView) itemView.findViewById(startingtime);
            item1=(TextView)itemView.findViewById(R.id.textView8);
            item2=(TextView)itemView.findViewById(R.id.startdateC);
            item3=(TextView)itemView.findViewById(R.id.enddateC);
            item4=(TextView)itemView.findViewById(R.id.locationC);

            item.setOnClickListener(this);
            imageView.setOnClickListener(this);
            share.setOnClickListener(this);
            event.setDrawingCacheEnabled(true);
            context1=itemView.getContext();
            storageReference= FirebaseStorage.getInstance().getReference().child("Events");


            //Context context1=itemView.getContext();
            //imageView=(ImageView)itemView.findViewById(R.id.imageView);
        }
        @Override
        public void onClick(View view) {
//            Toast.makeText(, "", Toast.LENGTH_SHORT).show();
           context=view.getContext();
            if(view.getId()== startingtime) {
                Toast toast = Toast.makeText(itemView.getContext(), item.getText() + "" + getLayoutPosition(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            if(view.getId()==qr){


                Intent it=new Intent(context,QRcode.class);
                str1=item.getText().toString();
                Log.d("onclick", "onClick " +str1);
                it.putExtra("QR",str1);
                context.startActivity(it);

//                Toast toast = Toast.makeText(itemView.getContext(), "IMAGE" + "" + getLayoutPosition(), Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();

            }
            if(view.getId()==R.id.share){
                Intent shareIntent = new Intent();


                //Uri uri=Uri.parse(itemList.get(getLayoutPosition()).getUrl().toString());
                String text="Event Name:"+item.getText()+"\n"+"Description:"+item1.getText()+"\n"+"Location:"+item4.getText()+"\n"+"Start Date:"+item2.getText()+"\n End Date: "+item3.getText();
                shareIntent.setAction(Intent.ACTION_SEND);
                //shareIntent.putExtra(Intent.EXTRA_STREAM,uri );
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                 shareIntent.setType("text/plain");
                shareIntent.setType("Image/jpeg");
                ByteArrayOutputStream bytes=new ByteArrayOutputStream();






//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);

                 bitmap= event.getDrawingCache();
                File root=Environment.getExternalStorageDirectory();
                File cache=new File(root.getAbsolutePath()+"/DCIM/Camera/img.jpg");
                try {
                    cache.createNewFile();
                    FileOutputStream fos=new FileOutputStream(cache);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fos);
                    fos.close();



                } catch (IOException e) {
                    e.printStackTrace();
                }
                shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(String.valueOf(cache)));
                context.startActivity(Intent.createChooser(shareIntent, "Share images to.."));
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
//                try {
//                    context.startActivity(new Intent(context, contacts2.class));
//                }
//                catch(Exception e){
//
//                    Toast toast = Toast.makeText(view.getContext(),"Enable permissions in settings", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
                //}
            }



           //Log.d("onclick", "onClick " + getLayoutPosition() + " " + item1.getId());

        }


    }
}
