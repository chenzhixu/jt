<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <!-- 引入JS函数类库 -->
    <script type="text/javascript" src="/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        //让页面加载完成之后执行
        $(function(){
            //1.$.get("url地址","添加参数","回调函数","返回值结果类型 text/html/json....一般ajax会自动匹配.");  为jquery中的一个ajax函数
            $.get("/findAjax",function(data){
                //data = [{user},{user},{user}]  es6~jsp中冲突
                //需求: 将userList集合信息动态的添加到table中.
                var trs = null;
                $(data).each(function(index){
                    //index代表循环遍历的下标从0开始
                    var user = data[index];
                    var id = user.id;
                    var name = user.name;
                    var age = user.age;
                    var sex = user.sex;
                    //最终需要在table中展现
                    trs += "<tr align='center'><td>"+id+"</td><td>"+name+"</td><td>"+age+"</td><td>"+sex+"</td></tr>"
                });
                //将结果追加到table中即可.
                $("#tab1").append(trs);
            });

            //2.$.post();  向服务端发送Ajax Post 请求
            //定义请求url
            var url="/doAjaxPost";
            //.定义请求参数
            var params={"id":"1","name":"tomcat"};
            //post(url[,params][,callback][,dataType])为jquery中的一个ajax函数
            $.post(url,params,function (result) {  //post请求一般用于向服务端提交数据
                alert("ajax调用成功");
                alert(result);
            })


            //3.$.getJSON();
            //4.$.getScript();
            //5.$.ajax();  说明
            var params="id=1&name=tomcat";
            var url="/findAjax2";
            $.ajax({
                type:"get", //表示get请求(默认为get),省略不写也为Get请求
                url:url, //":"左边的内容为语法定义,我们不能修改.":"右边为我们自己定义
                //data:{"id":"1","name":"tomcat"},
                data:params,  //请求参数
                success:function(data){ //成功回调函数
                  alert("ajax调用成功");
                  alert(data);
                },
                async:true,//默认都是true  表示异步调用
                error:function (data) {
                    alert("服务器异常,请稍后重试!!!!");
                }


            });


        })








    </script>


    <title>您好Springboot</title>
</head>
<body>
<table id="tab1"  border="1px" width="65%" align="center">
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
</table>
</body>
</html>