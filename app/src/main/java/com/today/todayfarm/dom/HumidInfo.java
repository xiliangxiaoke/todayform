package com.today.todayfarm.dom;

public class HumidInfo {

	int himidid;
	int humidtime;
	int fieldid;
	String path;
	String eleft;
	String ebottom;
	String eright;
	String etop;
	
	
	
	public HumidInfo(int himidid, int humidtime, int fieldid, String path, String eleft, String ebottom, String eright,
			String etop) {
		super();
		this.himidid = himidid;
		this.humidtime = humidtime;
		this.fieldid = fieldid;
		this.path = path;
		this.eleft = eleft;
		this.ebottom = ebottom;
		this.eright = eright;
		this.etop = etop;
	}
	public int getHimidid() {
		return himidid;
	}
	public void setHimidid(int himidid) {
		this.himidid = himidid;
	}
	public int getHumidtime() {
		return humidtime;
	}
	public void setHumidtime(int humidtime) {
		this.humidtime = humidtime;
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
	
	
}
