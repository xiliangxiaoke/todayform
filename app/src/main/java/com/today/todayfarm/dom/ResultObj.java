package com.today.todayfarm.dom;

import java.util.List;

/**
 * 请求数据返回的基本结构 code==200 正常返回
 * @author likunshang
 *
 * @param <T>
 */
public class ResultObj<T> {

	/**
	 * 服务器返回 的状态
	 * code:0 数据正常返回，1：调用接口返回失败，-1：非法的token，这时候建议调到登录页面
	 */
	public int code = -1;
	public String msg;
	public T object;
	public List<T> list;

	/**
	 * token
	 */
	public String token;
	/**
	 * boolean 是否是实名认证
	 */
	public String auth;

	public PropInfo prop;

	public int all;// 总数量

	public String totalPage;


	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public int getAll() {
		return all;
	}

	public void setAll(int all) {
		this.all = all;
	}

	public PropInfo getProp() {
		return prop;
	}

	public void setProp(PropInfo prop) {
		this.prop = prop;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
}
