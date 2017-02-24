package board.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class BoardVO implements Serializable {
	
	//property 선언
	private Integer idx;
	private String name;
	private String email;
	private String homepage;
	private String pwd;
	private String subject;
	private String content;
	
	private java.sql.Timestamp wdate;
	private int readnum;
	private String filename;
	private long filesize;
	private int refer; //답변형 관련
	private int lev;//답변형 관련
	private int sunbun;//답변형 관련
	
	public BoardVO(){
		
	}

	public BoardVO(Integer idx, String name, String email, String homepage, String pwd, String subject, String content,
			Timestamp wdate, int readnum, String filename, long filesize, int refer, int lev, int sunbun) {
		super();
		this.idx = idx;
		this.name = name;
		this.email = email;
		this.homepage = homepage;
		this.pwd = pwd;
		this.subject = subject;
		this.content = content;
		this.wdate = wdate;
		this.readnum = readnum;
		this.filename = filename;
		this.filesize = filesize;
		this.refer = refer;
		this.lev = lev;
		this.sunbun = sunbun;
	}
	//setter, getter-----------

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public java.sql.Timestamp getWdate() {
		return wdate;
	}

	public void setWdate(java.sql.Timestamp wdate) {
		this.wdate = wdate;
	}

	public int getReadnum() {
		return readnum;
	}

	public void setReadnum(int readnum) {
		this.readnum = readnum;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public int getRefer() {
		return refer;
	}

	public void setRefer(int refer) {
		this.refer = refer;
	}

	public int getLev() {
		return lev;
	}

	public void setLev(int lev) {
		this.lev = lev;
	}

	public int getSunbun() {
		return sunbun;
	}

	public void setSunbun(int sunbun) {
		this.sunbun = sunbun;
	}

	@Override
	public String toString() {
		return "BoardVO [idx=" + idx + ", name=" + name + ", email=" + email + ", homepage=" + homepage + ", pwd=" + pwd
				+ ", subject=" + subject + ", content=" + content + ", wdate=" + wdate + ", readnum=" + readnum
				+ ", filename=" + filename + ", filesize=" + filesize + ", refer=" + refer + ", lev=" + lev
				+ ", sunbun=" + sunbun + "]";
	}
	
	

}////////////////////////////////////////











