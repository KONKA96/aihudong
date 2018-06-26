<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>消息管理</title>
    <jsp:include page="../common/include_css.jsp" />
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><small>消息管理</small></h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<div  class="dataTables_wrapper form-inline" role="grid">
                    		<!-- 查询条件 -->
	                    	<form method="post" id="searchForm" action="/aihudong-web/admin/showAllAdmin">
		                    	<div class="row">
		                    		<div class="col-sm-2">
		                    			<div class="dataTables_length">
		                    				<a href="/aihudong-web/message/toUpdateMessage" class="btn btn-primary ">推送新消息</a>
		                    			</div>
		                    		</div>
		                    		<div class="col-sm-10">
		                    			<%-- <c:if test="${admin.power==0 }">
			                    			<div class="input-group">
			                    				<select class="form-control" id="subjectSelected" name="power">
			                    					<option value="">--请选择--</option>
			                    					<option value="1" ${admin.power==1 ? 'selected' : '' }>一级管理员</option>
			                    					<option value="2" ${admin.power==2 ? 'selected' : '' }>操作管理员</option>
	                                    		</select>
			                    			</div>
		                    			</c:if> --%>
		                    			<div class="input-group" style="float:right;">
		                    				<!-- 真实姓名 -->
				                            <input type="text" name="truename" value="${admin.truename }" class="form-control" placeholder="关键字查找">
				                            <div class="input-group-btn">
				                                <input type="submit"  class="btn btn-primary" value="搜索">
				                            </div>
				                        </div>
		                    		</div>
		                    	</div>
	                    	</form>
                    		<!-- 查询条件结束 -->
                    		<%-- <c:if test="${admin.power==0 }">
	                    		<form method="POST"  enctype="multipart/form-data" id="form1" action="/aihudong-web/admin/uploadExcel">  
							        <table>  
							        <tr>  
							        	<td><label for="upfile"class="btn btn-success">Excel批量导入</label></td> 
							        	<td><input id="upfile" type="file" name="upfile" class="btn btn-info" style="display:none"></td>  
							            <td><input type="submit" value="提交" onclick="return checkData()" class="btn btn-primary"></td>  
							         </tr>  
							        </table>    
							    </form>
						    </c:if> --%>
	                        <table class="table table-striped table-bordered table-hover dataTables-example">
	                            <thead>
	                                <tr>
	                                	<th>计划名称</th>
	                                    <th>推送时间</th>
	                                    <th>计划内容</th>
	                                    <th>接收终端</th>
	                                    <th>计划状态</th>
	                                    <th>发布人</th>
	                                    <th>操作</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            	<c:forEach items="${messageList }" var="message">
	                            		<tr class="gradeA">
	                            			<td>${message.messageName }</td>
	                            			<td>${message.startTime }-${message.endTime }</td>
		                            		<td>详情</td>
		                            		<td>详情</td>
		                            		<td>
		                            		 	<c:if test="${message.messageState==0 }">未开始</c:if>
		                            		 	<c:if test="${message.messageState==1 }">进行中</c:if>
		                            		 	<c:if test="${message.messageState==2 }">已结束</c:if>
		                            		</td>
		                            		<td>${message.admin.truename }</td>
			                                 <td>
			                                 	<a href="/aihudong-web/message/toUpdateMessage?id=${message.id }"><i style="margin-left:5px;" class="fa fa-edit"></i></a>
			                                 	<a href="javascript:;" onclick="deleteMessage(${message.id })"><i style="margin-left:5px;" class="fa fa-trash"></i></a>
			                                 </td>
		                            	</tr>
		                            </c:forEach>
	                            </tbody>
	                        </table>
							<!-- 分页 -->
							<jsp:include page="../common/include_page.jsp">
 								<jsp:param value="/aihudong-web/admin/showAllAdmin" name="pageTitle"/>
							</jsp:include>
							<!-- 分页结束 -->
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../common/include_js.jsp" />
	<!-- 弹窗 -->
	<div id="diaLogDiv" style="display: none;">
		<div class="ibox-content">
			<div class="dataTables_wrapper form-inline" role="grid">
				<table
					class="table table-striped table-bordered table-hover dataTables-example">
					<thead>
						<tr>
							<th>院系ID</th>
							<th>院系名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="tbody">
					
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<!-- 弹窗 -->
	<div id="diaLogDivT" style="display: none;">
		<div class="ibox-content">
			<div class="dataTables_wrapper form-inline" role="grid">
				<table
					class="table table-striped table-bordered table-hover dataTables-example">
					<thead>
						<tr>
							<th>教学楼ID</th>
							<th>教学楼名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="tbodyT">
					
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

