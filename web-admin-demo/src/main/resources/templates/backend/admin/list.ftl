<@override name="title">管理员</@override>

<@override name="css">
<style>
</style>
</@override>

<@override name="content">
    <div class="m-content">
        <p class="operation">管理员列表 <a href="/backend/admin/save" class="save"><i class="el-icon-circle-plus-outline"></i></a></p>

        <div id="data-container">
            <el-table
                    v-loading="loading"
                    element-loading-text="拼命加载中"
                    :data="pager.list"
                    border
                    style="width: 100%">
                <el-table-column
                prop="id"
                label="ID"
                min-width="30">
                </el-table-column>
                <el-table-column
                        prop="account"
                        label="账号"
                        min-width="120">
                </el-table-column>
                <el-table-column
                        prop="username"
                        label="用户名"
                        min-width="120">
                </el-table-column>
                <el-table-column
                        prop="email"
                        label="邮箱"
                        min-width="120">
                </el-table-column>
                <el-table-column
                        prop="mobile"
                        label="手机号码"
                        min-width="120">
                </el-table-column>
                <el-table-column label="创建时间" width="200">
                    <div slot-scope="scope">
                        {{scope.row.createTime | time}}
                    </div>
                </el-table-column>
                <el-table-column align="center" label="操作" min-width="120">
                    <div slot-scope="scope">
                        <a :href="'/backend/admin/update?adminId=' + scope.row.id"><el-button type="text">编辑</el-button></a>
                        <el-button type="text" @click="reset(scope.row.id)">重置密码</el-button>
                        <el-button type="text" @click="remove(scope.row.id)">删除</el-button>
                    </div>
                </el-table-column>
            </el-table>
            <div class="pagination center">
                <el-pagination
                        @current-change="loadData"
                        layout="prev, pager, next"
                        :pageSize=10
                        :current-page="pager.pageNumber"
                        :total="pager.recordCount">
                </el-pagination>
            </div>
        </div>
    </div>
</@override>

<@override name="js">
<script>
    var app = new Vue({
        el: "#app",
        mixins: [layout_mixin, app_mixin],
        data: {
            loading: false,
            pageNumber: 1,
            pager: {}
        },
        methods: {
            reset: function(adminId) {
                app.$confirm('此操作会将改管理员的密码重置为：123456, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    post(app, {
                        url: "/backend/admin/resetpassword",
                        data: {"adminId": adminId},
                        successText: "密码重置成功"
                    });
                }).catch(function (e) {
                    console.log('catch:' + e)
                });
            },
            remove: function(adminId) {
                remove(app, {
                    url: "/backend/admin/remove",
                    data: {"adminId": adminId},
                    success: function () {
                        app.loadData(app.pager.pageNumber);
                    }
                })
            },
            loadData: function(pageNumber) {
                get(this, {
                    loading: true,
                    url: "/backend/admin/list",
                    data: {pageNumber: pageNumber},
                    success: function(d) {
                        app.pager = d.pager;
                    }
                })
            }
        },
        created: function () {
            this.loadData(1);
        }
    });
</script>
</@override>

<@extends name="/backend/app-layout.ftl"/>