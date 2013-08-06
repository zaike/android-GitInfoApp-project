package com.example.gitinfoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gitinfoapp.common.Const;
import com.example.gitinfoapp.dto.InfoDto;
import com.example.gitinfoapp.task.DetailTask;
import com.zako.dialog.CustomProgressDialog;

/**
 * �ڍ׉��
 * @author zaike
 */
public class DetailActivity extends Activity implements Handler.Callback {

	EditText editId;

	EditText editTitle;
	
	EditText editDetail;

	EditText editCreateDttm;
	
	EditText editUpdateDttm;

	Button buttonBack;

	InfoDto infoDto;

	private Handler handler;

	private CustomProgressDialog dialogProgress;
	
	private DetailTask taskDetail;
	
	private Thread thread;

	/**
	 * ��������
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		// ��ʃp�[�c�̏�����
		findViews();

		setListener();

		// �J�ڌ��f�[�^�̎󂯎��
		setIntentData();
	}

	/**
	 * ��ʃp�[�c������
	 */
	protected void findViews() {
		this.editId = (EditText)findViewById(R.id.editId);
		this.editTitle = (EditText)findViewById(R.id.editTitle);
		this.editDetail = (EditText)findViewById(R.id.editDetail);
		this.editCreateDttm = (EditText)findViewById(R.id.editCreateDttm);
		this.editUpdateDttm = (EditText)findViewById(R.id.editUpdateDttm);
		this.buttonBack = (Button)findViewById(R.id.buttonBack);
	}

	/**
	 * ��ʃp�[�c�C�x���g���X�i�[�o�^
	 */
	private void setListener() {
		this.buttonBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}

	/**
	 * �J�ڌ���ʏ����p������
	 */
	private void setIntentData() {
		Intent intent = getIntent();

		InfoDto infoDto = (InfoDto)intent.getSerializableExtra("selData");

		this.infoDto = new InfoDto();
		this.infoDto.setInfoId(infoDto.getInfoId());

		doMainProcess(this);
	}

	/**
	 * �ڍ׉�ʎ又��
	 * @param context
	 */
	private void doMainProcess(Context context) {
		// �ڍ׎擾���_�C�A���O�̕\��
		showProgressDialog("�ڍ׎擾���E�E�E");
		
		this.handler = new Handler(this);
		this.taskDetail = new DetailTask(this.handler, context, this.infoDto.getInfoId());
		this.thread = new Thread(this.taskDetail);
		this.thread.start();
	}

	/**
	 * �又���n���h�����O
	 */
	@Override
	public boolean handleMessage(Message msg) {
		switch(msg.what){
		case Const.DETAIL_GET_DETAIL_TASK_RUNNING:
			return true;
		case Const.DETAIL_GET_DETAIL_TASK_SUCCEED:

			this.infoDto = (InfoDto)msg.obj;
			this.editId.setText(this.infoDto.getInfoId());
			this.editTitle.setText(this.infoDto.getInfoTitle());
			this.editDetail.setText(this.infoDto.getInfoDetail());
			this.editCreateDttm.setText(this.infoDto.getInfoCreateDttm());
			this.editUpdateDttm.setText(this.infoDto.getInfoUpdateDttm());
			
			this.dialogProgress.dismiss();
			this.dialogProgress = null;
			this.thread = null;
			this.handler = null;
			return true;
		case Const.DETAIL_GET_DETAIL_TASK_FAILED:
			this.dialogProgress.dismiss();
			this.dialogProgress = null;
			this.thread = null;
			this.handler = null;
			return true;
		default:
			this.thread = null;
			return false;
		}
	}

	/**
	 * �v���O���X��ʕ\������
	 * @param strMess
	 */
	private void showProgressDialog(String strMess) {
		this.dialogProgress = new CustomProgressDialog(this, strMess);
		this.dialogProgress.show();
	}
}
