package com.example.gitinfoapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.gitinfoapp.adapter.InfoListAdapter;
import com.example.gitinfoapp.common.Const;
import com.example.gitinfoapp.dto.InfoDto;
import com.example.gitinfoapp.task.ListTask;
import com.zako.dialog.CustomProgressDialog;

/**
 * �ꗗ���
 * @author zaikea
 */
public class ListActivity extends Activity implements Handler.Callback {

	private Button button;

	private ListView listView;

	private Handler handler;
	
	private CustomProgressDialog dialogProgress;

	private ListTask taskList;

	private Thread thread;

	private static List<InfoDto> dataList = new ArrayList<InfoDto>();

	private static InfoListAdapter adapter;

	/**
	 * ��������
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		// ��ʃp�[�c�̔z�u
		findViews();

		setAdapters();
		
		setListener();

		doMainProcess(this);
	}

	/**
	 * ��ʃp�[�c������
	 */
	protected void findViews() {
		button = (Button) findViewById(R.id.button1);
		listView = (ListView) findViewById(R.id.listView1);
	}

	/**
	 * ��ʃp�[�c�C�x���g���X�i�[�o�^
	 */
	protected void setListener() {
		this.button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = null;

				// �I�����ꂽ�s�̃f�[�^���擾����B
				intent = new Intent(ListActivity.this, HogeActivity.class);
				startActivity(intent);
			}
		});
		
		this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					ListView listView = (ListView) parent;

					InfoDto selectedData = (InfoDto) listView.getItemAtPosition(position);

					Intent intent = null;

					// �I�����ꂽ�s�̃f�[�^���擾����B
					intent = new Intent(ListActivity.this, DetailActivity.class);
					intent.putExtra("selData", selectedData);
					startActivity(intent);
				}
			});
	}

	/**
	 * ���X�g�A�_�v�^�[�ݒ菈��
	 */
	protected void setAdapters() {
		if (dataList.size() > 0) {
			dataList.clear();
		}
	}

	/**
	 * �又��
	 * @param context
	 */
	private void doMainProcess(Context context) {
		if (adapter != null) {
			adapter.clear();
		}

		// �ꗗ�擾���_�C�A���O�̕\��
		showProgressDialog("�ꗗ�擾���E�E�E");

		// �X���b�h�Ăяo��
		this.handler = new Handler(this);
		this.taskList = new ListTask(this.handler);
		this.thread = new Thread(this.taskList);
		this.thread.start();
	}

	/**
	 * �又���n���h�����O
	 */
	@Override
	public boolean handleMessage(Message msg) {
		switch(msg.what) {
		case Const.LIST_GET_LIST_TASK_RUNNING:
			return true;
		case Const.LIST_GET_LIST_TASK_SUCCEED:
			if (adapter != null) {
				adapter.clear();
			}

			dataList = (List<InfoDto>)msg.obj;
/*
			msg.obj
			for (int i = 0; i < 5; i++) {
				InfoDto data = new InfoDto();
				data.setInfoId(String.valueOf((i + 1)));
				data.setInfoTitle("hoge" + String.valueOf(i));
				data.setInfoDetail("fuga" + String.valueOf(i));
				dataList.add(data);
			}
*/
			adapter = new InfoListAdapter(this, dataList);
			this.listView.setAdapter(adapter);

			this.dialogProgress.dismiss();
			this.dialogProgress = null;
			this.thread = null;
			this.handler = null;
			return true;
		case Const.LIST_GET_LIST_TASK_FAILED:
			this.dialogProgress.dismiss();
			this.dialogProgress = null;
			this.thread = null;
			this.handler = null;

			// TODO:Alert
			
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
