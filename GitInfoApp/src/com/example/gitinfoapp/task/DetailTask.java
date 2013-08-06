package com.example.gitinfoapp.task;

import java.io.IOException;

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

import android.content.Context;
import android.net.ParseException;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.example.gitinfoapp.common.Const;
import com.example.gitinfoapp.dto.InfoDto;

/**
 * �ڍ׉�ʗpTask
 * @author zaike
 *
 */
public class DetailTask implements Runnable {

	private Handler handler;
	private Message message;
	private Context context;
	private String infoId;

	/**
	 * �R���X�g���N�^
	 * @param handler
	 * @param context
	 */
	public DetailTask(Handler handler, Context context, String infoId) {
		super();
		this.handler = handler;
		this.context = context;
		this.infoId = infoId;
	}

	/**
	 * �又��
	 */
	@Override
	public void run() {
		onRunning();

		shotRequest();
	}

	/**
	 * ���N�G�X�g�E�V���b�g����
	 */
	private void shotRequest() {
		String scheme = "http";
		String authority = "jenkins.136.kp";
		String path = "/request/detail";

		boolean bError = false;
		InfoDto dto = new InfoDto();

		Uri.Builder ub = new Uri.Builder();
		
		ub.scheme(scheme);
		ub.authority(authority);
		ub.path(path);
		ub.appendQueryParameter("id", this.infoId);

		String uri = ub.toString();

		HttpClient httpClient = new DefaultHttpClient();
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 1000);
		HttpConnectionParams.setSoTimeout(params, 1000);

		HttpUriRequest httpRequest = new HttpGet(uri);
		
		HttpResponse httpResponse = null;
		
		try {
			httpResponse = httpClient.execute(httpRequest);
		} catch (ClientProtocolException e) {
			bError = true;
			onError();
			return;
		} catch (IOException e) {
			bError = true;
			onError();
			return;
		}

		// �擾����JSON�𕶎����
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
			}
		}

		httpClient.getConnectionManager().shutdown();

		if (json != null) {
			try {
				JSONObject rootObject = new JSONObject(json);
				JSONArray listItems = rootObject.getJSONArray("items");

				if (listItems.length() > 0) {
					JSONObject listData = (JSONObject)listItems.get(0);
					JSONObject itemObject = listData.getJSONObject("item");

					dto.setInfoId(String.valueOf(itemObject.get("id")));
					dto.setInfoTitle(String.valueOf(itemObject.get("subject")));
					dto.setInfoDetail(String.valueOf(itemObject.get("detail")));
					dto.setInfoCreateDttm(String.valueOf(itemObject.get("in_dt")));
					
					if (!itemObject.get("up_dt").equals(null)) {
						dto.setInfoUpdateDttm(String.valueOf(itemObject.get("up_dt")));
					}
				}
				
			} catch (JSONException e) {
				bError = true;
				onError();
				return;
			}
		}

		if (!bError) {
			onSuccess(dto);
		}
	}

	/**
	 * �^�X�N�J�n����
	 */
	private void onRunning() {
		this.message = new Message();
		this.message.what = Const.DETAIL_GET_DETAIL_TASK_RUNNING;

		this.handler.sendMessage(this.message);
	}

	/**
	 * �G���[������
	 */
	private void onError() {
		this.message = new Message();
		this.message.what = Const.DETAIL_GET_DETAIL_TASK_FAILED;
		
		this.handler.sendMessage(this.message);
	}

	/**
	 * ����������
	 */
	private void onSuccess(InfoDto dto) {
		this.message = new Message();
		this.message.what = Const.DETAIL_GET_DETAIL_TASK_SUCCEED;
		this.message.obj = dto;
		
		this.handler.sendMessage(this.message);
	}
}
