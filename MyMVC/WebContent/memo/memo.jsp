<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/top.jsp" />
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
	<h1 id="chap01">한줄 메모장</h1>    
<form name="frm" action="memoInsert.do" method="POST">
<table border="0" style="width:90%"
 class="table table-striped table-bordered">
			<tr>
				<th colspan="2">★★한줄 메모장★★</th>
			</tr>
			<tr>
				<td width="15%"><b>작성자</b></td>
				<td width="85%">
				<input type="text" name="name" class="box"
				style="width:50%">
				</td>
			</tr>
			<tr>
				<td width="15%"><b>메모내용</b></td>
				<td width="85%">
				<input type="text" name="msg" class="box"
				 style="width:80%">
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<button class="btn btn-info" type="button" onclick="check()">메모 남기기</button>
				<button class="btn btn-warning" type="reset">다시 쓰기</button>
				</td>
			</tr>
		</table>
	</form>
</div>
<jsp:include page="/foot.jsp" />