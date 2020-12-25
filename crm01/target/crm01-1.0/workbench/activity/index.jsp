<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">

        $(function () {
            $("#addBtn").click(function () {
                    $(".time").datetimepicker({
                        minView: "month",
                        language: 'zh-CN',
                        format: 'yyyy-mm-dd',
                        autoclose: true,
                        todayBtn: true,
                        pickerPosition: "bottom-left"
                    });





                    $.ajax({
                        url: "workbench/activity/getUserList.do",
                        type: "get",
                        dataType: "json",
                        success: function (data) {


                            var html = "<option></option>";

                            $.each(data, function (i, n) {
                                html += "<option value='" + n.id + "'>" + n.name + "</option>";
                            })

                            $("#create-owner").html(html);

                            var id = "${user.id}";
                            $("#create-owner").val(id);

                            //所有下拉条处理完之后，就可以打开模态窗口勒
                            $("#createActivityModal").modal("show");

                        }

                    })
                }
            )


            $("#editBtn").click(function () {

                var $xz = $("input[name=xz]:checked");

                if ($xz.length == 0) {
                    alert("请选择记录进行修改");
                } else if ($xz.length > 1) {
                    alert("只能选择一条记录进行修改！");
                } else {
                    //修改操作
                    var id = $xz.val();

                    $.ajax({
                        url: "workbench/activity/getUserListAndActivity.do",
                        data: {
                            "id": id
                        },
                        type: "get",
                        dataType: "json",
                        success: function (data) {
                            /*
                            data{
                            {"uList":[{用户1},{用户2}] ,"a":{活动}}
                            }*/

                            var html = "<option></option>";

                            $.each(data.uList, function (i, n) {
                                html += "<option value= '" + n.id + "'>" + n.name + "</option>";
                            });

                            //这一步操作是获取下拉条的名单
                            $("#edit-owner").html(html);

                            //处理单条activity
                            $("#edit-name").val(data.a.name);
                            $("#edit-id").val(data.a.id);
                            $("#edit-owner").val(data.a.owner);
                            $("#edit-cost").val(data.a.cost);
                            $("#edit-startDate").val(data.a.startDate);
                            $("#edit-endDate").val(data.a.endDate);
                            $("#edit-description").val(data.a.description);

                            //所有修改之后，就可以打开模态窗口了
                            $("#editActivityModal").modal("show");


                        }

                    })

                }


            });
            //实际项目中，先做添加，再做修改

            $("#updateBtn").click(function () {
                $.ajax({
                    url: "workbench/activity/update.do",
                    data: {
                        "id": $.trim($("#edit-id").val()),
                        "owner": $.trim($("#edit-owner").val()),
                        "name": $.trim($("#edit-name").val()),
                        "startDate": $.trim($("#edit-startDate").val()),
                        "endDate": $.trim($("#edit-endDate").val()),
                        "cost": $.trim($("#edit-cost").val()),
                        "description": $.trim($("#edit-description").val())

                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        /*data{
                            success:true/false
                        }*/

                        if (data.success) {

                            //修改成功后，就局部刷新
                            pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                                , $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

                            //关闭添加操作的模态窗口
                            $("#editActivityModal").modal("hide");
                        } else {
                            alert("修改失败")
                        }

                    }

                })

            });


            $("#saveBtn").click(function () {

                $.ajax({
                    url: "workbench/activity/save.do",
                    data: {
                        "owner": $.trim($("#create-owner").val()),
                        "name": $.trim($("#create-name").val()),
                        "startDate": $.trim($("#create-startDate").val()),
                        "endDate": $.trim($("#create-endDate").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-description").val())

                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        /*data{
                            success:true/false
                        }*/

                        if (data.success) {


                            //添加成功后，就局部刷新

                            //现有的技术
                            //直接使用就行
                            pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));


                            //清空数据
                            //拿到jquery的表单对象，但是没有reset无效
                            //很坑
                            //所以将jquery对象转化为dom对象
                            //原生的js为我们提供了这个
                            $("#create-form")[0].reset();


                            //关闭添加操作的模态窗口
                            $("#createActivityModal").modal("hide");
                        } else {
                            alert("添加失败")
                        }

                    }

                })


            })

            //局部刷新
            //默认展开页表的第一页，每一页两条记录
            pageList(1, 2);
            $("#searchBtn").click(function () {

                $("#hidden-name").val($.trim($("#search-name").val()));
                $("#hidden-owner").val($.trim($("#search-owner").val()));
                $("#hidden-startDate").val($.trim($("#search-startDate").val()));
                $("#hidden-endDate").val($.trim($("#search-endDate").val()));

                pageList(1, 2);

            })

            //勾选了全选，则所有的选项都自动选中
            $("#qx").click(function () {

                $("input[name=xz]").prop("checked", this.checked);

            });


            //动态生成得元素，不能以普通绑定事件进行操作得
            /*

                动态生成得元素，要不停找到外层正常的元素去进行绑定

                    下面这种方式才是有效的
                    固定语法：
                        $(需要绑定的外层元素).on("绑定事件的方式",需要绑定的jquery元素,回调函数);
            */


            $("#activityBody").on("click", $("input[name=xz]"), function () {
                $("#qx").prop("checked", $("input[name=xz]").length == $("input[name=xz]:checked").length);
            })

            $("#deleteBtn").click(function () {

                var $xz = $("input[name=xz]:checked");

                if ($xz.length == 0) {
                    alert("请选择需要删除的记录");
                } else {
                    //执行删除操作

                    //拼接参数

                    //可能是一条，可能是多条
                    var param = "";
                    //将每个dom循环取出来
                    for (var i = 0; i < $xz.length; i++) {
                        param += "id=" + $($xz[i]).val();
                        //如果不是最后一个元素，需要追加一个&

                        if (i < $xz.length - 1) {
                            param += "&";
                        }
                    }



                    $.ajax({
                        url: "workbench/activity/delete.do",
                        data: param,
                        type: "post",
                        dataType: "json",
                        success: function (data) {
                            if (data.success) {

                                pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                            } else {
                                //删除失败
                                alert("删除失败")
                            }
                        }

                    })
                }
            })


        });


        /*

           对于所有前端的页面
           分页函数，必须带两个参数
           一个pageNo：当前页码
           一个pageSize:每页展现的记录数


        */
        function pageList(pageNo, pageSize) {

            //将全选框干掉
            $("#qx").prop("checked", false);

            //将隐藏域得信息，重新赋值到我们得查询里面

            $("#search-name").val($.trim($("#hidden-name").val()));
            $("#search-owner").val($.trim($("#hidden-owner").val()));
            $("#search-startDate").val($.trim($("#hidden-startDate").val()));
            $("#search-endDate").val($.trim($("#hidden-endDate").val()));


            $.ajax({
                url: "workbench/activity/pageList.do",
                data: {

                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "name": $.trim($("#search-name").val()),
                    "owner": $.trim($("#search-owner").val()),
                    "startDate": $.trim($("#search-startDate").val()),
                    "endDate": $.trim($("#search-endDate").val()),

                },
                type: "get",
                dataType: "json",
                success: function (data) {
                    /*
                    查询市场活动信息列表

                    我们需要的是：
                    1. 市场活动信息列表  List<Activity> aList
                    2. total。信息总条数 : long total

                    打包起来
                    */

                    var html = "";

                    //每一个n，就是每一个市场活动对象
                    $.each(data.dataList, function (i, n) {

                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" name="xz" value="' + n.id + '"/></td>';
                        /*需要转义*/
                        //    \' :这就是转义
                        //<td><a style="text-decoration: none; cursor: pointer;"onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                        html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">' + n.name + '</a></td>';
                        html += '<td>' + n.owner + '</td>';
                        html += '<td>' + n.startDate + '</td>';
                        html += '<td>' + n.endDate + '</td>';
                        html += '</tr>'
                    });

                    $("#activityBody").html(html);

                    //计算总页数
                    var totalPages = data.total % totalPages == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1;

                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        //该回调函数，在点击分页组件之后触碰
                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });
                }

            })

        }

    </script>
</head>
<body>
<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">


                <form class="form-horizontal" id="create-form" role="form">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-owner">

                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startDate">
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endDate">
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">

                    <input id="edit-id" type="hidden"/>

                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-owner">

                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-startDate">
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-endDate">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <%--
                                <textarea></textarea>
                                一定要紧挨着
                                统一使用val()不能用html
                            --%>
                            <textarea class="form-control" rows="3" id="edit-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="search-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="search-endDate">
                    </div>
                </div>

                <button type="button" class="btn btn-default" id="searchBtn">查询</button>

            </form>
        </div>


        <%--
            data-toggle="model"
                说明我们要打开的是模态窗口

            data-target="#"
                定位具体的目标模态窗口

            但是这样的写法，不好。不能添加其他的功能

            未来的开发中，一定不能写死在元素当中，一定写在js代码块里面

        --%>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span>创建
                </button>
                <button type="button" class="btn btn-default " id="editBtn"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="deleteBtn"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>

        </div>

        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="qx"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="activityBody">

                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage">

            </div>

        </div>

    </div>

</div>
</body>
</html>