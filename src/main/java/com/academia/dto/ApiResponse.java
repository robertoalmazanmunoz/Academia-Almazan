package com.academia.dto;

public class ApiResponse<T> {
	
	private boolean success;
	private String message;
	private int results;
	private T data;
	private String version;
	
	public ApiResponse() {
		
	}

	public ApiResponse(boolean success, String message, int results, String version, T data) {
		this.success = success;
		this.message = message;
		this.results = results;
		this.version = version;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getResults() {
		return results;
	}

	public void setResults(int results) {
		this.results = results;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}