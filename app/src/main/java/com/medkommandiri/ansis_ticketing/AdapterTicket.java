package com.medkommandiri.ansis_ticketing;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTicket extends RecyclerView.Adapter<AdapterTicket.ViewHolder> {
    ArrayList<PojoTicket> data;
    PojoTicket pojoTicket;
    Context context;


    public AdapterTicket(ArrayList<PojoTicket> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PojoTicket pojo = data.get(position);

        holder.tl_realname.setText(pojo.getRealname());
        holder.tl_title.setText(pojo.getTitle());
        holder.tl_desc.setText(pojo.getDescription());
        holder.tl_date.setText(pojo.getDate());
        holder.tl_time.setText(pojo.getTime());
        String status = pojo.getStatus();
        Log.d("cek status", "onBindViewHolder: " + status);

        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailTicket = new Intent(context, DetailTicketActivity.class);

                detailTicket.putExtra("idTicket", pojo.getId_ticket());
                detailTicket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detailTicket);
                Toast.makeText(context, "Muncullah Kau iblis" + pojo.getId_ticket(), Toast.LENGTH_SHORT).show();
            }
        });
        if (status.contains("open")) {
            holder.iv_tlStatus.setBackground(holder.itemView.getResources().getDrawable(R.drawable.open));
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tl_title, tl_realname, tl_desc, tl_date, tl_time;
        ImageView iv_tlStatus;
        RelativeLayout viewBackground;
        LinearLayout viewForeground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tl_title = itemView.findViewById(R.id.tl_title);
            tl_realname = itemView.findViewById(R.id.tl_realname);
            tl_desc = itemView.findViewById(R.id.tl_desc);
            tl_date = itemView.findViewById(R.id.tl_date);
            tl_time = itemView.findViewById(R.id.tl_time);
            iv_tlStatus = itemView.findViewById(R.id.tl_ivStatus);
            viewBackground = itemView.findViewById(R.id.viewBackground);
            viewForeground = itemView.findViewById(R.id.viewForeground);
        }
    }

    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }
}
