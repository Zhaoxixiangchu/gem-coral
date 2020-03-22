(function ($) {
    $.learunindex = {
        jsonWhere: function (data, action) {
            if (action == null) return;
            var reval = new Array();
            $(data).each(function (i, v) {
                if (action(v)) {
                    reval.push(v);
                }
            })
            return reval;
        },
        makeHtml: function (datas) {
            // alert(_html+"=data.child===>"+_html)
            return _html;
        },
        loadMenu: function () {
            var mdata  ="";
            $.ajax({
                type: "get",
                url: "prekit/sys/right/leftSidebar",
                data: {
                },
                async:false, // 异步请求
                cache:false, // 设置为 false 将不缓存此页面
                dataType: 'json', // 返回对象
                success: function(res) {
                    console.log(res);
                    if(res.code == 0){
                        mdata  = res.data;
                        if(mdata == "" || mdata == null || mdata.length == 0){
                            mdata = [
                                {
                                    "deleted":0,
                                    "flag":"right:add",
                                    "icon":"layui-icon-bluetooth",
                                    "id":5,
                                    "level":3,
                                    "link":"home",
                                    "name":"控制台",
                                    "pid":0,
                                    "type":1
                                },
                                {
                                    "child":[
                                        {
                                            "deleted":0,
                                            "flag":"right",
                                            "icon":"layui-icon-gift",
                                            "id":2,
                                            "level":2,
                                            "link":"right.html",
                                            "name":"权限管理",
                                            "pid":1,
                                            "type":0
                                        }
                                    ],
                                    "deleted":0,
                                    "flag":"sys",
                                    "icon":"layui-icon-unlink",
                                    "id":1,
                                    "level":1,
                                    "link":"",
                                    "name":"系统管理",
                                    "pid":0,
                                    "type":0
                                }
                            ];
                        }
                        //TODO: 拼装HTML
                        get_html(mdata)
                    }else{
                        console.log(res.msg);
                        if(mdata == "" || mdata == null || mdata.length == 0){
                            mdata = menusData_def;
                        }
                    }
                },
                error: function(res) {
                    // 请求失败函数
                    console.log(res);
                    if(mdata == "" || mdata == null || mdata.length == 0){
                        mdata = menusData_def;
                    }
                }
            })

            // console.log("==="+_html)
            $("#sidebar-menu").append(_html);
        }
    };
    $(function () {
        $.learunindex.loadMenu();
    });
})(jQuery);

var _html = "";
function get_html(datas) {
    for (var i=0;i<datas.length;i++){
        var data = datas[i];
        if(data.pid == '0'){
            _html += '<li class="layui-nav-item">';
        }
        //如果没有子对象
        if(data.child == null){
            if(data.pid != '0'){
                _html += '<dd>';
            }
            _html += '<a lay-href="'+data.link+'">';
            _html += '<i class="layui-icon '+data.icon+'"></i>&emsp;';
            _html += '<cite>'+data.name+'</cite>';
            _html += '</a>';

            if(data.pid != '0'){
                _html += '</dd>';
            }
        }else{
            //如果有子菜单
            _html += '<a>';
            _html += '<i class="layui-icon '+data.icon+'"></i>&emsp;';
            _html += '<cite>'+data.name+'</cite>';
            _html += '</a>';
            _html += '<dl class="layui-nav-child">';
            _html += '<dd>';
            get_html(data.child);
            _html += '</dd>';
            _html += '</dl>';
        }
        if(data.pid == '0'){
            _html += '</li>';
        }
    }
}
