package com.beanstack.utility.alertDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    public static final int CUSTOM_DLG = 4;

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
    public Dialog getDialog(int dlgtype,String dialogTitle,String dialogContent,
                            boolean headerReq,View view)
    {
        Button okButton = dialogView.findViewById(R.id.dlg_buttonOk);
        Button caButton = dialogView.findViewById(R.id.dlg_buttoncancel);
        okButton.setOnClickListener(new CustomClickListener(this));
        TextView titleText = dialogView.findViewById(R.id.dlg_title);
        RelativeLayout relativeLayout = dialogView.findViewById(R.id.dlg_head);
        titleText.setText(dialogTitle);
        if(dlgtype==MESSAGE_DLG)
           caButton.setOnClickListener(new CustomClickListener(this));
        if(dlgtype==ERROR_DLG)
        {
            relativeLayout.setBackgroundResource(R.color.red);
            caButton.setVisibility(View.INVISIBLE);
            ImageView headimg = dialogView.findViewById(R.id.dlg_head_icon);
            headimg.setBackgroundResource(R.drawable.ic_cross_svg);
        }
        if(dlgtype==CUSTOM_DLG)
        {
            LinearLayout linearLayout = dialogView.findViewById(R.id.componentlayout);
            caButton.setOnClickListener(new CustomClickListener(this));
            linearLayout.addView(view);
        }
        if(!headerReq)
            relativeLayout.setVisibility(View.GONE);
        TextView dlgMsgTxt = dialogView.findViewById(R.id.dlg_msg_txt);
        dlgMsgTxt.setText(dialogContent);
        return this;
    }

    public void showCustomDialog(int dlgtype,String dialogTitle,String dialogContent,
                                  boolean headerReq,View view,View.OnClickListener listener) {
        Button okButton = dialogView.findViewById(R.id.dlg_buttonOk);
        Button caButton = dialogView.findViewById(R.id.dlg_buttoncancel);
        okButton.setOnClickListener(listener);
        caButton.setOnClickListener(listener);
        TextView titleText = dialogView.findViewById(R.id.dlg_title);
        RelativeLayout relativeLayout = dialogView.findViewById(R.id.dlg_head);
        titleText.setText(dialogTitle);
        if(!headerReq)
            relativeLayout.setVisibility(View.GONE);
        dialogView.findViewById(R.id.dlg_msg_txt).setVisibility(View.GONE);
        LinearLayout linearLayout = dialogView.findViewById(R.id.componentlayout);
        linearLayout.addView(view);
    }

    @Override
    public void onClick(View v) {

    }
    class CustomClickListener implements View.OnClickListener {
        private Dialog dialog;
        public CustomClickListener(Dialog dialog) {
            this.dialog= dialog;
        }
        @Override
        public void onClick(View v) {
            dialog.dismiss();
            if(null != navigationService)
                navigationService.buttonAction(((Button)v).getText().toString());
        }
    }
}
