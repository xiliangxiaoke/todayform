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
	double eleft;
	double ebottom;
	double eright;
	double etop;




	public GrowthInfo() {
	}
	public double getEleft() {
		return eleft;
	}
	public void setEleft(double eleft) {
		this.eleft = eleft;
	}
	public double getEbottom() {
		return ebottom;
	}
	public void setEbottom(double ebottom) {
		this.ebottom = ebottom;
	}
	public double getEright() {
		return eright;
	}
	public void setEright(double eright) {
		this.eright = eright;
	}
	public double getEtop() {
		return etop;
	}
	public void setEtop(double etop) {
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