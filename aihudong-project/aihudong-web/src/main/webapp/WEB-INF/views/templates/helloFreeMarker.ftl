<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>jQuery无限循环图片轮播代码 - 站长素材</title>
<link type="text/css" rel="stylesheet" href="/aihudong-web/resources/css/lunbotu.css">

<script src="/aihudong-web/resources/js/jquery.min.js"></script>
<script src="/aihudong-web/resources/js/lunbotu.js?v=0.0.1"></script>

</head>
<body>

<div class="box">
	<!-- 存放大图的容器-->
	<div class="all">
		<div class="top-img">
			<div class="activeimg">
				<#list picStringList as pic>
				<img src="/aihudong-web/upload/${pic}">
				</#list>
			</div>
			<div class="left"><img src="/aihudong-web/resources/img/left.png"> </div>
			<div class="right"><img src="/aihudong-web/resources/img/right.png"></div>
		</div>
		<!-- 存放缩略图的容器-->
		<div class="bot-img">
			<ul>
				<#list picStringList as pic>
				<li class="active"><img src="/aihudong-web/upload/${pic}"></li>
				</#list>
			</ul>
		</div>
	</div>
</div>

</body>
</html>