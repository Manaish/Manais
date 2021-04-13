package com.marketsimplified.afinal;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter <DetailsAdapter.ViewHolder>{
    List<Details> q;
    Context c;
    MyAdapterClick myAdapterClick;

    public DetailsAdapter(List<Details> q, Context c) {
        this.q = q;
        this.c = c;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Details  details = q.get(position);
        holder.tid.setText(details.getSymbolname());
        holder.tnamer.setText(details.getChangeper());
        holder.tmail.setText(details.getChange());
        holder.tltp.setText(details.getLtp());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapterClick.onItemClick(details);

            }
        });
    }

    @Override
    public int getItemCount() {

        return q.size();

    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tid,tnamer,tmail,tltp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tid=itemView.findViewById(R.id.textview1);
            tnamer=itemView.findViewById(R.id.textview2);
            tmail=itemView.findViewById(R.id.textview3);
            tltp=itemView.findViewById(R.id.textview);
        }
    }
    public interface MyAdapterClick
    {
        void onItemClick(Details item);
    }

    public void setMyAdapterClick(MyAdapterClick myAdapterClick) {
        this.myAdapterClick = myAdapterClick;
    }

}
