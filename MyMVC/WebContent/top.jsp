<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript"
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript"
	src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link href="mystyle.css" rel="stylesheet" type="text/css">

<style type="text/css">
	#myCar .carousel-inner{
		width:100%;
		max-height:400px;
		margin:auto;
	}
</style>
<% 
	String myctx=request.getContextPath();
	// "/MyMVC"
%>

</head>
<body>

	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#navbar-ex-collapse">
					<span class="sr-only">Toggle navigation</span><span
						class="icon-bar"></span><span class="icon-bar"></span><span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><span>Brand</span></a>
			</div>
			<div class="collapse navbar-collapse" id="navbar-ex-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="<%=myctx%>/index.do">Home</a></li>
					<c:if test="${loginUser eq null}">
						<li><a href="#" data-toggle="modal" data-target="#myModal">Login</a></li>
					</c:if>
					<c:if test="${loginUser ne null}">
						<li class="bg-warning"><a href="#">
							"<span style="color:red">${loginUser.name}</span>" 님 로그인중</a></li>
						<li><a href="<%=myctx%>/logout.do">Logout</a></li>
					</c:if>
					<li><a href="<%=myctx%>/memoWrite.do#chap01">Memo글쓰기</a></li>					
				    <li><a href="<%=myctx%>/memoList.do#memoTab">Memo목록</a></li>					
					<li><a href="<%=myctx%>/board-list.do#bbs">Board</a></li>
					<li><a href="<%=myctx%>/cartList.do#shop">장바구니</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="text-center">Search</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-offset-3 col-md-6">
					<form role="form" action="pdSearch.do">
						<div class="form-group">
							<div class="input-group">
								<input type="text" name="pname" id="pname" class="form-control"
									placeholder="Enter Product Name"> <span
									class="input-group-btn"> <a class="btn btn-success"
									type="submit">Go</a>
								</span>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="myCar" data-interval="false"
		class="carousel slide" data-ride="carousel">
		<div class="carousel-inner">
			<div class="item active">
				<img
					src="https://unsplash.imgix.net/uploads/14129863345840df499fc/0165574c?w=1024&amp;q=50&amp;fm=jpg&amp;s=6e5127e797feb9b5a72f7ae4a0f3a89b">
				<div class="carousel-caption">
					<h2>Title</h2>
					<p>Description</p>
				</div>
			</div>
		</div>
		<a class="left carousel-control" href="#myCar"
			data-slide="prev"><i class="icon-prev fa fa-angle-left"></i></a><a
			class="right carousel-control" href="#myCar"
			data-slide="next"><i class="icon-next fa fa-angle-right"></i></a>
	</div>
	<!--Include loginModal Page source--><!-- jsp는 실행결과를 include는 소스를 포함시키는일 -->
	<%@include file="/shop/loginModal.jsp" %>
	<!-- ============================ -->
	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-2">
				<!-- -------카테고리 목록  --------- -->
				<ul class="list-group">
					<li class="list-group-item list-group-item-warning"><a href="<%=myctx%>/index.do">
					CATEGORY</a></li>
					<c:forEach var="cg" items="${categoryList}">
						<li  class="list-group-item">
						<a href="<%=myctx%>/mallByCate.do?code=${cg.code}&cname=${cg.cname}#shop">
						${cg.cname}</a></li>
					</c:forEach>
				</ul>
				<!-- --------------------------- -->
				<ul class="list-group hidden-xs">
					<li class="list-group-item list-group-item-success"><a href="<%=myctx%>/testMyBatis.do">MyBatis Test</a></li>
					<li  class="list-group-item disabled"><a href="<%=myctx%>/board-list.do#bbs">Board List</a></li>
					<li  class="list-group-item"><a href="<%=myctx%>/board-write.do#bbs">Board Write</a></li>
				</ul>
				
				</div>
				<div class="col-md-10">
				
				
				
				
				
				
				
				
				
				