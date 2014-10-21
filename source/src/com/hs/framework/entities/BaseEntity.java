package com.hs.framework.entities;

import java.io.Serializable;

/**
 *
 * @author wanghuan
 * @date 2014年10月11日 下午3:36:22
 * @email hunter.v.wang@gmail.com
 *
 */
public class BaseEntity implements Serializable{

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
