package com.example.projekt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt.R;
import com.example.projekt.models.Projekt;

import java.util.List;

public class ProjektsAdapter extends RecyclerView.Adapter<ProjektsAdapter.ViewHolder> {
    private Context context;
    private List<Projekt> projekts;

    public ProjektsAdapter(Context context, List<Projekt> projekts){
        this.context = context;
        this.projekts = projekts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_projekt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjektsAdapter.ViewHolder holder, int position) {
        Projekt projekt = projekts.get(position);
        holder.bind(projekt);
    }

    @Override
    public int getItemCount() {
        return projekts.size();
    }

    public void clear() {
        projekts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Projekt> list) {

        projekts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvProjektName;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvProjektName = itemView.findViewById(R.id.tvProjektName);

        }

        public void bind(Projekt projekt){
            tvProjektName.setText(projekt.getName());
        }

    }

}
