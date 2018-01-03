package com.example.hp.mail.recycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.mail.R;
import com.example.hp.mail.adapterclass.Adapter;

import java.util.ArrayList;




/**
 * Created by HP on 6/26/2017.
 */

public  class itemAdapter2 extends  RecyclerView.Adapter<itemAdapter2.ViewHolder> {
    private int listItemLayout;
    private ArrayList<Adapter> itemList;
    public static String str1;
    static Context context;



    //CustomItemClickListener listener;

    // Constructor of the class
    public itemAdapter2(int layoutId, ArrayList<Adapter> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }


    @Override
    public itemAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(view);


        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(itemAdapter2.ViewHolder holder, int position) {
        TextView item = holder.item;
        TextView item1=holder.item1;
        TextView item2=holder.item2;
        TextView item3=holder.item3;
        TextView item4=holder.item4;
        ImageView event=holder.event;
        //ImageView item1=holder.imageView;
        item.setText(itemList.get(position).getName());
        item1.setText(itemList.get(position).getDesp());
        item2.setText(itemList.get(position).getSd());
        item3.setText(itemList.get(position).getEd());
        item4.setText(itemList.get(position).getLocation());
        Glide.with(context).load(itemList.get(position).getUrl()).into(event);
        //item1.setImageResource(itemList.get(position).getName());


    }

    @Override
    public int getItemCount() {

        return itemList == null ? 0 : itemList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item,item1,item2,item3,item4;
        public ImageView event;

        //public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);

            item = (TextView) itemView.findViewById(R.id.eventname);
            item1=(TextView)itemView.findViewById(R.id.textView8);
            item2=(TextView)itemView.findViewById(R.id.startdateC);
            item3=(TextView)itemView.findViewById(R.id.enddateC);
            item4=(TextView)itemView.findViewById(R.id.locationC);
            event=(ImageView) itemView.findViewById(R.id.eventpicture);
            item.setOnClickListener(this);

            context=itemView.getContext();

            //Context context1=itemView.getContext();
            //imageView=(ImageView)itemView.findViewById(R.id.imageView);
        }
        @Override
        public void onClick(View view) {
//            Toast.makeText(, "", Toast.LENGTH_SHORT).show();
            if(view.getId()==R.id.eventname) {
                Toast toast = Toast.makeText(itemView.getContext(), item.getText() + "" + getLayoutPosition(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
//            if(view.getId()==imageView2){
//                Context context=view.getContext();
//
//                Intent it=new Intent(context,QRcode.class);
//                str1=item.getText().toString();
//                Log.d("onclick", "onClick " +str1);
//                it.putExtra("QR",str1);
//                context.startActivity(it);
//
////                Toast toast = Toast.makeText(itemView.getContext(), "IMAGE" + "" + getLayoutPosition(), Toast.LENGTH_SHORT);
////                toast.setGravity(Gravity.CENTER, 0, 0);
////                toast.show();
//
//            }


            //Log.d("onclick", "onClick " + getLayoutPosition() + " " + item1.getId());

        }


    }
}
