package com.medkommandiri.ansis_ticketing;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterDetailTicket extends RecyclerView.Adapter<AdapterDetailTicket.ViewHolder> {
    ArrayList<PojoDetailTicket> data = new ArrayList<>();
    Context context;

    public AdapterDetailTicket(ArrayList<PojoDetailTicket> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_ticket_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PojoDetailTicket pojo = data.get(position);
        if (position % 2 == 0) {
            holder.background.setGravity(Gravity.RIGHT);
            holder.foreground.setGravity(Gravity.RIGHT);
        }
        holder.dt_tv_realname.setText(pojo.getRealname());
        holder.dt_tv_datetime.setText(pojo.getDatetime());
        holder.dt_tv_description.setText(pojo.getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dt_tv_realname, dt_tv_datetime, dt_tv_description;
        RelativeLayout background;
        LinearLayout foreground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dt_tv_realname = itemView.findViewById(R.id.dt_tv_realname);
            dt_tv_datetime = itemView.findViewById(R.id.dt_tv_datetime);
            dt_tv_description = itemView.findViewById(R.id.dt_tv_description);
            background = itemView.findViewById(R.id.dt_list_background);
            foreground = itemView.findViewById(R.id.dt_foreground);
        }
    }
}
