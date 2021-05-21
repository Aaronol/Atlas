<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>作物图谱查询系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" type="text/css" href="../static/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">
    <link rel="stylesheet" type="text/css" href="../static/css/modules/code.css">
    <link rel="stylesheet" type="text/css" href="../static/css/modules/laydate/default/laydate.css">
    <link rel="stylesheet" type="text/css" href="../static/css/modules/layer/default/layer.css">
</head>
<body>
<ul class="layui-nav layui-bg-cyan">
    <li class="layui-nav-item"><a href="/static/user.jsp">查询模块</a></li>
    <li class="layui-nav-item layui-this"><a href="/static/feedback_user.jsp">反馈模块</a></li>
    <li class="layui-nav-item"><a href="/static/personal_center.jsp">个人中心</a></li>
    <li class="layui-nav-item" style="margin-left: 65%">
        <div><a href="/static/user.jsp"><img src="//t.cn/RCzsdCq" class="layui-nav-img">${sessionScope.user.username}</div>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:layer.confirm('确实要退出登录吗?', function(index){location='/front/loginout';layer.close(index);});">退了</a>
    </li>
</ul>
<div>
    反馈
</div>

<script>
    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function () {
        var element = layui.element;

        //…
    });
</script>


<script type="text/javascript" src="../static/js/layui.js"></script>
<script type="text/javascript" src="../static/js/jquery-1.12.4.min.js"></script>

</body>
