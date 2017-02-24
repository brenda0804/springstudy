<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/top.jsp"/>
<div align="center" id="shop">
	<h1>"${pname}" 상품 검색</h1>
	<div class="row">
        <c:if test="${productList eq null || empty productList}">
          <div class="col-md-3 col-xs-6 col-sm-6"><!-- md는 데탑,xs는 스마트폰,sm은 태블릿. -->
            <img src="http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png"
            class="img-responsive">
            <h2>상품 준비중</h2>
          </div>
        </c:if>
        <c:if test="${productList ne null && not empty productList}">
         <c:forEach var="pd" items="${productList}" varStatus="st" begin="0" end="3"><!-- 1~4까지만 가져오 -->
          <div class="col-md-3 col-xs-6 col-sm-6"><!-- md는 데탑,xs는 스마트폰,sm은 태블릿. -->
           <%--  ${st.index} // ${st.count} --%>
            <a href="pdDetail.do?pnum=${pd.pnum}"><img src="images/${pd.pimage1}" class="img-responsive"></a>
            <h2>${pd.pname}</h2>
            <p>
              <del>${pd.price}</del> 원<br>
              <span class="text text-danger"><b>${pd.saleprice}</b></span> 원<br>
              <span class="text text-primary"><b>${pd.percent}</b> %할인</span><br><!-- getPersent만든것 호출 -->
              <span class="label label-warning">${pd.point} POINT</span><br>
            </p>
          </div><!-- col-md-3 end -->
          <c:if test="${st.count mod 4 eq 0}">
          	</div><!-- 행을 끝내고 새로운 행을 시작 -->
          	<div class="row">
          </c:if>
         </c:forEach>
        </c:if>
        </div>
</div>
<jsp:include page="/foot.jsp"/>