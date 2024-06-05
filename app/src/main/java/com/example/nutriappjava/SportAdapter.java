package com.example.nutriappjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutriappjava.classes.sportItems;

import java.util.ArrayList;
import java.util.List;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.ViewHolder> {

    private List<sportItems> activityItems;
    private Context context;

    public SportAdapter(Context context) {
        this.activityItems = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sport_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sportItems activity = activityItems.get(position);
        holder.nameTextView.setText(activity.getName());
    }

    @Override
    public int getItemCount() {
        return activityItems.size();
    }

    public void updateData(List<sportItems> newData) {
        activityItems.clear();
        activityItems.addAll(newData);
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}