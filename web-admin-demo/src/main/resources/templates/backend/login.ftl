<@override name="title">Calculog</@override>

<@override name="css">
<style>
    .m-login {margin: 15vh auto 0;width: 400px;text-align: center;}
    .m-logo {margin: 50px 0 50px;}
    .m-form .el-button {margin-top: 15px;width: 80%;}
    footer {text-align: center;margin-bottom: 20px;font-size: 14px;color: #777;}
</style>
</@override>

<@override name="content">
<div id="app" class="g-body">
    <div class="m-login">
        <div class="m-logo">
            <#--<img src="/static/img/logo.png" width="160">-->
            <p>LOGO</p>
        </div>
        <el-form class="m-form" ref="form" :model="form" :rules="rules">
            <el-form-item label="" prop="account">
                <el-input v-model="form.account" placeholder="账号"></el-input>
            </el-form-item>
            <el-form-item label="" prop="password">
                <el-input type="password" v-model="form.password" placeholder="密码" @keyup.enter.native="login()"></el-input>
            </el-form-item>
            <el-button type="primary" @click="login()" :loading="loading" round>登录</el-button>
        </el-form>
    </div>
</div>
 <footer>
     <p>Copyright © mklaus.com</p>
 </footer>
</@override>

<@override name="js">
<script>
    var app = new Vue({
        el: "#app",
        data: {
            loading: false,
            form: {account: "", password: ""},
            rules: {
                account: [{required: true, message: '账号不能为空' },],
                password:[
                    { required: true, message: '密码不能为空' },
                    {
                        validator: function(rule, value, callback) {
                            if (value.length < 6) {
                                callback(new Error('密码长度不能小于6位'))
                            } else {
                                callback();
                            }
                        },
                        trigger: 'blur'
                    }
                ]
            }
        },
        methods: {
            login: function () {
                this.$refs['form'].validate(function(valid) {
                    if (valid) {
                        post(app, {
                            loading: true,
                            url: "/auth/login",
                            data: app.form,
                            success: function () {
                                location.href = "/backend/admin";
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            }
        }
    });
</script>
</@override>

<@extends name="/layout/element.ftl"/>