package com.cxy.firststudio.Utils;

import java.io.Serializable;

public class PlanListBean implements Serializable {

	private String dateline;//      日期
	private float xiaolv;//        效率
	public String getDateline() {
		return dateline;
	}
	public void setDateline(String dateline) {
		this.dateline = dateline;
	}
	public float getXiaolv() {
		return xiaolv;
	}
	public void setXiaolv(float xiaolv) {
		this.xiaolv = xiaolv;
	}
	
}