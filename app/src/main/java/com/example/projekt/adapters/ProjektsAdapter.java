package com.example.projekt.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt.AddProjektActivity;
import com.example.projekt.MainActivity;
import com.example.projekt.ProjektActivity;
import com.example.projekt.R;
import com.example.projekt.models.Projekt;

import org.parceler.Parcels;

import java.util.List;

public class ProjektsAdapter extends RecyclerView.Adapter<ProjektsAdapter.ViewHolder> {
    private Context context;
    private List<Projekt> projekts;

    public ProjektsAdapter(Context context, List<Projekt> projekts) {
        this.context = context;
        this.projekts = projekts;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == projekts.size()) ? R.layout.item_add_projekt : R.layout.item_projekt;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == R.layout.item_projekt) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_projekt, parent, false);

        }
        //If last in list we want to add this button.
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_projekt, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjektsAdapter.ViewHolder holder, int position) {
        if (position == projekts.size()) {
            holder.tvAddProjekt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, AddProjektActivity.class);
                    context.startActivity(i);


                }
            });
        } else {
            Projekt projekt = projekts.get(position);
            holder.bind(projekt);
        }
    }

    @Override
    public int getItemCount() {
        return projekts.size() + 1;
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

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProjektName;
        private TextView tvAddProjekt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProjektName = itemView.findViewById(R.id.tvProjektName);
            tvAddProjekt = itemView.findViewById(R.id.tvAddProjekt);

        }

        public void bind(Projekt projekt) {
            tvProjektName.setText(projekt.getName());
            tvProjektName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ProjektActivity.class);
                    i.putExtra("DETAILS", Parcels.wrap(projekt));
                    context.startActivity(i);

                }
            });
        }

    }


}
