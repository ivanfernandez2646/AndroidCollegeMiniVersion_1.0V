package com.dam.t05p02_ivan.vista.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.dam.t05p02_ivan.R;

public class ConfirmDialog extends DialogFragment {

    private ExitDialogListener listener;
    private String title;
    private String message;

    public ConfirmDialog(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public interface ExitDialogListener {
        void onExitDialogPositiveClick(DialogFragment dialogFragment);
        void onExitDialogNegativeClick(DialogFragment dialogFragment);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.bt_OK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onExitDialogPositiveClick(ConfirmDialog.this);
                    }
                })
                .setNegativeButton(R.string.bt_KO, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onExitDialogNegativeClick(ConfirmDialog.this);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExitDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
