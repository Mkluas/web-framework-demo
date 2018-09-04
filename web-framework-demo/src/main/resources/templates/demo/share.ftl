<@override name="title">福利FM</@override>

<@override name="css">
</@override>

<@override name="content">
<div id="app">
</div>
<div class="weui-footer">
    <p class="weui-footer__text">Copyright &copy; 2017 mklaus.cn</p>
</div>
</@override>

<@override name="js">
<script src="/static/js/jweixin-1.2.0.js"></script>
<script>
    <#--
        参考链接
        https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
    -->
    wx.config({
        debug: false,
        appId: '${(wx.appId)!}',
        timestamp: '${(wx.timestamp?c)!}',
        nonceStr: '${(wx.nonceStr)!}',
        signature: '${(wx.signature)!}',
        jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
    });
    wx.ready(function(){
        wx.onMenuShareTimeline(app.share);
        wx.onMenuShareAppMessage(app.share);
    });
    wx.ready(function(){

    });
    wx.error(function(res){
        console.log(res);
    });
    const app = new Vue({
        el: '#app',
        data: {
            share: {
                title: '微信分享标题',
                link: 'http://mklaus.cn',
                desc: 'mklaus.cn',
                imgUrl: "http://7xo50o.com2.z0.glb.qiniucdn.com/9153EB0BB016325D1273884289A12698.jpg?imageView2/2/h/300/w/300/q/80",
                success: function () {
                    
                },
                fail: function () {

                },
                complete: function () {
                    
                },
                cancel: function () {
                    
                },
                trigger: function () {
//                    监听Menu中的按钮点击时触发的方法，该方法仅支持Menu中的相关接口。
                }
            },
        },
        methods: {
        }
    });
</script>
</@override>

<@extends name="/layout/mobile-vue.ftl"/>