/*
 * DTO ( Data Transfer Object) : db에서 가져온 데이터나 gui입력 폼에서
    가져온 데이터를 담고 있는 객체
    VO(Value Object) 라고도 함
 */
package memo.model;
import java.io.Serializable;
import java.sql.Date;

public class MemoDTO implements Serializable{
    //db 컬럼과 매칭되는 멤버변수를 선언=> 캡슐화
    private int idx;
    private String name;
    private String msg;
    private java.sql.Date wdate;
    public MemoDTO(){
        
    }
    public MemoDTO(int idx, String name, String msg, Date wdate) {
        this.idx = idx;
        this.name = name;
        this.msg = msg;
        this.wdate = wdate;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getWdate() {
        return wdate;
    }

    public void setWdate(Date wdate) {
        this.wdate = wdate;
    }

    @Override
    public String toString() {
        return "MemoDTO{" + "idx=" + idx + ", name=" + name + ", msg=" + msg + ", wdate=" + wdate + '}';
    }
    
}///////////////////////////////////////
