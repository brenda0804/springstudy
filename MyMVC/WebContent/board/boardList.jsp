<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
<jsp:include page="/top.jsp" />
<%
	String ctx=request.getContextPath(); 
%>
<div align="center" id="bbs">
<h1>MVC Board</h1>
	<p>
	<a href="<%=ctx%>/board-write.do#bbs">글쓰기</a>|
	<a href="<%=ctx%>/board-list.do#bbs">글목록</a>
	<p>
<!-- [12.05] pageSize관련 form시작==== -->
<form name="listF" action="board-list.do#bbs">
	<!-- hidden data ================ --><!-- pageSize와 더불어 cpage(현재페이지)값도 넘어가야하므로 hidden전송 -->
	<input type="hidden" name="cpage" value="${cpage}">
	<!-- ============================ -->
	<select name="pageSize" onchange="submit()"><!-- option값이 바뀔때마다 submit(). form에 action페이지가 없으면 자기페이지로. pageSize로 넘어간다. -->
	<option value="5" SELECTED>
		::페이지 SIZE 선택::
	</option>
	<c:forEach begin="5" end="20" step="5" var="ps">
		<option value="${ps}"<c:if test="${ps eq pageSize}">selected</c:if>>페이지 사이즈${ps}</option>
		<!-- forEach의 ps변수와 req에 저장된 pageSize와 같은지 if 문돌고 selected조건이 붙는다. -->
	</c:forEach>
	</select>
</form>
<!-- form end =========== -->
<p></p>

<%-- ${boardList} --%>

<table class="table table-striped table-condensed table-hover">
	<tr>
		<th width="10%">순번</th>
		<th width="40%">제목</th>
		<th width="20%">글쓴이</th>
		<th width="20%">날짜</th>
		<th width="10%">조회수</th>
	</tr>
	<!-- ---------------------------- -->
	<c:if test="${boardList ==null || empty boardList}">
		<tr>
			<td colspan="5">게시글이 없습니다.</td>
		</tr>
	</c:if>
	<c:if test="${boardList != null && not empty boardList}">
	<c:forEach var="board" items="${boardList}">
	<tr>
		<td>${board.idx}</td>
		<td align="left" style="padding-right:10px">
		<a href="board-view.do?idx=${board.idx}#bbs">${board.subject}</a>		 
		</td>
		<td>
		<a href="mailto:${board.email}">${board.name}</a>
		</td>
		<td>
		<fmt:formatDate value="${board.wdate}"
		 pattern="yyyy-MM-dd hh:mm"/>			
		</td>
		<td>${board.readnum}</td>
	</tr>
	</c:forEach>
	</c:if>
	<!-- ---------------------------- -->
	<tr>
		<td colspan="5" align="center">
		<hr width="500" color="#efefef">
		</td>
	</tr>

	<tr>
		<td colspan="3" align="center">
			<ul class="pagination">
				<li><a href="board-list.do?cpage=${cpage-1}#bbs">Prev</a></li>
				<c:forEach var="i" begin="1" end="${pageCount}" step="1"><!-- 변수/시작/끝/증가치 -->
				  <c:if test="${i eq cpage}">
					<li class="active"><a href="#bbs">${i}</a></li>
				  </c:if>
				  <c:if test="${i ne cpage}">
					<li><a href="board-list.do?cpage=${i}#bbs">${i}</a></li>
				  </c:if>
				</c:forEach>
				<li><a href="board-list.do?cpage=${cpage+1}#bbs">Next</a></li>
			</ul>
		</td>
		<td colspan="2" align="center">
		<font color=red><b>
		${cpage}
		</b></font>
		/${pageCount}
		<br>
		총게시물수: <span style="color:blue"><b>
		${totalCount} 개
		</b></span>
		</td>
	</tr>
</table>
<br>
<!-- [12.05]검색 form시작------------------------------- -->
<form name="bf" action="board-find.do"><!-- pageCount 관련 수정(name,action) -->
	<input type="hidden" name="cpage" value="${cpage}">
	<input type="hidden" name="pageSize" value="${pageSize}">
	<select name="findType"><!-- option의 value값이 그대로 노출됨(?findType=subject&findString=123) -->
		<option value="1" selected>제목</option><!-- subject 1,name 2,content 3로 보안성 증가 -->
		<option value="2">글쓴이</option>
		<option value="3">글내용</option>
	</select>
	<input type="text" size="40" name="findString" class="box">
	 <input type="button" onclick="findSearch()" class="btn btn-default"
	  value="검색">
</form>
<!-- ------------------------------------------ -->
</div>
<script type="text/javascript">
	function findSearch(){
		if(!bf.findString.value){
			alert('검색어를 입력하세요');
			bf.findString.focus();
			return;
		}		
		bf.submit();		
	}
</script>

<jsp:include page="/foot.jsp" />