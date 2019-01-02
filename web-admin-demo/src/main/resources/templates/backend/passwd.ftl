<#--<@override name="title">Calculog</@override>-->

<@override name="layout-css">
<style>
    .m-content {
        max-width: 800px;
    }
    .el-form-item {
        width: 500px;
        margin: 20px auto;
    }
</style>
</@override>

<@override name="layout-content">
    <div class="m-content">
        <p class="operation">修改密码</p>
        <div class="form-panel">
            <el-form ref="form" :model="form" ref="form" :rules="rules" label-width="100px">
                <el-form-item label="原密码" prop="oldPassword">
                    <el-input type="password" v-model="form.oldPassword"></el-input>
                </el-form-item>

                <el-form-item label="新密码" prop="newPassword">
                    <el-input type="password" v-model="form.newPassword"></el-input>
                </el-form-item>

                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input type="password" v-model="form.confirmPassword"></el-input>
                </el-form-item>

                <div class="submit-block">
                    <el-button type="success" @click="onSubmit">保存</el-button>
                </div>
            </el-form>
        </div>
    </div>
</@override>

<@override name="layout-js">
<script>
    var app = new Vue({
        el: "#app",
        mixins: [layout_mixin],
        data: {
            form: {oldPassword: "", newPassword: "", confirmPassword: ""},
            rules: {
                oldPassword: [{required: true, message: "请输入原密码"}],
                newPassword: [{required: true, message: "请输入新密码"}, {min: 6,  message: '新密码长度最小为6位', trigger: 'blur'}],
                confirmPassword: [
                    {required: true, message: "请输入确认密码"},
                    {validator: (rule, value, callback) => {
                        if (value !== app.form.newPassword) {
                            callback(new Error('两次输入密码不一致!'));
                        } else {
                            callback();
                        }
                    }, trigger: 'blur' }
                ],
            }
        },
        methods: {
            onSubmit: function () {
                this.$refs['form'].validate(function(valid) {
                    if (valid) {
                        post(app, {
                            url: "/backend/admin/passwd",
                            data: app.form,
                            success: function () {
                                app.$message({type: "success", message: "修改成功"});
                                setTimeout(function () {location.href = "/backend";}, 1000)
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

<@extends name="/backend/layout.ftl"/>
