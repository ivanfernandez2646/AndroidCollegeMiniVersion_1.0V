package com.dam.t05p02_ivan.vista.ui.baja;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dam.t05p02_ivan.R;
import com.dam.t05p02_ivan.modelo.Alumno;
import com.dam.t05p02_ivan.vista.ui.alta.AltaAlumnoFragment;

public class BajaAlumnoFragment extends Fragment {

    public static final String TAG_BAJA_ALUMNO_FRAGMENT = "TAG_BajaAlumnoFragment";

    private BajaAlumnoListener mListener;

    public interface BajaAlumnoListener {
        void onBajaBtCancelar();

        void onBajaBtAceptar(Alumno alumno);
    }

    private EditText etDni;
    private Button btCancelar, btAceptar;

    public BajaAlumnoFragment() {

    }

    public static BajaAlumnoFragment newInstance(Bundle arguments) {

        BajaAlumnoFragment bajaAlumnoFragment = new BajaAlumnoFragment();
        if (arguments != null) {
            bajaAlumnoFragment.setArguments(arguments);
        }

        return bajaAlumnoFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BajaAlumnoListener) {
            mListener = (BajaAlumnoListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BajaAlumnoListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_baja_alumno, container, false);

        etDni = v.findViewById(R.id.etDni);
        btCancelar = v.findViewById(R.id.btCancelar);
        btAceptar = v.findViewById(R.id.btAceptar);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            //Inits

            //Listeners
            btCancelar.setOnClickListener(btFuncionales_OnClickListener);
            btAceptar.setOnClickListener(btFuncionales_OnClickListener);
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

    private View.OnClickListener btFuncionales_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == btCancelar.getId()) {
                mListener.onBajaBtCancelar();
            } else if (v.getId() == btAceptar.getId()) {
                Alumno alumno = new Alumno();
                alumno.setDni(etDni.getText().toString());
                mListener.onBajaBtAceptar(alumno);
            }
        }
    };
}