<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,memo.model.*" %>
<!-- memoList.jsp -->    
<jsp:include page="/top.jsp" />
<div align="center"  id="memoTab">    
	<h1>::한줄 메모장::</h1>    
	<table   style="width:90%" 
	class="table table-condensed table-hover">
		<tr class="danger">
			<th>글번호</th>
			<th style="width:50%">메모내용</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
		<!-- -------------------------  -->
		<% 
			Vector<MemoDTO> memoV
			=(Vector<MemoDTO>)request.getAttribute("memoV");
			if(memoV==null||memoV.size()==0){
		%>
		<tr><td colspan="4">
		<b>서버오류이거나 데이터가 없습니다.</b></td>	
		<%	
			}else{
		for(MemoDTO m:memoV){		
		%>
		<tr>
			<td><%=m.getIdx()%></td>
			<td><%=m.getMsg()%></td>
			<td><%=m.getName()%></td>
			<td><%=m.getWdate()%></td>
		</tr>
		<% 
		}//for----------
			}//else--------------
		%>	
		<!-- -------------------------  -->
	</table>
</div>
<jsp:include page="/foot.jsp" />



