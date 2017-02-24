<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"
		 uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${mode !=null && mode=='pop'}">
	<script type="text/javascript">
		alert('${message}');
		self.close();
		//location.href='${loc}';
		//부모창을 새로 고침
		opener.location.reload();
	</script>	
</c:if>		 		     
<c:if test="${mode ==null }">    
	<script type="text/javascript">
		alert('<%=request.getAttribute("message")%>');
		location.href='<%=request.getAttribute("loc")%>';
	</script>
</c:if>