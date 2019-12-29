package com.dam.t05p02_ivan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dam.t05p02_ivan.modelo.Alumno;
import com.dam.t05p02_ivan.vista.dialogos.ConfirmDialog;
import com.dam.t05p02_ivan.vista.dialogos.FechaDialog;
import com.dam.t05p02_ivan.vista.ui.alta.AltaAlumnoFragment;
import com.dam.t05p02_ivan.vista.ui.baja.BajaAlumnoFragment;
import com.dam.t05p02_ivan.vista.ui.listado.ListadoAlumnosFragment;
import com.dam.t05p02_ivan.vistamodelo.Datos;
import com.dam.t05p02_ivan.vistamodelo.LogicaAlumno;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity implements ConfirmDialog.ExitDialogListener,
        AltaAlumnoFragment.AltaAlumnoListener,
        BajaAlumnoFragment.BajaAlumnoListener,
        ListadoAlumnosFragment.ListadoAlumnosListener,
        FechaDialog.FechaDialogListener {

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    private FragmentTransaction fragmentTransaction;

    //Para facilitar el manejo del BottomNavigationView
    private int posAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);

        mostrarFragmentoListadoAlumnos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuItemSalir) {
            DialogFragment exitDialog = new ConfirmDialog(getString(R.string.app_name),getString(R.string.salirDialog_msg));
            exitDialog.show(getSupportFragmentManager(), "exitDialog");
        }

        return super.onOptionsItemSelected(item);
    }

    public NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.nav_listado_alumnos:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, ListadoAlumnosFragment.newInstance(null), ListadoAlumnosFragment.TAG_LISTADO_ALUMNOS_FRAGMENT);
                    fragmentTransaction.commit();
                    break;
                case R.id.nav_alta_alumno:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, AltaAlumnoFragment.newInstance(null), AltaAlumnoFragment.TAG_ALTA_ALUMNO_FRAGMENT);
                    fragmentTransaction.commit();
                    break;
                case R.id.nav_baja_alumno:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, BajaAlumnoFragment.newInstance(null), BajaAlumnoFragment.TAG_BAJA_ALUMNO_FRAGMENT);
                    fragmentTransaction.commit();
                    break;
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    };


    @Override
    public void onExitDialogPositiveClick(DialogFragment dialogFragment) {


        switch (dialogFragment.getTag().toString()){
            case "exitDialog":
                finish();
                break;
            case "eliminarAlumno":
                Alumno alumno = Datos.getInstance().getmAlumnos().get(posAlumno);
                if(LogicaAlumno.bajaAlumno(alumno)){
                    if(getSupportFragmentManager().getFragments().size() > 0) {
                        Fragment fragmentActual = getSupportFragmentManager().getFragments().get(0);
                        ((ListadoAlumnosFragment)fragmentActual).actualizarRVEliminar(posAlumno);
                    }
                    Snackbar.make(findViewById(R.id.clMain), R.string.msg_BajaAlumnoOK, Snackbar.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(findViewById(R.id.clMain), R.string.msg_BajaAlumnoKO, Snackbar.LENGTH_SHORT).show();
                }
                posAlumno = -1;
                break;
        }
    }

    @Override
    public void onExitDialogNegativeClick(DialogFragment dialogFragment) {
        ;
    }

    private void mostrarFragmentoListadoAlumnos() {
        //Esconder teclado
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, ListadoAlumnosFragment.newInstance(null), ListadoAlumnosFragment.TAG_LISTADO_ALUMNOS_FRAGMENT);
        fragmentTransaction.commit();
    }

    @Override
    public void onAltaBtCancelar() {
        mostrarFragmentoListadoAlumnos();
    }

    @Override
    public void onAltaBtAceptarNuevoAlumno(Alumno alumno) {
        //Esconder teclado
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if (faltanDatosObligatoriosAlta(alumno)) {
            Snackbar.make(findViewById(R.id.clMain), R.string.msg_FaltanDatosObligatorios, Snackbar.LENGTH_SHORT).show();
        } else {
            if (LogicaAlumno.altaAlumno(alumno)) {
                Snackbar.make(findViewById(R.id.clMain), R.string.msg_AltaAlumnoOK, Snackbar.LENGTH_SHORT).show();
                mostrarFragmentoListadoAlumnos();
            } else {
                Snackbar.make(findViewById(R.id.clMain), R.string.msg_AltaAlumnoKO, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAltaBtAceptarEditarAlumno(Alumno alumno, int posicionAlumno) {
        //Esconder teclado
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if (faltanDatosObligatoriosAlta(alumno)) {
            Snackbar.make(findViewById(R.id.clMain), R.string.msg_FaltanDatosObligatorios, Snackbar.LENGTH_SHORT).show();
        } else {
            if (LogicaAlumno.editarAlumno(alumno, posicionAlumno)) {
                Snackbar.make(findViewById(R.id.clMain), R.string.msg_EditarAlumnoOK, Snackbar.LENGTH_SHORT).show();
                mostrarFragmentoListadoAlumnos();
            } else {
                Snackbar.make(findViewById(R.id.clMain), R.string.msg_EditarAlumnoKO, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFechaDialogClick(String fechaFormateada) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment.getTag().toString().equals(AltaAlumnoFragment.TAG_ALTA_ALUMNO_FRAGMENT)) {
                ((AltaAlumnoFragment) fragment).asignarFecha(fechaFormateada);
            }
        }
    }

    @Override
    public void onBajaBtCancelar() {
        mostrarFragmentoListadoAlumnos();
    }

    @Override
    public void onBajaBtAceptar(Alumno alumno) {
        //Esconder teclado
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if (faltanDatosObligatoriosBaja(alumno)) {
            Snackbar.make(findViewById(R.id.clMain), R.string.msg_FaltanDatosObligatorios, Snackbar.LENGTH_SHORT).show();
        } else {
            if (LogicaAlumno.bajaAlumno(alumno)) {
                Snackbar.make(findViewById(R.id.clMain), R.string.msg_BajaAlumnoOK, Snackbar.LENGTH_SHORT).show();
                mostrarFragmentoListadoAlumnos();
            } else {
                Snackbar.make(findViewById(R.id.clMain), R.string.msg_BajaAlumnoKO, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onListadoBtEditar(int posicionAlumno) {

        Alumno alumno;

        if (posicionAlumno > -1) {
            alumno = Datos.getInstance().getmAlumnos().get(posicionAlumno);

            fragmentTransaction = getSupportFragmentManager().beginTransaction();

            Bundle bundle = new Bundle();
            bundle.putParcelable("alumno", alumno);
            bundle.putInt("posicionAlumno", posicionAlumno);

            fragmentTransaction.replace(R.id.frameLayout, AltaAlumnoFragment.newInstance(bundle), AltaAlumnoFragment.TAG_ALTA_ALUMNO_FRAGMENT);
            fragmentTransaction.commit();
        } else {
            Snackbar.make(findViewById(R.id.clMain), R.string.msg_NingunAlumnoSeleccionado, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListadoBtEliminar(int posicionAlumno) {

        if (posicionAlumno > -1) {
            posAlumno = posicionAlumno;
            DialogFragment eliminarAlumno = new ConfirmDialog(getString(R.string.app_name),getString(R.string.msg_BorrarConfirmacion));
            eliminarAlumno.show(getSupportFragmentManager(), "eliminarAlumno");
        } else {
            Snackbar.make(findViewById(R.id.clMain), R.string.msg_NingunAlumnoSeleccionado, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListadoBtFoto(int posicionAlumno) {

        if (posicionAlumno > -1) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                posAlumno = posicionAlumno;
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            Snackbar.make(findViewById(R.id.clMain), R.string.msg_NingunAlumnoSeleccionado, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ListadoAlumnosFragment.CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    Alumno alumnoTmp = Datos.getInstance().getmAlumnos().get(posAlumno);
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    alumnoTmp.setFoto(imageBitmap);
                    posAlumno = -1;
                }
                mostrarFragmentoListadoAlumnos();
                break;
        }
    }

    private boolean faltanDatosObligatoriosAlta(Alumno alumno) {
        if (alumno.getDni().equals("") || alumno.getNombre().equals("")) {
            return true;
        }
        return false;
    }

    private boolean faltanDatosObligatoriosBaja(Alumno alumno) {
        if (alumno.getDni().equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if(fragment.getTag().toString().equals(ListadoAlumnosFragment.TAG_LISTADO_ALUMNOS_FRAGMENT)){
                    DialogFragment exitDialog = new ConfirmDialog(getString(R.string.app_name),getString(R.string.salirDialog_msg));
                    exitDialog.show(getSupportFragmentManager(), "exitDialog");
                }else{
                    mostrarFragmentoListadoAlumnos();
                }
            }
        }
    }
}
