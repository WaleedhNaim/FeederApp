package com.example.nb_interview.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nb_interview.R;

import de.mateware.snacky.Snacky;

public class Utils {

    public static Dialog getLoadingDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }


    public static void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public static boolean validateField(EditText editText, String msg) {
        if (editText.getText().toString().length() == 0) {
            editText.setError(msg);
            editText.requestFocus();

            return false;
        }
        return true;
    }

    public static boolean isNotEmptyString(String value) {
        if (value != null && !value.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

    public static void showAlert(Activity activity,String message,AlertType alertType){

            // Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            switch (alertType) {

                case SUCCESS:
                    Snacky.builder()
                            .setActivity(activity)
                            .setText(message)
                            .setDuration(3000)
                            .success()
                            .show();
               break;

                case ERROR:
               Snacky.builder()
                       .setActivity(activity)
                       .setText(message)
                       .setDuration(3000)
                       .error()
                       .show();

                    break;

                case WARNING:
                    Snacky.builder()
                            .setActivity(activity)
                            .setText(message)
                            .setDuration(3000)
                            .warning()
                            .show();

                    break;
                case TOAST:
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }

    }
}
