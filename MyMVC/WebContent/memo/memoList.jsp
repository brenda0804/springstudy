<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,memo.model.*" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>    
<!-- ====================================== 
태그라이브러리 JSTL 을 사용하기 위해서는
taglib지시어를 명시한다===================== -->    
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
			<th>삭제</th>
		</tr>
		<!-- -------------------------  -->
		<c:if test="${requestScope.memoV == null
		 || empty requestScope.memoV}">
		   <!--requestScope는 생략가능  -->
		<tr><td colspan="5">
		<b>서버오류이거나 데이터가 없습니다.</b></td>	
		</c:if>
		<c:if test="${memoV!=null && not empty memoV}">
			<c:forEach var="m" items="${memoV}">		
				<tr>
					<td>${m.idx}</td>
					<td>${m.msg}</td>
					<td>${m.name}</td>
					<td>${m.wdate}</td>
					<td><a href="#memoTab"
					 onclick="del('${m.idx}')">삭제</a>|
					 <a href="#memoTab"
					 onclick="edit('${m.idx}')">수정</a>
					 </td>
				</tr>
			</c:forEach>
		</c:if>
		<!-- -------------------------  -->
	</table>
</div>
<script type="text/javascript">
	var edit=function(idx){
		var url="memoEdit.do?idx="+idx;
		win=open(url,"edit",
		"width=500, height=400, left=100, top=100");
	}


	var del=function(idx){
		var yn=confirm(idx+"번 글을 삭제할까요?");
		if(yn){
			location.href="memoDel.do?idx="+idx;
		}
	}
</script>

<jsp:include page="/foot.jsp" />







