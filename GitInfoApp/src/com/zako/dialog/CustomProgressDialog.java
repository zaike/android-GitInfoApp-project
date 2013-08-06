package com.zako.dialog;

import com.example.gitinfoapp.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;


public class CustomProgressDialog extends Dialog {

	TextView textMess;

	public CustomProgressDialog(Context context, String mess) {
		super(context, R.style.Theme_CustomProgressDialog);
		
		setContentView(R.layout.custom_progress_dialog);
		
		findViews();

		this.textMess.setText(mess);
	}

	protected void findViews() {
		this.textMess = (TextView)findViewById(R.id.progressDialogMessage);
	}
	
}
