<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
    
<jsp:include page="/top.jsp" />
<div align="center" id="bbs">    
	<h1>${title}</h1>
	<form name="frm" id="frm" method="POST">
		<input type="hidden" name="idx" value="${idx}">
		<label for="pwd">비밀번호</label>
		<input type="password" name="pwd" id="pwd">
		<button type="button" id="btn" 
		class="btn btn-primary">전송</button>
	</form>    
	
	<script type="text/javascript">
		$(function(){
			$('#btn').click(function(){
				if(!$('#pwd').val()){
					alert('비밀번호를 입력하세요');
					$('#pwd').focus();
					return;
				}//if----
				<c:if test="${mode eq 'del'}">					
					$('#frm').attr('action','board-remove.do');
				</c:if>
				<c:if test="${mode eq 'edit'}">					
					$('#frm').attr('action','board-edit.do#bbs');
				</c:if>
				
				$('#frm').submit();
				
			});
		});
	</script>

</div>
<jsp:include page="/foot.jsp" />



