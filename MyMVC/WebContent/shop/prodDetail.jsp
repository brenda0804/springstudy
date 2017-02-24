<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/top.jsp"/>
<script type="text/javascript" src="./js/jquery.magnifier.js"></script>
<div align="center" id="shop">
	<h1>상품 상세 정보</h1>
	<div class="section">
	   <div class="container-fluid">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<table class="table table-responsive">
					<tr>
						<td class="col-md-5" align="center">
							<a href="#shop"><img class="magnify img img-responsive" data-magnifyby="1.5"
							 src="images/${item.pimage1}"></div></a>
						</td>
						<td>
							<h4><span class="label label-danger">${item.pspec}</span></h4>
							상품번호: <b>${item.pnum}</b><br>
							상품이름: <b>${item.pname}</b><br>
							정    가: <del><b>
								<fmt:formatNumber value="${item.price}" pattern="###,###"/>
 							</b></del> 원<br>
							판 매 가: <span>
								<b><fmt:formatNumber value="${item.saleprice}" pattern="###,###"/></b>
							</span> 원<br>
							할 인 율: <span style="color:blue"><b>${item.percent}%</b></span><br>
							POINT : <span class="label label-success">${item.point}Point</span>
							<p></p>
							<!-- form 시작--------------------------- -->
							<form name="frm" id="frm" method="GET">
								<label for="oqty">상품갯수</label>
								<!-- 상품번호를 hidden으로 넘긴다. -->
								<input type="hidden" name="pnum" value="${item.pnum}">
								<input type="number" name="oqty" id="oqty" min="1" max="50" value="1">
								<!-- 원래는 재고 수량만큼 줘야한다. -->
								
							</form>
							<!-- ----------------------------------- -->
							<button class="btn btn-primary" onclick="goCart()">장바구니</button>
							<button class="btn btn-warning" onclick="goOrder()">주문하기</button>
							<button class="btn btn-success" onclick="goWish()">위시리스트</button>
							
						</td>
					</tr>
					<tr>
						<td align="center">
							<img src="images/${item.pimage2}" class="img img-responsive">
						</td>
						<td align="center">
							<img src="images/${item.pimage3}" class="img img-responsive">
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<p>상품설명</p>
							<pre>${item.pcontents}</pre><!-- 소스보기태그 -->
						</td>
					</tr>
				</table>
			</div>			
		  </div>
		</div>
	</div>
</div>
<script type="text/javascript">
//
	var goCart=function(){
		frm.action="cartAdd.do";
		//frm.metho="POST";
		frm.submit();
	}
	var goOrder=function(){
		frm.action="order.do";
		//frm.metho="POST";
		frm.submit();
	}
	var goWish=function(){
		frm.action="wish.do";
		frm.submit();
	}
//팝업창으로 상세 이미지를 보여주는 함수
	var openWin=function(img){
		//alert(img);
		var url="images/"+img;
		//var url="images/"+img+"Lager";큰이미지를 준비했을 경우
		
		var imgObj=new Image();//javascript의 Image객체가 있다.
		imgObj.src=url;//src속성이 있는데, url경로로 속성값을 준다.
		var w=imgObj.width+80;
		var h=imgObj.height+80;
		
		//팝업창의 폭=(원래 이미지 폭+80)px
		//팝업창의 높이=(원래 이미지 높이+80)px
		window.open(url,"imgView","width="+w+"px, height="+h+"px , left=100, top=100");//image url, name, 속성(폭,높이,x,y좌표)
	}

</script>

<jsp:include page="/foot.jsp"/>