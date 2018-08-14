package com.today.todayfarm.dom;
/**
 * 农田长势信息
 * @author likunshang
 *
 */
public class GrowthInfo {
	int growthid;
	int growthtime;
	int fieldid;
	String path;
	String eleft;
	String ebottom;
	String eright;
	String etop;
	
	
	
	public GrowthInfo(int growthid, int growthtime, int fieldid, String path, String eleft, String ebottom,
			String eright, String etop) {
		super();
		this.growthid = growthid;
		this.growthtime = growthtime;
		this.fieldid = fieldid;
		this.path = path;
		this.eleft = eleft;
		this.ebottom = ebottom;
		this.eright = eright;
		this.etop = etop;
	}
	public String getEleft() {
		return eleft;
	}
	public void setEleft(String eleft) {
		this.eleft = eleft;
	}
	public String getEbottom() {
		return ebottom;
	}
	public void setEbottom(String ebottom) {
		this.ebottom = ebottom;
	}
	public String getEright() {
		return eright;
	}
	public void setEright(String eright) {
		this.eright = eright;
	}
	public String getEtop() {
		return etop;
	}
	public void setEtop(String etop) {
		this.etop = etop;
	}
	public int getGrowthid() {
		return growthid;
	}
	public void setGrowthid(int growthid) {
		this.growthid = growthid;
	}
	public int getGrowthtime() {
		return growthtime;
	}
	public void setGrowthtime(int growthtime) {
		this.growthtime = growthtime;
	}
	public int getFieldid() {
		return fieldid;
	}
	public void setFieldid(int fieldid) {
		this.fieldid = fieldid;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
