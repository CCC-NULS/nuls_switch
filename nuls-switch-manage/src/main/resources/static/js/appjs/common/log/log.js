var prefix = "/common/log"
$(function() {
	load();

});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
						showRefresh : true,
						// showToggle : true,
						// showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						// search : true, // 是否显示搜索框
						// showColumns : true, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
						// "server"
						queryParamsType : "",
						// //设置为limit则会发送符合RESTFull格式的参数
						queryParams : function(params) {
							return {
								pageNumber : params.pageNumber,
								pageSize : params.pageSize,
								sort : 'id',
								order : 'desc',
								operation : $("#searchOperation").val(),
								username : $("#searchUsername").val(),
								userId : $("#searchUserId").val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						responseHandler : function(res){
							console.log(res);
							return {
				                "total": res.data.total,//总数
				                "rows": res.data.records   //数据
				             };
						},
						columns : [
								{
									checkbox : true
								},
								{
									field : 'id', // 列字段名
									title : '序号' // 列标题
								},
								{
									field : 'userId',
									title : '员工Id'
								},
								{
									field : 'username',
									title : '用户名'
								},
								{
									field : 'operation',
									title : '操作',
									formatter : function(value, row, index) {
										return '<span title="'+row.method+'">'+value+'</span>';
									}
								},
								{
									field : 'time',
									title : '用时'
								},
								{
									field : 'params',
									title : '参数',
									formatter : function(value, row, index){
										var txt = !value ? "" : value, size = 100;
										if(txt.length<=size){
											return txt;
										}else{
											return "<textarea rows=\""+Math.min(5,Math.ceil(lengthCN(txt)/size))+"\" cols=\""+size+"\" style=\"width:100%;background:transparent;border-style:none;resize:none\" readonly>"+txt+"</textarea>";
										}
									}
								},
								{
									field : 'ip',
									title : 'IP地址'
								},
								{
									field : 'gmtCreate',
									title : '创建时间'
								},
								{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.userId
												+ '\')"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ row.id
												+ '\')"><i class="fa fa-remove"></i></a> ';
										var f = '<a class="btn btn-success btn-sm" href="#" title="重置密码"  mce_href="#" onclick="resetPwd(\''
												+ row.userId
												+ '\')"><i class="fa fa-key"></i></a> ';
										return d;
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/remove",
			type : "post",
			data : {
				'id' : id
			},
			beforeSend : function(request) {
				index = layer.load();
			},
			success : function(r) {
				if (r.code == 0) {
					layer.close(index);
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
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {
	});
}