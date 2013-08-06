package com.example.gitinfoapp.dto;

import java.io.Serializable;

/**
 * お知らせDTO
 * @author zaike
 */
public class InfoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	String infoId;
	String infoTitle;
	String infoDetail;
	String infoCreateDttm;
	String infoUpdateDttm;

	/**
	 * コンストラクタ
	 */
	public InfoDto() {
		this.infoId = "";
		this.infoTitle = "";
		this.infoDetail = "";
		this.infoCreateDttm = "";
		this.infoUpdateDttm = "";
	}

	/**
	 * お知らせID取得処理
	 * @return お知らせID
	 */
	public String getInfoId() {
		return infoId;
	}

	/**
	 * お知らせID設定処理
	 * @param infoId お知らせID
	 */
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	/**
	 * お知らせタイトル取得処理
	 * @return お知らせタイトル
	 */
	public String getInfoTitle() {
		return infoTitle;
	}

	/**
	 * お知らせタイトル設定処理
	 * @param infoTitle
	 */
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	/**
	 * お知らせ詳細取得処理
	 * @return お知らせ詳細
	 */
	public String getInfoDetail() {
		return infoDetail;
	}

	/**
	 * お知らせ詳細設定処理
	 * @param infoDetail お知らせ詳細
	 */
	public void setInfoDetail(String infoDetail) {
		this.infoDetail = infoDetail;
	}

	/**
	 * お知らせ登録日時取得処理
	 * @return お知らせ登録日時
	 */
	public String getInfoCreateDttm() {
		return infoCreateDttm;
	}

	/** 
	 * お知らせ登録日時設定処理
	 * @param infoCreateDttm
	 */
	public void setInfoCreateDttm(String infoCreateDttm) {
		this.infoCreateDttm = infoCreateDttm;
	}

	/**
	 * お知らせ更新日時取得処理
	 * @return お知らせ更新日時取得処理
	 */
	public String getInfoUpdateDttm() {
		return infoUpdateDttm;
	}

	/**
	 * お知らせ更新日時設定処理
	 * @param infoUpdateDttm お知らせ更新日時
	 */
	public void setInfoUpdateDttm(String infoUpdateDttm) {
		this.infoUpdateDttm = infoUpdateDttm;
	}
}
