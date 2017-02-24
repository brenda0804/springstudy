<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- -------------------------------- -->    
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript"
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript"
	src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link href="mystyle.css" rel="stylesheet" type="text/css">
<!-- -------------------------------- -->


<script type="text/javascript">
	var check=function(){
		if(!frm.name.value){
			alert('작성자를 입력하세요');
			frm.name.focus();
			return;
		}
		if(frm.msg.value.length >100){
			alert("메모내용은 100자 이내여야 해요");
			frm.msg.select();
			return;
		}
		//window.document.frm.submit();
		frm.submit(); //서버에 전송
	}
</script>


<div align="center"> 
<%-- ${requestScope.memo} --%>
<%-- ${memo} --%>
   
	<h1 id="chap01">한줄 메모장 수정</h1>    
<form name="frm" action="memoEditEnd.do" method="POST">
<table border="0" style="width:90%"
 class="table table-striped table-bordered">
			<tr>
				<th colspan="4">★★한줄 메모장 수정★★</th>
			</tr>
			<tr>
				<td width="15%"><b>글번호</b></td>
				<td width="35%">
				<input type="text" name="idx" readonly 
				class="box" value="${memo.idx}"
				style="width:50%">
				</td>
				<td width="15%"><b>작성일</b></td>
				<td>${memo.wdate}</td>
			</tr>
			<tr>
				<td width="15%"><b>작성자</b></td>
				<td width="85%" colspan="3">
				<input type="text" name="name" class="box"
				value="${memo.name}"
				style="width:50%">
				</td>
			</tr>
			<tr>
				<td width="15%"><b>메모내용</b></td>
				<td width="85%"  colspan="3">
				<input type="text" name="msg" class="box"
				value="${memo.msg}"
				 style="width:80%">
				</td>
			</tr>
			<tr>
				<td colspan="4">
				<button class="btn btn-info" type="button" onclick="check()">메모 수정</button>
				<button class="btn btn-warning" type="reset">다시 쓰기</button>
				</td>
			</tr>
		</table>
	</form>
</div>






