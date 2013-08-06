package com.example.gitinfoapp.dto;

import java.io.Serializable;

/**
 * ���m�点DTO
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
	 * �R���X�g���N�^
	 */
	public InfoDto() {
		this.infoId = "";
		this.infoTitle = "";
		this.infoDetail = "";
		this.infoCreateDttm = "";
		this.infoUpdateDttm = "";
	}

	/**
	 * ���m�点ID�擾����
	 * @return ���m�点ID
	 */
	public String getInfoId() {
		return infoId;
	}

	/**
	 * ���m�点ID�ݒ菈��
	 * @param infoId ���m�点ID
	 */
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	/**
	 * ���m�点�^�C�g���擾����
	 * @return ���m�点�^�C�g��
	 */
	public String getInfoTitle() {
		return infoTitle;
	}

	/**
	 * ���m�点�^�C�g���ݒ菈��
	 * @param infoTitle
	 */
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	/**
	 * ���m�点�ڍ׎擾����
	 * @return ���m�点�ڍ�
	 */
	public String getInfoDetail() {
		return infoDetail;
	}

	/**
	 * ���m�点�ڍאݒ菈��
	 * @param infoDetail ���m�点�ڍ�
	 */
	public void setInfoDetail(String infoDetail) {
		this.infoDetail = infoDetail;
	}

	/**
	 * ���m�点�o�^�����擾����
	 * @return ���m�点�o�^����
	 */
	public String getInfoCreateDttm() {
		return infoCreateDttm;
	}

	/** 
	 * ���m�点�o�^�����ݒ菈��
	 * @param infoCreateDttm
	 */
	public void setInfoCreateDttm(String infoCreateDttm) {
		this.infoCreateDttm = infoCreateDttm;
	}

	/**
	 * ���m�点�X�V�����擾����
	 * @return ���m�点�X�V�����擾����
	 */
	public String getInfoUpdateDttm() {
		return infoUpdateDttm;
	}

	/**
	 * ���m�点�X�V�����ݒ菈��
	 * @param infoUpdateDttm ���m�点�X�V����
	 */
	public void setInfoUpdateDttm(String infoUpdateDttm) {
		this.infoUpdateDttm = infoUpdateDttm;
	}
}
