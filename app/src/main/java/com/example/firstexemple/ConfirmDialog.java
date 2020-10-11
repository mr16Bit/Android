package com.example.firstexemple;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfirmDialog extends DialogFragment {

    ConfirmDialogListeners listeners;

    public void setDialogLiseteners(ConfirmDialogListeners liseteners){
        this.listeners = liseteners;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.remove))
                .setMessage(getString(R.string.remove_confirm))
                .setPositiveButton(R.string.confirm_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listeners != null) listeners.onPositiveClick();
                    }
                })
                .setNegativeButton(R.string.confirm_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listeners != null) listeners.onNegativeClick();
                    }
                })
                .create();
    }
}
