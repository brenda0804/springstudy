<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/top.jsp"/>
<!-- 테이블 정렬 jQuery plugin -->
<script type="text/javascript" src="./js/stupidtable.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#cartTable').stupidtable();
	});
</script>
<div align="center" id="shop">
	<h1>${loginUser.name}[${loginUser.userid}]님의 장바구니</h1>
	<form role="form" name="orderF" action="order.do">
		<input type="text" style="display:none" name="prevent">
		<!-- input text박스가 1개이면 엔터를 쳤을 때 submit이 된다. 이걸 방지하기 위해text박스를 하나 추가하고 보이지 않게 하자 -->
		<table class="table table-bordered table-responsive"  id="cartTable">
			<thead>
				<tr style="text-align:center">
					<th data-sort="int">번호</th>
					<th data-sort="string">상품명</th>
					<th data-sort="int">수량</th>
					<th data-sort="int">단가</th>
					<th data-sort="int">금액</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
			<!-- ======= 반복문 ================ -->
			<c:if test="${cartList eq null || empty cartList}">
				<tr>
					<td colspan="6" style="text-align:center">
						<b>장바구니에 담긴 상품이 없습니다.</b>
					</td>
				</tr>	
			</c:if>
			<c:if test="${cartList ne null && not empty cartList}">
				<c:forEach var="pd" items="${cartList}" varStatus="st">
					<tr>
						<td>
							<input type="checkbox" name="opnum" id="opnum" value="${pd.pnum}">
							&nbsp;&nbsp; ${pd.pnum}
						</td>
						<td align="center" style="width:30%">
							<a href="pdDetail.do?pnum=${pd.pnum}" target="_blank"><img src="images/${pd.pimage1}" class="img img-responsive img-thumbnail"
								style="width:50%;min-width:80px"></a><br>
							${pd.pname}
						</td>
						<td><!-- 수량 -->
							<input type="number" name="oqty" id="oqty${st.index}" value="${pd.pqty}" size="2" min="1" 
								style="width:50px" max="50">
							<a href="#shop" class="btn btn-default" 
								onclick="editCart('${pd.pnum}','${st.index}')">수정</a>	
						</td>
						<td>
							<fmt:formatNumber value="${pd.saleprice}" pattern="###,###"/>원<br>
							<span class="label label-info">${pd.point}POINT</span>
						</td>
						<td>
							<b><fmt:formatNumber value="${pd.totalPrice}" pattern="###,###"/>원</b><br>
							<span class="label label-danger">${pd.totalPoint}POINT</span>
						</td>
						<td>
							<a href="#shop" onclick="delCart('${pd.pnum}')">삭제</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<!-- ============================ -->
			</tbody>
		</table>
		<table>
			<tr>
				<td colspan="4" style="padding-right:10px">
					<b>장바구니 총액</b>
					<span style="color:blue;font-weight:bold">
					<fmt:formatNumber value="${cartMap.cartTotalPrice}" pattern="###,###"/>
					</span>원<br>
					<b>총포인트</b><span style="color:red">
					<b>${cartMap.cartTotalPoint}</b>
					</span>POINT	
				</td>
				<td colspan="2">
					<button type="button" class="btn btn-primary" onclick="">주문하기</button>
					<button type="button" class="btn btn-warning" onclick="history.go(-2)">계속쇼핑</button>
				</td>
			</tr>
		</table>
	</form><!-- 주문 form end -->
	<!-- 삭제 form start  -->
	<form name="df" id="df" action="cartRemove.do" method="POST">
		<input type="text" name="pnum" id="delPnum" style="display:none">
		<!-- hidden type 변경대신 style로 none값줌 -->
	</form>
	<!-- ============== -->
	<!-- 삭제 form start  -->
	<form name="ef" id="ef" action="cartEdit.do" method="POST">
		<input type="text" name="pnum" id="editPnum" style="display:none">
		<input type="text" name="oqty" id="editOqty" style="display:none">
	</form>
	<!-- ============== -->
</div>
<jsp:include page="/foot.jsp"/>
<script type="text/javascript">
$(function(){
	
});
var editCart=function(num,idx){
	//수정할 수량값 얻어오기
	var qty=$('#oqty'+idx).val();
	alert(qty);
	$('#editPnum').val(num);
	$('#editOqty').val(qty);
	$('#ef').submit();
}
var delCart=function(num){
	//delPnum 아이디 텍스트박스에 num값을 전달하세요
	var yn=confirm(num+"번 상품을 삭제하시겠습니까?");
	if(yn==true){
		$('#delPnum').val(num);
		$('#df').submit();
	}
}

</script>