function dialogShow(id){
	 $("#tbody").empty();
	 $.ajax({
			url:"/aihudong-web/admin/getAllFacultyByAdmin",
			data:"id="+id,
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				 for(var i=0;i<data.length;i++){
					$("#tbody").append("<tr><td>"+data[i].id+"</td><td>"+data[i].facultyName+"</td><td><a href='javascript:;' onclick='deleteFaculty("+data[i].id+")''><i style='margin-left:5px;' class='fa fa-trash'></i></a></td></tr>");
				 } 
			}
		})
	
	
	layer.open({
		type : 1,
		skin : 'layui-layer-rim', //加上边框
		area : [ '880px', '540px' ], //宽高
		content : $("#diaLogDiv").html()//内容
	});
}
function deleteFaculty(id){
	var f=window.confirm("你确定要删除该项吗？");
	if(f){
		$.ajax({
			url:"/aihudong-web/faculty/deleteFacultyAdmin",
			data:"id="+id,
			type:"post",
			success:function(data){
				if(data=='success'){
					alert("操作成功！");
					window.location="/aihudong-web/admin/showAllAdmin";
				}else{
					alert("操作失败");
				}
			}
		})
	}
}

function dialogShowT(id){
	 $("#tbodyT").empty();
	 $.ajax({
			url:"/aihudong-web/admin/getAllSubjectByAdmin",
			data:"id="+id,
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				 for(var i=0;i<data.length;i++){ 
					$("#tbodyT").append("<tr><td>"+data[i].id+"</td><td>"+data[i].buildingName+"</td><td><a href='javascript:;' onclick='deleteBuilding("+data[i].id+")''><i style='margin-left:5px;' class='fa fa-trash'></i></a></td></tr>");
				} 
			}
		})
	
	
	layer.open({
		type : 1,
		skin : 'layui-layer-rim', //加上边框
		area : [ '880px', '540px' ], //宽高
		content : $("#diaLogDivT").html()//内容
	});
}

function deleteBuilding(id){
	var f=window.confirm("你确定要删除该项吗？");
	if(f){
		$.ajax({
			url:"/aihudong-web/building/updateBuildingAdmin",
			data:"id="+id,
			type:"post",
			success:function(data){
				if(data=='success'){
					alert("操作成功！");
					window.location="/aihudong-web/admin/showAllAdmin";
				}else{
					alert("操作失败");
				}
			}
		})
	}
}

//JS校验form表单信息  
function checkData(){  
   var fileDir = $("#upfile").val();  
   var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
   if("" == fileDir){  
       alert("选择需要导入的Excel文件！");  
       return false;  
   }  
   if(".xls" != suffix && ".xlsx" != suffix ){  
       alert("选择Excel格式的文件导入！");  
       return false;  
   }  
   return true;  
} 

function changeSubject(object){
	var index=object.value;
	var arrayId = new Array();
	var arrayName = new Array();
	<c:forEach items="${facultyList }" var="e">
		<c:forEach items="${e.subjectList }" var="subject"> 
			if(${subject.facultyId}==index){
				arrayName.push("${subject.subjectName}"); //js中可以使用此标签，将EL表达式中的值push到数组中
				console.log(${subject.id});
				arrayId.push(${subject.id});
			}
		</c:forEach>
	</c:forEach>
	$("#subjectSelected").empty();
	$("#subjectSelected").append("<option>--请选择--</option>");
	
	for(var i=0;i<arrayName.length;i++){
		$("#subjectSelected").append("<option value='"+arrayId[i]+"'>"+arrayName[i]+"</option>");
	}
}

	function deleteMessage(id){
		var f=window.confirm("你确定要删除这项吗？");
		if(f){
			$.ajax({
				url:"/aihudong-web/message/deleteMessage",
				data:"id="+id,
				type:"post",
				success:function(data){
					if(data=='success'){
						alert("操作成功！");
						window.location="/aihudong-web/admin/showAllMessage";
					}else{
						alert("操作失败");
					}
				}
			})
		}
		
	}
</script>
</html>
