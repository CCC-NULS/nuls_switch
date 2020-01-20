var prefix = "/wxmp/mpFans";
$(function () {

    var appId = $('.currentMpInfo', window.top.document).attr('data-appid');
    console.log(appId)
    if(appId){
        console.log('mpFans 获取appId:' + appId);
        load();
    }else {
        console.log('mpfans 获取appId为空')
    }
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                // showRefresh : true,
                // showToggle : true,
                // showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                // search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
                // "server"
                queryParamsType: "",
                // //设置为limit则会发送符合RESTFull格式的参数
                queryParams: function (params) {
                    return {
                        // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        pageNumber: params.pageNumber,
                        pageSize: params.pageSize,
                        searchValue:$('#searchName').val(),
                        appId:$('.currentMpInfo', window.top.document).attr('data-appid')
                        // username:$('#searchName').val()
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                responseHandler: function (res) {
                    console.log(res);
                    return {
                        "total": res.data.total,// 总数
                        "rows": res.data.records
                        // 数据
                    };
                },
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        field: 'id',
                        title: 'ID'
                    },
//								{
//									field : 'mpId',
//									title : '公众号ID'
//								},
                    {
                        field: 'headimgurl',
                        title: '头像',
                        formatter: function (value, row, index) {
                            if (value) {
                                return '<img src="' + value + '" style="width:32px;height:32px;" />'
                            }
                            return value;
                        }
                    },
                    {
                        field: 'nickname',
                        title: '昵称'
                    },
                    {
                        field: 'sex',
                        title: '性别',
                        formatter: function (value, row, index) {
                            if (value == 1) {
                                return "男";
                            } else if (value == 2) {
                                return "女";
                            } else {
                                return "未知"
                            }
                        }
                    },
//								{
//									field : 'openid',
//									title : 'openid'
//								},
                    {
                        field: 'subscribe',
                        title: '关注状态',
                        formatter: function (value, row, index) {
                            if (value == '1') {
                                return "已关注";
                            } else if (value == '0') {
                                return "未关注";
                            } else {
                                return "未知"
                            }
                        }
                    },
                    {
                        field: 'subscribeTime',
                        title: '关注时间'
                    },
                    {
                        field: 'subscribeKey',
                        title: '关注来源'
                    },
                    {
                        field: 'city',
                        title: '市'
                    },
                    {
                        field: 'country',
                        title: '区'
                    },
                    {
                        field: 'province',
                        title: '省'
                    },
//								{
//									field : 'language',
//									title : '语言'
//								},
//								{
//									field : 'unionid',
//									title : 'unionid'
//								},
//								{
//									field : 'remark',
//									title : '备注'
//								},
//								{
//									field : 'groupid',
//									title : '分组ID'
//								},
//								{
//									field : 'status',
//									title : '用户状态',
//									formatter : function(value, row, index) {
//										if(value == 1){
//											return "正常";
//										}else if(value == 0){
//											return "禁用";
//										}else{
//											return "未知"
//										}
//									}
//								},
//								{
//									field : 'tagidList',
//									title : '标签列表'
//								},
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm '
                                + s_edit_h
                                + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm '
                                + s_remove_h
                                + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm" href="#" title="同步"  mce_href="#" onclick="sync(\''
                                + row.id
                                + '\')"><i class="fa fa-refresh"></i></a> ';
                            return e + f + d;
                        }
                    }]
            });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add' // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function batchSync() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要同步的数据");
        return;
    }
    layer.confirm("确认要同步选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/sync?appId=' + $('.currentMpInfo', window.top.document).attr('data-appid'),
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}

function sync(id) {
    console.log('sync fans :' + id);
    var ids = new Array();
    ids.push(id);
    layer.confirm('确定要同步选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/sync?appId=" + $('.currentMpInfo', window.top.document).attr('data-appid'),
            type: "post",
            data: {
                'ids': ids
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function syncWxMp() {
    console.log('sync fans from wx server...');
    layer.confirm('确定要同步所有粉丝记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/sync/wxmp?appId=" + $('.currentMpInfo', window.top.document).attr('data-appid'),
            type: "post",
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}