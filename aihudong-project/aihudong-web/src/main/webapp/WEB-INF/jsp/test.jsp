<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="common/include_css.jsp" />
<jsp:include page="common/include_js.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<button onclick="test()">aaa</button>
	<button onclick="test2()">bbb</button>
</body>
<script type="text/javascript">
	function test2(){
		var obj = {};
		 obj['user_cipher'] = "2f00b5421e66d0b223512f2ead170b86";
		 obj['name'] = "123";
		 obj['type'] = 3;
		 obj['encryption'] = 0;
	    
	    $.ajax({
			url : "http://www.buka.tv/room/add.do",
			type : "post",
			contentType: 'application/json', // 这句不加出现415错误:Unsupported Media Type
	        data: JSON.stringify(obj),
			success : function(data) {
				console.log(data);
				if (data.room_id!=null) {
					alert("操作成功！");
				} else {
					alert("操作失败");
				}
			}
		})
	}
	
	function test(){
		var obj = {};
	    obj['username'] = "1234";
	    obj['password'] = "1234" ;
		
		$.ajax({
			url : "/aihudong-web/front/userLogin",
			type : "post",
			contentType: 'application/json', // 这句不加出现415错误:Unsupported Media Type
	        data: JSON.stringify(obj),
			success : function(data) {
				if (data == 'success') {
					alert("操作成功！");
					
				} else {
					alert("操作失败");
				}
			}
		})
	}
</script>
</html>