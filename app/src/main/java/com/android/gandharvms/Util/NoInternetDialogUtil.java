package com.android.gandharvms.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.gandharvms.R;

public class NoInternetDialogUtil {
    private AlertDialog noInternetDialog;
    private CountDownTimer countDownTimer;
    public void showDialog(Activity activity) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return; // Don't show dialog if activity is invalid
        }
        if (noInternetDialog != null && noInternetDialog.isShowing()) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_no_internet, null);

        TextView tvMessage = dialogView.findViewById(R.id.tv_message);
        TextView tvTimer = dialogView.findViewById(R.id.tv_timer);
        Button btnRetry = dialogView.findViewById(R.id.btn_retry);
        Button btnExit = dialogView.findViewById(R.id.btn_exit);

        builder.setView(dialogView);
        builder.setCancelable(false);
        noInternetDialog = builder.create();
        if (noInternetDialog.getWindow() != null) {
            noInternetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlideAnimation;
        }
        noInternetDialog.show();

        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText((millisUntilFinished / 1000) + "s");
                btnRetry.setVisibility(View.GONE);
                btnExit.setVisibility(View.GONE);
            }

            public void onFinish() {
                tvMessage.setText("Still no connection.\nPlease check your network.");
                tvTimer.setText("00s");
                btnRetry.setVisibility(View.VISIBLE);
                btnExit.setVisibility(View.VISIBLE);
            }
        }.start();

        btnRetry.setOnClickListener(v -> {
            tvMessage.setText("Still no connection.\nTrying again...");
            btnRetry.setVisibility(View.GONE);
            btnExit.setVisibility(View.GONE);
            restartTimer(tvTimer, tvMessage, btnRetry, btnExit, activity);
        });

        btnExit.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setTitle("Exit App?")
                    .setMessage("Are you sure you want to exit the application?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        activity.finishAffinity(); // Exits the app
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        dialog.dismiss();
                        showDialog(activity); // Reshow main alert
                    })
                    .show();
        });
    }

    private void restartTimer(TextView tvTimer, TextView tvMessage, Button btnRetry, Button btnExit, Activity activity) {
        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText((millisUntilFinished / 1000) + "s");
            }

            public void onFinish() {
                tvMessage.setText("Still no connection.\nPlease check your network.");
                tvTimer.setText("00s");
                btnRetry.setVisibility(View.VISIBLE);
                btnExit.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void dismissDialog(Activity activity) {
        if (countDownTimer != null) countDownTimer.cancel();
        if (noInternetDialog != null && noInternetDialog.isShowing()) {
            noInternetDialog.dismiss();
        }
    }
}
