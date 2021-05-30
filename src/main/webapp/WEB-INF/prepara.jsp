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
<ul class="layui-nav layui-bg-green">
    <li class="layui-nav-item"><a href="/front/main">数据准备</a></li>
    <li class="layui-nav-item layui-this"><a href="/front/prepara">用户管理</a></li>
    <li class="layui-nav-item"><a href="/front/main/feedback">反馈管理</a></li>
    <li class="layui-nav-item" style="margin-left: 65%">
        <a href="/front/main"><img src="//t.cn/RCzsdCq" class="layui-nav-img">${sessionScope.user.username}</a>
    </li>
    <li class="layui-nav-item">
        <a href="javascript:layer.confirm('确实要退出登录吗?', function(index){location='/front/loginout';layer.close(index);});">退了</a>
    </li>
</ul>

<div class="layui-col-md4">
    <div class="layui-card">
        <div class="main-panel layui-card-header" style="font-weight: bold;font-size: 20px;margin-left: 214px">用户管理
        </div>
        <div class="layui-card-body">
            <label class="layui-form-label" style="text-align: left;width: 60px">用户姓名:</label>
            <input type="text" id="searchValue" name="title" style="width: 120px;display: inline" placeholder="姓名"
                   autocomplete="off"
                   class="layui-input">
            <button type="button" class="layui-btn layui-btn-sm" onclick="reLoadPage()">查询</button>
            <button type="button" class="layui-btn layui-btn-normal layui-btn-lg"
                    style="margin-left: 65px;margin-top: 5px" onclick="newFile()">新建档案
            </button>
        </div>
    </div>
    <table id="userdemo" lay-filter="userRow"></table>
</div>
<div class="layui-col-md4">
    <div style="font-weight: bold;font-size: 20px;margin-left: 150px">用户信息</div>
    <div class="layui-panel">
        <div style="padding: 50px 30px;height: 455px">
            <div class="layui-card">
                <div class="layui-card-body">
                    <div class="layui-form">
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="text-align: left;width: 100px">编码:</label>
                            <input id="editCode" type="text" name="title" style="width: 200px" readonly="readonly"
                                   placeholder="编码"
                                   autocomplete="off"
                                   class="layui-input">
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="text-align: left;width: 100px">用户名:</label>
                            <input id="editName" type="text" name="title" style="width: 200px" placeholder="用户名"
                                   autocomplete="off"
                                   class="layui-input">
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="text-align: left;width: 100px">密码:</label>
                            <input id="password" type="text" name="title" style="width: 200px" placeholder="密码"
                                   autocomplete="off"
                                   class="layui-input">
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="text-align: left;width: 100px">性别:</label>
                            <div class="layui-input-inline" style="width:200px;">
                                <select id="editSex" name="city">
                                    <option value="0">男</option>
                                    <option value="1">女</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="text-align: left;width: 100px">年龄:</label>
                            <input id="editAge" type="text" name="title" style="width: 200px" placeholder="年龄"
                                   autocomplete="off"
                                   class="layui-input">
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label" style="text-align: left;width: 100px">电话:</label>
                            <input id="editPhoneno" type="text" name="title" style="width: 200px" placeholder="电话"
                                   autocomplete="off"
                                   class="layui-input">
                        </div>
                        <div class="layui-form-item">
                            <button type="button" style="margin-left: 60px" class="layui-btn layui-btn-normal"
                                    onclick="saveUser()"
                            >保存
                            </button>
                            <button type="button" style="margin-left: 60px" class="layui-btn layui-btn-primary"
                                    onclick="clearBar()"
                            >清空
                            </button>
                            <button type="button" style="margin-left: 60px" class="layui-btn layui-btn-danger"
                                    onclick="deleteUser()"
                            >删除
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var userTable;
    layui.use('table', function () {
        userTable = layui.table;

        //第一个实例
        userTable.render({
            id: 'userTable',
            where: {roleid: '1', patient: $("#searchValue").val()},
            elem: '#userdemo'
            , height: 450
            , url: '/front/getAllUser' //数据接口
            , page: true //开启分页
            , cols: [[ //表头
                {field: 'id', title: 'ID', width: 50, fixed: 'left'}
                , {field: 'username', title: '姓名', width: 120}
                , {field: 'sex', title: '性别', width: 60}
                , {field: 'age', title: '年龄'}
            ]]
        });

        userTable.on('row(userRow)', function (obj) {
            $(".layui-table-body tr").attr({"style": "background:#FFFFFF; color:#666666"});//其他tr恢复颜色
            $(".layui-table-body tr[data-index=" + $(this).attr('data-index') + "]").attr({"style": "background:#b3aeae;color:#666666"});//改变当前tr颜色
            $("#editCode").val(obj.data.id);
            $("#editName").val(obj.data.username);
            $("#password").val(obj.data.password);
            $('#editSex').siblings("div.layui-form-select").find('dl').find('dd[lay-value=' + (obj.data.sex == '男' ? 0 : 1) + ']').click();
            $("#editAge").val(obj.data.age);
            $("#editPhoneno").val(obj.data.phoneno);
        });

    });

    function saveUser() {
        if (!$("#editName").val()) {
            layer.alert('请填写要保存的用户姓名');
            return;
        }
        if (!$("#password").val()) {
            layer.alert('请填写要保存的用户密码');
            return;
        }
        if (!$("#editSex  option:selected").text()) {
            layer.alert('请填写要保存的用户性别');
            return;
        }
        if (!$("#editAge").val()) {
            layer.alert('请填写要保存的用户年龄');
            return;
        }
        if (!$("#editPhoneno").val()) {
            layer.alert('请填写要保存的用户电话');
            return;
        }
        $.ajax({
            async: false,//同步
            cache: false,
            url: "/front/addOrUpdateUser",
            type: "post",
            datatype: 'json',
            contentType: 'application/json',
            data: JSON2.stringify({
                id: $("#editCode").val(),
                username: $("#editName").val(),
                password: $("#password").val(),
                sex: $("#editSex  option:selected").text(),
                age: $("#editAge").val(),
                phoneno: $("#editPhoneno").val(),
                roleid: '1'
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

    function newFile() {
        $(".layui-table-body tr").attr({"style": "background:#FFFFFF; color:#666666"});//其他tr恢复颜色
        $("#editCode").val(null);
        clearBar();
    }

    function clearBar() {
        $("#editName").val('');
        $("#password").val('');
        $('#editSex').siblings("div.layui-form-select").find('dl').find('dd[lay-value=0]').click();
        $("#editAge").val('');
        $("#editPhoneno").val('');
    }

    function deleteUser() {
        if (!$("#editCode").val()) {
            layer.alert('请先在左边选中要删除的用户');
            return;
        }
        $.ajax({
            async: false,//同步
            cache: false,
            url: "/front/deleteUser",
            type: "post",
            datatype: 'json',
            contentType: 'application/json',
            data: JSON2.stringify({
                id: $("#editCode").val(),
                username: $("#editName").val(),
                password: $("#password").val(),
                sex: $("#editSex  option:selected").text(),
                age: $("#editAge").val(),
                phoneno: $("#editPhoneno").val()
            }),
            error: function () {
                layer.alert('删除失败!请检查网络并重试!');
            },
            success: function () {
                layer.alert('删除成功!', {}, function (index) {
                    reLoadPage();
                    layer.close(index);
                });
            }
        });
    }

    function reLoadPage() {
        userTable.reload("userTable", {where: {roleid: '1', username: $("#searchValue").val()}});
        clearBar();
    }


</script>

</body>
