package com.example.timotej.whatdo;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Timotej on 17. 03. 2017.
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Activity ac;
    static MyActionList seznam;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public TextView txtDate;
        public TextView txtTime;
        public ConstraintLayout block;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
            txtDate = (TextView) itemView.findViewById(R.id.date);
            txtTime = (TextView) itemView.findViewById(R.id.twTime);
            block = (ConstraintLayout) itemView.findViewById(R.id.block);
        }
    }

    public MyAdapter(Activity ac, MyActionList seznam) {
        this.ac = ac;
        this.seznam = seznam;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    private static void startDView(String lokacijaID, Activity ac) { // <--------------Tu si ustau
        //  System.out.println(name+":"+position);
        Intent i = new Intent(ac.getBaseContext(), MainActivity.class);
        //i.putExtra(MyActionList.LOKACIJA_ID,lokacijaID);
        i.putExtra("neki",lokacijaID);
        System.out.println("Lokacija ID: "+lokacijaID);
        //i.putExtra(seznam.activities,  lokacijaID);
        ac.startActivity(i);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyAction trenutni = seznam.activities.get(position);
        final String title = trenutni.getName();

        holder.txtTitle.setText(title);
        holder.txtDate.setText("Date: "+ MyMain.getStringDate(trenutni.getStartDate() ));
        holder.txtTime.setText(trenutni.getStartTime());

        holder.block.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //odpri klikjen action
                MyAdapter.startDView(trenutni.getIdAction(),ac);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seznam.activities.size();
    }
}
