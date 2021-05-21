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
    <li class="layui-nav-item layui-this"><a href="/static/user.jsp">查询模块</a></li>
    <li class="layui-nav-item"><a href="/static/feedback_user.jsp">反馈模块</a></li>
    <li class="layui-nav-item"><a href="/static/personal_center.jsp">个人中心</a></li>
    <li class="layui-nav-item" style="margin-left: 65%">
        <div><a href="/static/user.jsp"><img src="//t.cn/RCzsdCq" class="layui-nav-img">${sessionScope.user.username}
        </div>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:layer.confirm('确实要退出登录吗?', function(index){location='/front/loginout';layer.close(index);});">退了</a>
    </li>
</ul>
<div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="margin-left: 250px;font-weight: bold;font-size: 20px;">问答模块</label>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">输入问题:</label>
        <div class="layui-input-inline">
            <input type="question" name="数量" placeholder="请输入问题" autocomplete="off" style="width: 400px"
                   class="layui-input">
        </div>
        <button class="layui-form-mid layui-btn" type="button" style="width: 100px;margin-left: 230px"  title="查询"
                onclick="participle()">查询
        </button>
    </div>
    <div class="layui-form-item">
        <div>
            <textarea class="layui-textarea" cols="20" rows="10" id="testArea"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="margin-left: 250px;font-weight: bold;font-size: 20px;">提示问句</label>
    </div>
    <div class="layui-form-item">
            <textarea class="layui-textarea" cols="20" rows="10" readonly="readonly"
                      id="testArea2"></textarea>
    </div>
</div>

<script type="text/javascript" src="../static/js/layui.js"></script>
<script type="text/javascript" src="../static/js/jquery-1.12.4.min.js"></script>

<script>
    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function () {
        var element = layui.element;

        //…
    });
    $("#testArea2").val("作物类型是小麦的有\n作物京作244是在哪里培育的\n产地为广东的作物有\n株高为110的作物有\n千粒重为38.0的作物有\n系谱为北京7号的作物有\n介绍下作物京作244\n什么是京作244");

    function participle() {
        $("#testArea").val("查询中,请等待...........");
        $.ajax({
            url: "/front/participle?question=" + encodeURI(encodeURI($("#question").val())),
            type: "get",
            error: function (err) {
                console.log(err);
            },
            success: function (reply) {
                $("#testArea").val(reply);
            }
        });
    }
</script>


</body>
