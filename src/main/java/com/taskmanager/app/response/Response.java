package com.taskmanager.app.response;

public class Response {
	private Object result;
	private String message;
	public Response() {
		super();
	}
	public Response(Object result,  String message) {
		super();
		this.result = result;
		this.message = message;
	}
	@Override
	public String toString() {
		return "Response [result=" + result + ", message=" + message + "]";
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
