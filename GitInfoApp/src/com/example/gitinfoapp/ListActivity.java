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
 * 一覧画面
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
	 * 初期処理
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		// 画面パーツの配置
		findViews();

		setAdapters();
		
		setListener();

		doMainProcess(this);
	}

	/**
	 * 画面パーツ初期化
	 */
	protected void findViews() {
		button = (Button) findViewById(R.id.button1);
		listView = (ListView) findViewById(R.id.listView1);
	}

	/**
	 * 画面パーツイベントリスナー登録
	 */
	protected void setListener() {
		this.button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = null;

				// 選択された行のデータを取得する。
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

					// 選択された行のデータを取得する。
					intent = new Intent(ListActivity.this, DetailActivity.class);
					intent.putExtra("selData", selectedData);
					startActivity(intent);
				}
			});
	}

	/**
	 * リストアダプター設定処理
	 */
	protected void setAdapters() {
		if (dataList.size() > 0) {
			dataList.clear();
		}
	}

	/**
	 * 主処理
	 * @param context
	 */
	private void doMainProcess(Context context) {
		if (adapter != null) {
			adapter.clear();
		}

		// 一覧取得中ダイアログの表示
		showProgressDialog("一覧取得中・・・");

		// スレッド呼び出し
		this.handler = new Handler(this);
		this.taskList = new ListTask(this.handler);
		this.thread = new Thread(this.taskList);
		this.thread.start();
	}

	/**
	 * 主処理ハンドリング
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
	 * プログレス画面表示処理
	 * @param strMess
	 */
	private void showProgressDialog(String strMess) {
		this.dialogProgress = new CustomProgressDialog(this, strMess);
		this.dialogProgress.show();
	}
}
