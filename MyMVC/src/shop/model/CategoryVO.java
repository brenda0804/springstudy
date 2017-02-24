package shop.model;

import java.io.Serializable;

public class CategoryVO implements Serializable {
	private Integer cnum;
	private String code;
	private String cname;
	
	public CategoryVO(){
		
	}

	public CategoryVO(Integer cnum, String code, String cname) {
		super();
		this.cnum = cnum;
		this.code = code;
		this.cname = cname;
	}

	public Integer getCnum() {
		return cnum;
	}

	public void setCnum(Integer cnum) {
		this.cnum = cnum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	
}
