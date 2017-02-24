<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/top.jsp" />
<script type="text/javascript">
	$(function(){
		$('#btnWrite').click(function(){
			if(!$('#subject').val()){
				alert('글제목을 입력하세요');
				$('#subject').focus();
				return;
			}
			if(! $('#name').val()){
				alert('글쓴이를 입력하세요');
				$('#name').focus();
				return;
			}
			
			if(! $('#pwd').val()){
				alert('비밀번호를 입력하세요');
				$('#pwd').focus();
				return;
			}
			
			$('#bf').submit();
		});
	});
	

</script>

<%
	String ctx=request.getContextPath();
%>
<div align="center" id="bbs">    
	<h1>MVC Board</h1>
	<p>
	<a href="<%=ctx%>/board-write.do#bbs">글쓰기</a>|
	<a href="<%=ctx%>/board-list.do#bbs">글목록</a>
	<p>
<!--파일 업로드시
method: POST
enctype: multipart/form-data  -->	
<form name="bf" id="bf" role="form" action="board-editEnd.do"
 method="POST"  enctype="multipart/form-data"> 	
 	<table class="table table-bordered">
 		<tr>
 			<td style="width:20%"><b>글번호</b></td>
 			<td  style="width:80%">
 			<input type="text" name="idx" id="idx"
 			value="${board.idx}"
 			 class="form-control">
 			</td>
 		</tr>
 		<tr>
 			<td style="width:20%"><b>제목</b></td>
 			<td  style="width:80%">
 			<input type="text" name="subject" id="subject"
 			value="${board.subject}"
 			 class="form-control">
 			</td>
 		</tr>
 		<tr>
 			<td style="width:20%"><b>글쓴이</b></td>
 			<td  style="width:80%">
 			<input type="text" name="name" id="name"
 			value="${board.name}"
 			class="form-control">
 			</td>
 		</tr>
 		<tr>
 			<td style="width:20%"><b>이메일</b></td>
 			<td  style="width:80%">
 			<input type="text" name="email" id="email"
 			value="${board.email}"
 			class="form-control">
 			</td>
 		</tr>
 		<tr>
 			<td style="width:20%"><b>홈페이지</b></td>
 			<td  style="width:80%">
 			<input type="text" name="homepage"
 			 id="homepage" value="${board.homepage}"
 			 class="form-control">
 			</td>
 		</tr>
 		<tr>
 			<td style="width:20%"><b>글내용</b></td>
 			<td  style="width:80%">
 			<textarea name="content" id="content"
 			 rows="10" cols="50"
 			  class="form-control">${board.content }</textarea>
 			</td>
 		</tr>
 		<tr>
 			<td style="width:20%"><b>비밀번호</b></td>
 			<td  style="width:80%">
 			<div class="col-md-5">
 			<input type="password" name="pwd" id="pwd"
 			class="form-control">
 			</div>
 			</td>
 		</tr>
 		<tr>
 			<td style="width:20%"><b>첨부파일</b></td>
 			<td  style="width:80%">
 			<!-- -------------------- -->
 			<p><b>${board.filename}</b>[${board.filesize}]bytes</p>
 			<!-- --------------------  -->
 			<input type="file" name="filename" id="filename"
 			class="form-control">
 			
 			</td>
 		</tr>
 		<tr>
 			<td colspan="2">
 				<button type="button" id="btnWrite"
 				class="btn btn-success">글수정</button>
 				<button type="reset" id="btnReset"
 				 class="btn btn-warning">다시쓰기</button>
 			</td>
 		</tr>
 	</table>
	

</form>	    

</div>
<jsp:include page="/foot.jsp" />