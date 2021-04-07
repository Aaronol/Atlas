<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <title>Hello world</title>
    <script type="text/javascript" src="../../static/jquery-1.12.4.min.js"></script>
</head>
<body>
<div>
    <div style="font-weight: bold;font-size: 15px">数据准备</div>
    <input id="num" type="text" placeholder="爬取数量" name="数量" value="100">
    <button type="button" title="小麦" onclick="spiderA()">小麦</button>
    <button type="button" title="水稻" onclick="spiderB()">水稻</button>
    <span style="margin-left: 50">
    <button type="button" title="生成字典" onclick="creatDic()">生成字典</button>
    </span>
</div>
<div>
    <div style="font-weight: bold;font-size: 15px">问答模块</div>
    <input id="question" type="text" placeholder="请输入问题">
    <span style="margin-left: 20px">
    <button type="button" title="查询" onclick="participle()">查询</button>
    </span>
    <div>
        <textarea cols="43" rows="20" id="testArea"></textarea>
    </div>
</div>
<div>
    <div style="font-weight: bold;font-size: 15px">提示问句</div>
    <textarea cols="43" rows="20" readonly="readonly"
              id="testArea2"></textarea>
</div>


</body>
<script>
    $("#testArea2").val("作物类型是小麦的有\n作物京作244是在哪里培育的\n产地为广东的作物有\n株高为110的作物有\n千粒重为38.0的作物有\n系谱为北京7号的作物有\n介绍下作物京作244\n什么是京作244");
    function spiderA() {
        if (!/^\d+$/.test($("#num").val())) {
            alert("请输入正整数");
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
            alert("请输入正整数");
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
</html>
