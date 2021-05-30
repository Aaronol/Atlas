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
    <link rel="stylesheet" type="text/css" href="../../static/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../../static/css/common.css">
    <link rel="stylesheet" type="text/css" href="../../static/css/modules/code.css">
    <link rel="stylesheet" type="text/css" href="../../static/css/modules/laydate/default/laydate.css">
    <link rel="stylesheet" type="text/css" href="../../static/css/modules/layer/default/layer.css">

    <script type="text/javascript" src="../../static/js/layui.js"></script>
    <script type="text/javascript" src="../../static/js/json2.js"></script>
    <script type="text/javascript" src="../../static/js/jquery-1.12.4.min.js"></script>
</head>
<body>
<ul class="layui-nav layui-bg-cyan">
    <li class="layui-nav-item"><a href="/front/user">查询模块</a></li>
    <li class="layui-nav-item layui-this"><a href="/front/user/feedback">反馈模块</a></li>
    <li class="layui-nav-item"><a href="/front/personal/center">个人中心</a></li>
    <li class="layui-nav-item" style="margin-left: 65%">
        <div><a href="/front/user"><img src="//t.cn/RCzsdCq" class="layui-nav-img">${sessionScope.user.username}
        </div>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:layer.confirm('确实要退出登录吗?', function(index){location='/front/loginout';layer.close(index);});">退了</a>
    </li>
</ul>
<div class="layui-col-md4">
    <div class="layui-card">
        <div class="main-panel layui-card-header" style="font-weight: bold;font-size: 20px;margin-left: 214px">我的反馈</div>
        <div class="layui-card-body">
            <button type="button" class="layui-btn layui-btn-normal"
                    style="margin-left: 214px;margin-top: 5px" onclick="newFeedback()">新增反馈
            </button>
        </div>
    </div>
    <table id="feedbackDemo" lay-filter="feedbackRow"></table>
</div>
<div class="layui-col-md4">
    <div class="layui-card">
        <div class="layui-card-body">
            <div class="layui-form">
                <div class="layui-form-item">
                    <div class="layui-form-item" style="display: none">
                        <label class="layui-form-label" style="text-align: left;width: 100px">编码:</label>
                        <input id="feedbackCode" type="text" name="title" style="width: 200px" readonly="readonly"
                               placeholder="编码"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                    <div>
                        <textarea class="layui-textarea" cols="20" rows="30" placeholder="请简要描述您的问题和意见,以便我们提供更好的帮助。"
                                  id="feedbackContent"></textarea>
                    </div>
                </div>

                <div class="layui-form-item">
                    <button type="button" style="margin-left: 350px" class="layui-btn layui-btn-lg"
                            onclick="saveFeedback()"
                    >提交
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var feedbackTable;
    layui.use('table', function () {
        feedbackTable = layui.table;

        //第一个实例
        feedbackTable.render({
            id: 'feedbackTable',
            where: {id: ${sessionScope.user.id}},
            elem: '#feedbackDemo'
            , height: 578
            , url: '/front/getAllFeedBack' //数据接口
            , page: true //开启分页
            , cols: [[ //表头
                {
                    field: 'updatedate',
                    title: '反馈时间',
                    width: 180,
                    templet: "<div>{{layui.util.toDateString(d.updatedate, 'yyyy-MM-dd HH:mm:ss')}}</div>"
                }
                , {field: 'content', title: '反馈内容'}
            ]]
        });

        feedbackTable.on('row(feedbackRow)', function (obj) {
            $(".layui-table-body tr").attr({"style": "background:#FFFFFF; color:#666666"});//其他tr恢复颜色
            $(".layui-table-body tr[data-index=" + $(this).attr('data-index') + "]").attr({"style": "background:#b3aeae;color:#666666"});//改变当前tr颜色
            $("#feedbackCode").val(obj.data.id);
            $("#feedbackContent").val(obj.data.content);
        });

    });

    function newFeedback() {
        $(".layui-table-body tr").attr({"style": "background:#FFFFFF; color:#666666"});//其他tr恢复颜色
        $("#feedbackCode").val(null);
        $("#feedbackContent").val('');
    }

    function saveFeedback() {
        if (!$("#feedbackContent").val()) {
            layer.alert('要反馈的信息不能为空');
            return;
        }
        $.ajax({
            async: false,//同步
            cache: false,
            url: "/front/saveFeedBack",
            type: "post",
            datatype: 'json',
            contentType: 'application/json',
            data: JSON2.stringify({
                content: $("#feedbackContent").val()
            }),
            error: function () {
                layer.alert('保存失败!请检查网络并重试!');
            },
            success: function () {
                layer.alert('保存成功!', {}, function (index) {
                    reLoadPage();
                    layer.close(index);
                });
            }
        });
    }

    function reLoadPage() {
        feedbackTable.reload("feedbackTable", {id: ${sessionScope.user.id}});
        newFeedback();
    }
</script>


</body>
