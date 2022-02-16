package com.example.tp1_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.MyViewHolder> {

    private ArrayList<Stats> lstStats;

    public StatsAdapter(ArrayList<Stats> pLstStats) {
        this.lstStats = pLstStats;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvScore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvLstItemDate);
            tvScore = (TextView) itemView.findViewById(R.id.tvLstItemsScore);
        }
    }

    @NonNull
    @Override
    public StatsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsAdapter.MyViewHolder holder, int position) {
        String date =lstStats.get(position).GetDate();
        int score = lstStats.get(position).GetScore();

        holder.tvDate.setText(String.format("%s", date));
        holder.tvScore.setText(String.format("%s", score));
    }

    @Override
    public int getItemCount() {
        return lstStats.size();
    }
}
