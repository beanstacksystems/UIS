package com.beanstack.utility.alertDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanstack.utility.R;
import com.beanstack.utility.service.NavigationService;

public class AppAlertDialog extends Dialog implements View.OnClickListener{

    public final static int BUTTON_CONFIRM = DialogInterface.BUTTON_POSITIVE;
    public final static int BUTTON_CANCEL = DialogInterface.BUTTON_NEGATIVE;

    public static final int MESSAGE_DLG = 1;
    public static final int ERROR_DLG = 0;
    public static final int SUCCESS_DLG = 2;
    public static final int WARNING_DLG = 3;

    private String dlgTitleText;
    private String dlgContentText;
    private String cancelBtnText;
    private String confirmBtnText;
    View dialogView;
    NavigationService navigationService;


    public AppAlertDialog(Context context) {
        super(context);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(context).inflate(R.layout.app_alert_dlg_layout, viewGroup, false);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        this.setContentView(dialogView);
    }
    public AppAlertDialog(Context context,NavigationService navigationService) {
        super(context);
        this.navigationService = navigationService;
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(context).inflate(R.layout.app_alert_dlg_layout, viewGroup, false);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        this.setContentView(dialogView);
    }
    public Dialog getDialog(int dlgtype,String dialogTitle,String dialogContent)
    {
        Button okButton = dialogView.findViewById(R.id.dlg_buttonOk);
        Button caButton = dialogView.findViewById(R.id.dlg_buttoncancel);
        if(dlgtype==ERROR_DLG)
        {
            RelativeLayout relativeLayout = dialogView.findViewById(R.id.dlg_head);
            relativeLayout.setBackgroundResource(R.color.red);
            TextView titleText = dialogView.findViewById(R.id.dlg_title);
            titleText.setText(dialogTitle);
            caButton.setVisibility(View.INVISIBLE);
            okButton.setOnClickListener(new CustomClickListener(this));
            ImageView headimg = dialogView.findViewById(R.id.dlg_head_icon);
            headimg.setBackgroundResource(R.drawable.ic_cross_svg);
        }
        TextView dlgMsgTxt = dialogView.findViewById(R.id.dlg_msg_txt);
        dlgMsgTxt.setText(dialogContent);
        return this;
    }

    private void showCustomDialog(Context context) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {

    }
    class CustomClickListener implements View.OnClickListener {
        private final Dialog dialog;
        public CustomClickListener(Dialog dialog) {
            this.dialog= dialog;
        }
        @Override
        public void onClick(View v) {
            dialog.dismiss();
            if(null != navigationService)
                navigationService.buttonAction();
        }
    }
}
