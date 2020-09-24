<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>您好Springboot</title>
	<!-- 引入JS函数类库 -->
	<script type="text/javascript" src="/js/jquery-3.4.1.min.js"></script>
	<script type="text/javascript">
		//让页面加载完成之后执行
		$(function () {
			//1.$.get("url地址","添加参数","回调函数","返回值结果类型 text/html/json....一般ajax会自动匹配.");
			var trs=null;
			$.get("/findAjax",function (data) {
				//需求: 将userList集合信息动态的添加到table中.
				//data=[{user},{user},{user}]  这里不要使用ECMAS6，因为可能会与jsp产生冲突
				$(data).each(function (index) { //index代表循环遍历的下标从0开始
					var user=data[index];
					var id=user.id;
					var name=user.name;
					var age=user.age;
					var sex=user.sex;
					//最终需要在table中展现
					trs+="<tr align='center'><td>"+id+"</td><td>"+name+"</td><td>"+age+"</td><td>"+sex+"</td><tr>";
				});
				//将结果追加到table中进行呈现
				$("#tb1").append(trs);
			});


			//2.$.post();
			var posturl="http://localhost:8090/findAjax3";
			var data="id=1&name=tomcat";
			$.post(posturl,data,function (data) {
				if(data.status==200){
					alert("恭喜你新增用户成功!");
				}else{
					alert("新增失败!");
				}
			})

			//3.$.getJSON();
			var geturl="http://localhost:8090/findAjax4";
			var data={"id":"1","name":"tomcat"};
			//发起get请求获取json数据
			$.getJSON(geturl,data,function (data) {
				if(data.status==200){
					alert("恭喜你新增用户成功!!!");
				}else{
					alert("新增失败!!!!");
				}

			})


			//4.$.getScript();

			//5.$.ajax();  说明
			$.ajax({
				type:"get",
				url:"/findAjax2",
				//data:{"id":"1","name":"tomcat"},
				data:"id=1&name=tomcat",
				success:function (data) {
					alert("ajax调用成功！");
					alert(data);
				},
				async:true, //默认都是true，表示异步调用
				error:function (data) {
					alert("服务器异常,请稍后重试!!!!");
					alert(data);
				}
			});

		})






	</script>

</head>
<body>
<table id="tb1" border="1px" width="65%" align="center">
	<tr>
		<td colspan="6" align="center"><h3>学生信息</h3></td>
	</tr>
	<tr>
		<th>编号</th>
		<th>姓名</th>
		<th>年龄</th>
		<th>性别</th>
		<th></th>
	</tr>

	<c:forEach items="${userList}" var="u">
		<tr>
			<th>${u.id}</th>
			<th>${u.name}</th>
			<th>${u.age}</th>
			<th>${u.sex}</th>
		</tr>
	</c:forEach>
</table>
</body>
</html>