package com.dam.t05p02_ivan.vista.ui.alta;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.dam.t05p02_ivan.R;
import com.dam.t05p02_ivan.modelo.Alumno;
import com.dam.t05p02_ivan.vista.dialogos.FechaDialog;

public class AltaAlumnoFragment extends Fragment {

    public static final String TAG_ALTA_ALUMNO_FRAGMENT = "TAG_AltaAlumnoFragment";

    private AltaAlumnoListener mListener;

    public interface AltaAlumnoListener {
        void onAltaBtCancelar();
        void onAltaBtAceptarNuevoAlumno(Alumno alumno);
        void onAltaBtAceptarEditarAlumno(Alumno alumno,int posicionAlumno);
    }

    private EditText etDni, etNombre, etFechaNac;
    private Button btFechaNac, btCancelar, btAceptar;
    private Spinner spCiclo;

    private boolean modoEdicion;
    private int posicionAlumno;

    public AltaAlumnoFragment() {

    }

    public static AltaAlumnoFragment newInstance(Bundle arguments) {

        AltaAlumnoFragment altaAlumnoFragment = new AltaAlumnoFragment();
        if (arguments != null) {
            altaAlumnoFragment.setArguments(arguments);
        }

        return altaAlumnoFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AltaAlumnoListener) {
            mListener = (AltaAlumnoListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AltaAlumnoListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_alta_alumno, container, false);

        etDni = v.findViewById(R.id.etDni);
        etNombre = v.findViewById(R.id.etNombre);
        etFechaNac = v.findViewById(R.id.etFechaNac);
        btFechaNac = v.findViewById(R.id.btFechaNac);
        btCancelar = v.findViewById(R.id.btCancelar);
        btAceptar = v.findViewById(R.id.btAceptar);
        spCiclo = v.findViewById(R.id.spCiclo);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            //Inits
            posicionAlumno = -1;
            ArrayAdapter<CharSequence> spAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.ciclos_array, android.R.layout.simple_spinner_dropdown_item);
            spCiclo.setAdapter(spAdapter);

            //Listeners
            btFechaNac.setOnClickListener(btFuncionales_OnClickListener);
            btCancelar.setOnClickListener(btFuncionales_OnClickListener);
            btAceptar.setOnClickListener(btFuncionales_OnClickListener);

            if(getArguments() != null){

                Alumno alumnoTMP = getArguments().getParcelable("alumno");
                etDni.setText(alumnoTMP.getDni());
                etDni.setEnabled(false);
                etNombre.setText(alumnoTMP.getNombre());
                etFechaNac.setText(alumnoTMP.getFechaNac());
                spCiclo.setSelection(alumnoTMP.getCiclo());
                modoEdicion = true;
                posicionAlumno = getArguments().getInt("posicionAlumno");
            }
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
            if (v.getId() == btFechaNac.getId()) {
                DialogFragment fechaDialog = new FechaDialog();
                fechaDialog.show(getChildFragmentManager(), "fechaDialog");
            } else if (v.getId() == btCancelar.getId()) {
                mListener.onAltaBtCancelar();
            } else if (v.getId() == btAceptar.getId()) {

                if(modoEdicion){
                    Alumno alumno = new Alumno();
                    alumno.setDni(etDni.getText().toString());
                    alumno.setNombre(etNombre.getText().toString());
                    alumno.setFechaNac(etFechaNac.getText().toString());
                    alumno.setCiclo(spCiclo.getSelectedItemPosition());

                    mListener.onAltaBtAceptarEditarAlumno(alumno,posicionAlumno);
                }else{
                    Alumno alumno = new Alumno();
                    alumno.setDni(etDni.getText().toString());
                    alumno.setNombre(etNombre.getText().toString());
                    alumno.setFechaNac(etFechaNac.getText().toString());
                    alumno.setCiclo(spCiclo.getSelectedItemPosition());

                    mListener.onAltaBtAceptarNuevoAlumno(alumno);
                }
            }
        }
    };

    public void asignarFecha(String fechaFormateada) {
        etFechaNac.setText(fechaFormateada);
    }
}