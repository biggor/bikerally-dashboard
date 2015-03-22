<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js" type="text/javascript"></script>
<title>BikeRally - Dashboard</title>
<style type="text/css">
ul.list-centered {
	padding: 0;
	text-align: center;
}

ul.list-centered li {
	display: inline-block;
	padding-left: 5px;
	padding-right: 5px;
}

a.boxed-border {
	border-color: rgb(201, 201, 201);
	border-style: solid;
	border-width: 1px;
	box-shadow: rgb(255, 255, 255) 1px 1px 0px 0px inset, rgb(255, 255, 255) 0px 1px 1px 0px;
	float: left;
	width: 120px;
}

.number-1 {
	color: rgb(98, 174, 239);
	float: left;
	font-size: 24px;
	font-weight: bold;
	margin-top: 5px;
	text-align: center;
	width: 118px;
}

.number-2 {
	color: rgb(114, 177, 16);
	float: left;
	font-size: 24px;
	font-weight: bold;
	margin-top: 5px;
	text-align: center;
	width: 118px;
}

.number-3 {
	color: rgb(119, 119, 119);
	display: block;
	font-size: 36px;
	font-weight: bold;
	margin-top: 5px;
	text-align: center;
	width: 254px;
}

div.centered {
	margin: 0 auto;
	text-align: center;
	width: 256px;
	text-align: center;
}

.txt-1 {
	color: rgb(153, 153, 153);
	float: left;
	font-size: 12px;
	font-weight: normal;
	margin-bottom: 5px;
	text-align: center;
	width: 118px;
}

.txt-2 {
	color: rgb(153, 153, 153);
	display: block;
	font-size: 12px;
	font-weight: normal;
	margin-bottom: 5px;
	text-align: center;
	width: 254px;
}
</style>
</head>
<body>
	<section data-role="page">
		<header data-role="header">
			<h1>Dashboard</h1>
		</header>
		<article data-role="content" class="stats">
			<ul class="list-centered">
				<li><a href="#" class="boxed-border"> <span class="number-1">82</span> <span class="txt-1">riders</span></a></li>
				<li><a href="#" class="boxed-border"> <span class="number-2">9</span> <span class="txt-1">crew</span></a></li>
			</ul>
			<div class="centered">
				<span class="number-3">$8200</span> <span class="txt-2">funds raised</span><a></a>
			</div>
			<ul></ul>
		</article>
		<footer data-role="footer" data-position="fixed">
			<div data-role="navbar">
				<ul>
					<li><a href="riders.jsp">Riders</a></li>
					<li><a href="crew.jsp">Crew</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</footer>
	</section>
</body>
</html>