package com.example.kalkulatoruas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.ViewHolder> {

    private ArrayList<Riwayat> listRiwayat;
    private Context context;
    private SharedPreferences sharedPreferences;

    public RiwayatAdapter(ArrayList<Riwayat> listRiwayat, Context context, SharedPreferences sharedPreferences) {
        this.listRiwayat = listRiwayat;
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.riwayat_layout, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Riwayat riwayat = listRiwayat.get(position);
        holder.riwayat.setText(riwayat.getRiwayat());
    }

    @Override
    public int getItemCount() {
        return listRiwayat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView riwayat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            riwayat = itemView.findViewById(R.id.txRwyt);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int p = getLayoutPosition();

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Hapus Riwayat")
                            .setMessage("Ingin hapus riwayat ?")
                            .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String id = listRiwayat.get(p).getId();

                                    sharedPreferences.edit().remove(id).commit();

                                    for (int j = 0; j < listRiwayat.size(); j++) {
                                        if (id.equalsIgnoreCase(listRiwayat.get(j).getId())){
                                            listRiwayat.remove(j);
                                            notifyItemRemoved(j);
                                            notifyItemRangeChanged(j, listRiwayat.size());
                                        }
                                    }
                                }
                            });
                    AlertDialog dialog = alert.create();
                    dialog.show();

                    return true;
                }
            });
        }
    }
}
