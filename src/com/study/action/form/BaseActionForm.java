package com.study.action.form;

import java.io.File;
import java.io.InputStream;

import com.study.utility.PaginateParamters;

public class BaseActionForm {

	/**
	 * 随机码
	 */
	private String randomCodeKey; 
	
	/**
	 * 定义错误消息
	 */
	protected String errorMessage;	
	
	/**
	 * 文件
	 */
	private File file;
	
	private File image;
	
	private String fileId;
	 
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	/**
	 * 文件名称
	 */
	private String imageFileName; 
	
    /**
     * 文件类型
     */
    private String imageContentType;
	
	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	/**
	 * 文件名
	 */
	private String fileFileName;

	/**
	 * 内容类型 
	 */
	private String fileFileContentType;

	/**
	 * 文件输入流 
	 */
	private InputStream inputStream;
	
	/**
	 * 请求令牌
	 */
	private String requestToken;
	
	/**
	 * 分页参数
	 */
	private PaginateParamters paginateParamters;
	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}	

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setPaginateParamters(PaginateParamters paginateParamters) {
		this.paginateParamters = paginateParamters;
	}

	public PaginateParamters getPaginateParamters() {
		return paginateParamters;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileContentType(String fileFileContentType) {
		this.fileFileContentType = fileFileContentType;
	}

	public String getFileFileContentType() {
		return fileFileContentType;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setRandomCodeKey(String randomCodeKey) {
		this.randomCodeKey = randomCodeKey;
	}

	public String getRandomCodeKey() {
		return randomCodeKey;
	}
}