<@override name="title">點汁楽</@override>

<@override name="css">
<script type="application/javascript" src="/static/plugin/vue/vue.min.js"></script>
<script type="application/javascript" src="/static/plugin/vue/vue.tool.js"></script>
<style>
    .address-block {
        margin-top: 60px;
    }
    .address-block span {
        margin-right: 20px;
        font-size: 16px;
    }
    .address-block .address {
        color: #737373;
        font-size: 14px;
        padding-right: 15px;
    }

    .text-block {
        height: 60px;
        position: relative;
    }
    .item-price {
        position: absolute;
        right: 0;
        top: 0;
        color: #999;
    }
    .item-count {
        position: absolute;
        right: 0;
        bottom: 0px;
        color: #999;
        font-size: 14px;
    }
    .total-price {
        text-align: right;
        color: #ff0000;
        font-size: 20px;
    }
    .pay-block {
        margin: 20px;
    }
</style>
</@override>

<@override name="content">
<div class="main" id="module" v-cloak="">

    <div class="weui-cells address-block">
        <div class="weui-cell">
            <div class="weui-cell__bd">
                <p><span>{{order.username}}</span><span>{{order.mobile}}</span></p>
                <p class="address">{{order.address}}</p>
            </div>
        </div>
    </div>

    <div class="weui-panel weui-panel_access">
        <div class="weui-panel__hd">订单详情</div>
        <div class="weui-panel__bd">
            <div v-for="item in order.data" class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box__hd">
                    <img class="weui-media-box__thumb" :src="item.productCover | img(400,400)">
                </div>
                <div class="weui-media-box__bd text-block">
                    <h4 class="weui-media-box__title">{{item.productName}}</h4>
                    <p class="weui-media-box__desc">{{item.skuIntro}}</p>
                    <span class="item-price">￥{{item.price | money}}</span>
                    <span class="item-count">x {{item.count}}</span>
                </div>
            </div>
        </div>
        <div class="weui-panel__ft">
            <a href="javascript:void(0);" class="weui-cell weui-cell_access weui-cell_link">
                <div class="weui-cell__bd total-price">￥{{order.price | money}}</div>
            </a>
        </div>
    </div>

    <div class="pay-block" v-if="order.status == 'NEW'">
        <a href="javascript:;" @click="pay" class="weui-btn weui-btn_primary">支付</a>
    </div>

</div>

</@override>

<@override name="js">
<script src="/static/js/jweixin-1.2.0.js"></script>
<script>
    wx.config({
        debug: false,
        appId: '${(wx.appId)!}',
        timestamp: '${(wx.timestamp?c)!}',
        nonceStr: '${(wx.nonceStr)!}',
        signature: '${(wx.signature)!}',
        jsApiList: ['chooseWXPay']
    });

    function pay(payinfo, cb) {
        wx.chooseWXPay({
            timestamp: payinfo.timeStamp,
            nonceStr: payinfo.nonceStr,
            package: payinfo.packageValue,
            signType: payinfo.signType,
            paySign: payinfo.paySign,
            success: function () {
                cb && cb();
            }
        });
    }

</script>
<script>
    var app = new Vue({
        el: '#vue',
        data: {
            order: ${order},
        },
        methods: {
            pay: function () {
                post({
                    url: '/mp/order/pay',
                    data: {"outTradeNo" : app.order.outTradeNo},
                    success: function (d) {
                        pay(d.payInfo, function () {
                            window.location.href = "/pay/result?outTradeNo=" + app.order.outTradeNo;
                        })
                    }
                })
            }
        }
    });
</script>
</@override>

<@extends name="/layout/app.ftl"/>