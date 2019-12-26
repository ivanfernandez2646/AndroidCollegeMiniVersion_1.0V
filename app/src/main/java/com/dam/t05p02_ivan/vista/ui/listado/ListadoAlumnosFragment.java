package com.dam.t05p02_ivan.vista.ui.listado;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.t05p02_ivan.R;
import com.dam.t05p02_ivan.vista.adaptadores.AdaptadorAlumnos;
import com.dam.t05p02_ivan.vistamodelo.Datos;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ListadoAlumnosFragment extends Fragment {

    public static final String TAG_LISTADO_ALUMNOS_FRAGMENT = "TAG_ListadoAlumnosFragment";
    public static final int CAMERA_REQUEST = 1;

    private ListadoAlumnosListener mListener;

    public interface ListadoAlumnosListener{
        void onListadoBtEditar(int posicionAlumno);
        void onListadoBtEliminar(int posicionAlumno);
        void onListadoBtFoto(int posicionAlumno);
    }

    private RecyclerView rvAlumnos;
    private TextView tvSinAlumnos;
    private AdaptadorAlumnos mAdapter;
    private BottomNavigationView bottomNavigationView;

    public ListadoAlumnosFragment() {

    }

    public static ListadoAlumnosFragment newInstance(Bundle arguments){

        ListadoAlumnosFragment listadoAlumnosFragment = new ListadoAlumnosFragment();
        if(arguments != null){
            listadoAlumnosFragment.setArguments(arguments);
        }

        return listadoAlumnosFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListadoAlumnosListener) {
            mListener = (ListadoAlumnosListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ListadoAlumnosFragment");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_listado_alumnos, container, false);

        rvAlumnos = v.findViewById(R.id.rvAlumnos);
        tvSinAlumnos = v.findViewById(R.id.tvSinAlumnos);
        bottomNavigationView = v.findViewById(R.id.bottomNavigationView);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null){

            //Inits
            rvAlumnos.setHasFixedSize(true);
            rvAlumnos.setLayoutManager(new LinearLayoutManager(getContext()));

            //Listeners
            mAdapter = new AdaptadorAlumnos(Datos.getInstance().getmAlumnos(),getContext());
            rvAlumnos.setAdapter(mAdapter);
            bottomNavigationView.setOnNavigationItemSelectedListener(navigationView_OnSelectedItem);

            comprobarCantidadAlumnos();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationView_OnSelectedItem = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            if(menuItem.getItemId() == R.id.menuItemEditar){
                mListener.onListadoBtEditar(mAdapter.getmItemPos());
            }else if(menuItem.getItemId() == R.id.menuItemEliminar){
                mListener.onListadoBtEliminar(mAdapter.getmItemPos());
            }else if(menuItem.getItemId() == R.id.menuItemFoto){
                mListener.onListadoBtFoto(mAdapter.getmItemPos());
            }
            return true;
        }
    };

    private void comprobarCantidadAlumnos(){
        if(Datos.getInstance().getmAlumnos().size() > 0){
            tvSinAlumnos.setVisibility(View.INVISIBLE);
            mAdapter.notifyDataSetChanged();
        }else{
            tvSinAlumnos.setVisibility(View.VISIBLE);
        }
    }

    public void actualizarRVEliminar(int posicionAlumno){
        mAdapter.notifyItemRemoved(posicionAlumno);
        comprobarCantidadAlumnos();
    }
}