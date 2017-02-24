<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="section">
    <div>
    	<div class="row">
    		<div class="col-md-12 col-xs-12 col-sm-12">
    			<h2 class="text text-primary">NEW 상품</h2>
    		</div>
    	</div>
        <div class="row">
        <c:if test="${newList eq null || empty newList}">
          <div class="col-md-3 col-xs-6 col-sm-6"><!-- md는 데탑,xs는 스마트폰,sm은 태블릿. -->
            <img src="http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png"
            class="img-responsive">
            <h2>NEW 상품 준비중</h2>
          </div>
        </c:if>
        <c:if test="${newList ne null && not empty newList}">
         <c:forEach var="pd" items="${newList}" varStatus="st" begin="0" end="3"><!-- 1~4까지만 가져오 -->
         <!-- 자료구조는 items에 불러와서 pd로 사용 -->
         <!-- pd는 ProductVO를 참조하는 변수이다. (type) -->
         <!-- varStatus에 지정된 변수로 반복문의 상태정보를 얻어올 수 있다.
           st.index:반복문의 인덱스번호 0부터 시작 
           st.count:반복문의 횟수(1부터시작0를 반환
           -->
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
        </div><!-- row end -->
    </div>
</div>