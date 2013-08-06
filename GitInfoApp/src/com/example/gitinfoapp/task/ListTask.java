package com.example.gitinfoapp.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.example.gitinfoapp.common.Const;
import com.example.gitinfoapp.dto.InfoDto;

/**
 * 一覧画面用Task
 * @author zaike
 *
 */
public class ListTask implements Runnable {
	
	private Handler handler;
	private Message message;

	private boolean isComp = false;

	/**
	 * コンストラクタ
	 * @param handler
	 * @param context
	 */
	public ListTask(Handler handler) {
		super();
		this.handler = handler;
	}

	/**
	 * 主処理
	 */
	@Override
	public void run() {
		onRunning();

		shotRequest();
		// TODO:
//		for (int i = 0; i < 3000; i++) {
			
//		}
//		onSuccess();
	}

	/**
	 * リクエスト・ショット処理
	 */
	private void shotRequest() {
		String scheme = "http";
		String authority = "jenkins.136.kp";
		String path = "/request";

		Uri.Builder uB = new Uri.Builder();

		uB.scheme(scheme);
		uB.authority(authority);
		uB.path(path);

		String uri = uB.toString();
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 1000);
		HttpConnectionParams.setSoTimeout(params, 1000);

		HttpUriRequest httpRequest = new HttpGet(uri);

		HttpResponse httpResponse = null;

		List<InfoDto> retList = new ArrayList<InfoDto>();

		boolean bError = false;

		try {
			httpResponse = httpClient.execute(httpRequest);
		} catch (ClientProtocolException e) {
			bError = true;
			this.onError();
			return;
		} catch (IOException e) {
			bError = true;
			onError();
			return;
		}

		// 取得したJSONを文字列に
		String json = null;
		
		if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

			HttpEntity httpEntity = httpResponse.getEntity();
			
			try {
				json = EntityUtils.toString(httpEntity);
			} catch (ParseException e) {
				bError = true;
				onError();
				return;
			} catch (IOException e) {
				bError = true;
				onError();
				return;
			} finally {
				try {
					httpEntity.consumeContent();
				} catch (IOException e) {
					bError = true;
					onError();
					return;
				}
			}
		}

		httpClient.getConnectionManager().shutdown();

		if (json != null) {
			try {
				int ignoreIndex = json.indexOf("<script type=");

				if (ignoreIndex > -1) {
					json = json.substring(0, ignoreIndex);
				}

				JSONObject rootObject = new JSONObject(json);
				JSONArray listItems = rootObject.getJSONArray("items");
				
				for (int i = 0; i < listItems.length(); i++) {
					JSONObject listData = (JSONObject)listItems.get(i);
					JSONObject itemObject = listData.getJSONObject("item");

					InfoDto dto = new InfoDto();
					dto.setInfoId(String.valueOf(itemObject.get("id")));
					dto.setInfoTitle(String.valueOf(itemObject.get("subject")));
					dto.setInfoDetail(String.valueOf(itemObject.get("detail")));

					retList.add(dto);
				}
				
			} catch (JSONException e) {
				bError = true;
				onError();
				return;
			}
		}
		
		if (!bError) {
			onSuccess(retList);
		}
	}
	
	/**
	 * タスク開始処理
	 */
	private void onRunning() {
		this.message = new Message();
		this.message.what = Const.LIST_GET_LIST_TASK_RUNNING;
		
		this.handler.sendMessage(this.message);
	}

	/**
	 * エラー時処理
	 */
	private void onError() {
		isComp = true;
		this.message = new Message();
		this.message.what = Const.LIST_GET_LIST_TASK_FAILED;

		this.handler.sendMessage(this.message);
	}

	/**
	 * 成功時処理
	 */
	private void onSuccess(List<InfoDto> list) {
		isComp = true;
		this.message = new Message();
		this.message.what = Const.LIST_GET_LIST_TASK_SUCCEED;
		this.message.obj = list;

		this.handler.sendMessage(this.message);
	}

	public boolean isComp() {
		return isComp;
	}

	public Message getMessage() {
		return this.message;
	}
}
