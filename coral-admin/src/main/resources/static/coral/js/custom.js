//获取微信
function getWechat() {
    layer.photos({
        photos: {
            "data": [{
                "src": "coral/images/769990999.png",
            }]
        }
        ,area: ['320px','435px']
        ,anim: 0
        ,end: function(){
            layer.closeAll();
        }
        ,success: function(layero){
            layer.tips('您可以添加开发者咨询', layero, {
                tips: [1, '#3595CC']
                ,time: 0
            });
        }
    });
}


function helloGem() {
    /*layer弹出一个示例*/
    layer.open({
        type: 1
        ,skin: 'layui-layer-admin'
        ,title: false //不显示标题栏
        ,closeBtn: false
        ,area: '300px;'
        ,shade: 0.8
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['逛逛社区','理解万岁']
        ,btnAlign: 'c'
        ,moveType: 1 //拖拽模式，0或者1
        ,content: '    <div style="padding: 50px; line-height: 22px; background-color: #1e9fff; color: #fff; font-weight: 300;">\n' +
            '        Hi，有缘人<br>\n' +
            '        初次见面便与你一见如故<br>\n' +
            '        你是我今生最美丽的相遇...<br>\n' +
            '        <hr>\n' +
            '        <legend>温馨提示：</legend>\n' +
            '        由于服务器资源有限，演示系统给您带来不便的体验<br>\n' +
            '        敬请谅解~~~~~\n' +
            '    </div>'
        ,success: function(layero){
            var btn = layero.find('.layui-layer-btn');
            btn.find('.layui-layer-btn0').attr({
                href: 'http://www.gemframework.com/bbs'
                ,target: '_blank'
            });
        }
    });
}