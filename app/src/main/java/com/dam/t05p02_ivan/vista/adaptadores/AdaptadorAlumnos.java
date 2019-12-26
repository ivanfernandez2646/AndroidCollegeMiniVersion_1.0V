package com.dam.t05p02_ivan.vista.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.t05p02_ivan.R;
import com.dam.t05p02_ivan.modelo.Alumno;

import java.util.List;

public class AdaptadorAlumnos extends RecyclerView.Adapter<AdaptadorAlumnos.VHAlumno> {

    private List<Alumno> mAlumnos;
    private int mItemPos;
    private Context context;

    public AdaptadorAlumnos(List<Alumno> mAlumnos,Context context) {
        this.mAlumnos = mAlumnos;
        this.context = context;
        mItemPos = -1;
    }

    public int getmItemPos() {
        return mItemPos;
    }

    public void setmItemPos(int mItemPos) {
        this.mItemPos = mItemPos;
    }

    @NonNull
    @Override
    public VHAlumno onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_rv_alumnos, parent, false);

        VHAlumno vhAlumno = new VHAlumno(v);
        return vhAlumno;
    }

    @Override
    public void onBindViewHolder(@NonNull VHAlumno holder, int position) {
        holder.setItem(mAlumnos.get(position));
        holder.itemView.setBackgroundColor((position == mItemPos) ? Color.YELLOW : Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return mAlumnos.size();
    }

    class VHAlumno extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvDniRes, tvNombreRes, tvFechaNacRes, tvCicloRes;
        private ImageView ivFoto;

        public VHAlumno(@NonNull View itemView) {
            super(itemView);
            tvDniRes = itemView.findViewById(R.id.tvDniRes);
            tvNombreRes = itemView.findViewById(R.id.tvNombreRes);
            tvFechaNacRes = itemView.findViewById(R.id.tvFechaNacRes);
            tvCicloRes = itemView.findViewById(R.id.tvCicloRes);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            itemView.setOnClickListener(this);
        }

        public void setItem(Alumno alumno) {
            tvDniRes.setText(alumno.getDni());
            tvNombreRes.setText(alumno.getNombre());
            tvFechaNacRes.setText(alumno.getFechaNac());

            String[] ciclos = context.getResources().getStringArray(R.array.ciclos_array);
            for (int i = 0; i < ciclos.length; i++) {
                if(alumno.getCiclo() == i){
                    tvCicloRes.setText(ciclos[i]);
                }
            }

            if(alumno.getFoto() != null){
                ivFoto.setImageBitmap(alumno.getFoto());
            }
        }

        @Override
        public void onClick(View v) {
            mItemPos = getLayoutPosition();
            notifyDataSetChanged();
        }
    }
}
