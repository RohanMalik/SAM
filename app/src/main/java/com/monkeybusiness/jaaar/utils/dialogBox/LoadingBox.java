package com.monkeybusiness.jaaar.utils.dialogBox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.widget.TextView;

import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.utils.Log;


public class LoadingBox {
    private static ProgressDialog progressDialog;

    /**
     * @param context
     * @param message
     */
    public static void showLoadingDialog(Activity context, String message) {
        try {
            if (message.isEmpty())
                message = "Loading...";

            if (isDialogShowing()) {
                dismissLoadingDialog();
            }
            if (context.isFinishing()) {
                return;
            }

            progressDialog = new ProgressDialog(context, R.style.Theme_AppCompat_Translucent);

            progressDialog.show();
            WindowManager.LayoutParams layoutParams = progressDialog.getWindow().getAttributes();
            layoutParams.dimAmount = 0.7f;
            progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.loading_box);

            ((ProgressWheel) progressDialog.findViewById(R.id.progress_wheel)).spin();
            TextView messageText = (TextView) progressDialog.findViewById(R.id.tvProgress);
            messageText.setText(message);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * Check if loading box showing or not
     *
     * @return
     */
    public static boolean isDialogShowing() {
        try {
            if (progressDialog == null) {
                return false;
            } else {
                return progressDialog.isShowing();
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dismisses above loading dialog
     */
    public static void dismissLoadingDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            Log.e("e", "=" + e);
        }
    }

}