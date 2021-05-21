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
<ul class="layui-nav layui-bg-green">
    <li class="layui-nav-item layui-this"><a href="/static/main.jsp">数据准备</a></li>
    <li class="layui-nav-item"><a href="/static/prepara.jsp">用户管理</a></li>
    <li class="layui-nav-item"><a href="/static/feedback_main.jsp">反馈管理</a></li>
    <li class="layui-nav-item" style="margin-left: 65%">
        <a href="/static/main.jsp"><img src="//t.cn/RCzsdCq" class="layui-nav-img">${sessionScope.user.username}</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:layer.confirm('确实要退出登录吗?', function(index){location='/front/loginout';layer.close(index);});">退了</a>
    </li>
</ul>

<div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="margin-left: 250px;font-weight: bold;font-size: 20px;">数据准备</label>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">爬取数量:</label>
        <div class="layui-input-block">
            <input id="num" type="text" name="数量" placeholder="爬取数量" value="100" style="width: 500px" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">开始爬取:</label>
        <div class="layui-input-block">
            <button class="layui-btn" type="button" title="小麦" onclick="spiderA()">小麦</button>
            <button class="layui-btn" type="button" title="水稻" onclick="spiderB()">水稻</button>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">字典生成:</label>
        <div class="layui-input-block">
            <button class="layui-btn" type="button" title="生成字典" onclick="creatDic()">生成字典</button>
        </div>
    </div>

    <script>
        //注意：导航 依赖 element 模块，否则无法进行功能性操作
        layui.use('element', function () {
            var element = layui.element;

            //…
        });

        function spiderA() {
            if (!/^\d+$/.test($("#num").val())) {
                layer.alert('请输入正整数');
                return;
            }
            $.ajax({
                url: "/magic/spider/wheat?num=" + $("#num").val(),
                type: "get",
                error: function (err) {
                    console.log(err);
                },
                success: function (reply) {
                    console.log(reply);
                }
            });
        }

        function spiderB() {
            if (!/^\d+$/.test($("#num").val())) {
                layer.alert("请输入正整数");
                return;
            }
            $.ajax({
                url: "/magic/spider/rice?num=" + $("#num").val(),
                type: "get",
                error: function (err) {
                    console.log(err);
                },
                success: function (reply) {
                    console.log(reply);
                }
            });
        }

        function creatDic() {
            $.ajax({
                url: "/front/creatDic",
                type: "get",
                error: function (err) {
                    console.log(err);
                },
                success: function (reply) {
                    console.log(reply);
                }
            });
        }
    </script>


    <script type="text/javascript" src="../static/js/layui.js"></script>
    <script type="text/javascript" src="../static/js/jquery-1.12.4.min.js"></script>

</body>
