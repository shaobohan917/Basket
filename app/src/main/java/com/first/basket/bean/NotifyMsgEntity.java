package com.first.basket.bean;

/**
 * 通知实体类,用于NotifyManager向所有观察者发送消息
 * 
 * @author weichao
 *
 */
public class NotifyMsgEntity {

	private int code;// 消息类别代码
	private Object data;// 消息数据实体

	private Object content;


	public NotifyMsgEntity() {
		super();
	}

	public NotifyMsgEntity(int code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}

	public NotifyMsgEntity(int code, Object data, Object content) {
		this.code = code;
		this.data = data;
		this.content = content;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}


}
