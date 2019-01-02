<@override name="title">管理员</@override>

<@override name="css">
<style>

</style>
</@override>

<@override name="content">
    <div class="m-content">
        <p class="operation">填写管理员信息</p>
        <div class="form-panel">
            <el-form ref="form" :model="form" ref="form" :rules="rules" label-width="100px">
                <el-row>
                    <el-col  :span="12">
                        <el-form-item label="账号" prop="account">
                            <el-input v-model="form.account" <#if adminId??>disabled</#if>></el-input>
                        </el-form-item>
                        <el-form-item label="用户名" prop="username">
                            <el-input v-model="form.username"></el-input>
                        </el-form-item>
                        <el-form-item label="邮箱" prop="email">
                            <el-input v-model="form.email"></el-input>
                        </el-form-item>
                        <el-form-item label="手机号码" prop="mobile">
                            <el-input v-model="form.mobile"></el-input>
                        </el-form-item>
                        <div class="submit-block">
                            <el-button type="success" @click="onSubmit">保存</el-button>
                        </div>
                    </el-col>
                </el-row>
            </el-form>
        </div>
    </div>
</@override>

<@override name="js">
<script>
    var adminId = <#if adminId??>${adminId}<#else>0</#if>;
    var app = new Vue({
        el: "#app",
        mixins: [layout_mixin, app_mixin],
        data: {
            form: {account: "", password: "", confirmPassword: ""},
            rules: {
                account: [{required: true, message: "请输入账号", trigger: 'blur'}],
                username: [{required: true, message: "请输入用户名", trigger: 'blur'}],
                email: [{required: true, message: "请输入邮箱", trigger: 'blur'}],
                mobile: [{required: true, message: "请输入手机号码", trigger: 'blur'}],
            }
        },
        methods: {
            onSubmit: function () {
                this.$refs["form"].validate(function(valid) {
                    if (valid) {
                        let url = adminId > 0 ? "/backend/admin/update" : "/backend/admin/save";
                        post(app, {
                            url: url,
                            data: app.form,
                            success: function () {
                                app.$message.success("保存成功");
                                location.href = "/backend/admin";
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            }
        },
        created: function () {
            if (adminId && adminId > 0) {
                get(app, {
                    url: "/backend/admin/get",
                    data: {"adminId": adminId},
                    success: function (d) {
                        app.form = d.admin;
                        app.form.adminId = d.admin.id;
                    }
                })
            }
        }
    });
</script>
</@override>

<@extends name="/backend/app-layout.ftl"/>