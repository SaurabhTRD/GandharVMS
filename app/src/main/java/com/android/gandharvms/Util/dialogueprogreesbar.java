package com.android.gandharvms.Util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

public class dialogueprogreesbar {
    public static dialogueprogreesbar instance;
    public ProgressDialog progressDialog;

    public dialogueprogreesbar() {}

    public static synchronized dialogueprogreesbar getInstance() {
        if (instance == null) {
            instance = new dialogueprogreesbar();
        }
        return instance;
    }

    public void showProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Submitting data, please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showConfirmationDialog(Context context, Runnable onConfirm) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (onConfirm != null) {
                        onConfirm.run();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
