<@override name="title">短信验证码</@override>

<@override name="css">
<style>
    body {background-color: #f9f9f9}
    .m-btn {
        margin: 15px 5vw;
    }
</style>
</@override>

<@override name="content">
<div class="g-body" id="app">
    <div class="weui-cells weui-cells_form m-form">
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">手机号码</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" v-model="mobile" type="number" pattern="[0-9]*" maxlength="11" placeholder="">
            </div>
        </div>
        <div class="weui-cell weui-cell_vcode">
            <div class="weui-cell__hd">
                <label class="weui-label">验证码</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" v-model="code" type="tel" placeholder="">
            </div>
            <div class="weui-cell__ft" >
                <button class="weui-vcode-btn" @click="sendCode" style="width: 114px">{{codeText}}</button>
            </div>
        </div>
    </div>


    <div class="m-btn">
        <a href="javascript:;" class="weui-btn weui-btn_primary" @click="verify">验证</a>
        <a href="javascript:;" class="weui-btn weui-btn_default close-popup">取消</a>
    </div>
</div>
</@override>

<@override name="js">
<script>
    const app = new Vue({
        el: "#app",
        data: {
            send: false,
            codeText: "获取验证码",
            mobile: "",
            code: ""
        },
        methods: {
            sendCode: function () {
                if (this.send) {
                    return;
                }
                this.send = true;
                let self = this;
                post({
                    url: "/api/code/send",
                    loadingText: '请求中',
                    data: {"mobile": self.mobile},
                    success: function () {
                        var second = 60;
                        var interval = setInterval(function () {
                            if (--second < 1) {
                                self.send = false;
                                self.codeText = "获取验证码";
                                clearInterval(interval)
                            } else {
                                self.codeText = second + " 秒";
                            }
                        }, 1000)
                    },
                    fail: function (d) {
                        $.alert(d.errMsg);
                        self.send = false;
                    }
                })
            },
            verify: function () {
                var self = this;
                post({
                    url: "/api/code/valid",
                    loadingText: '验证',
                    data: {"mobile": self.mobile, "code": self.code},
                    success: function () {
                        $.toast("验证成功")
                    }
                })
            }
        },
        created: function () {

        }
    })
</script>
</@override>

<@extends name="/layout/mobile-weui-vue.ftl"/>