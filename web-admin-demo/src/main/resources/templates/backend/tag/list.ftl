<@override name="title">Calculog</@override>

<@override name="css">
<style>
</style>
</@override>

<@override name="content">
    <div class="m-content">
        <p class="operation">标签列表 <a class="save" @click="save"><i class="el-icon-circle-plus-outline"></i></a></p>

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
                        prop="name"
                        label="名称"
                        min-width="100">
                </el-table-column>
                <el-table-column align="center" label="操作" min-width="100">
                    <div slot-scope="scope">
                        <el-button type="text" @click="update(scope.row)">编辑</el-button>
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

        <el-dialog title="标签编辑" :visible.sync="dialogFormVisible" >
            <el-form :model="form" ref="form" :rules="rules" label-width="100px">
                <el-form-item label="名称" prop="name">
                    <el-col :span="12" >
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submit">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</@override>

<@override name="js">
<script>
    var app = new Vue({
        el: "#app",
        mixins: [layout_mixin, app_mixin],
        data: {
            dialogFormVisible: false,
            saveFlag: true,
            loading: false,
            pageNumber: 1,
            pager: {},
            form: {name: ""},
            rules: {name: [{required: true, message: "请输入标签名称"}]}
        },
        methods: {
            save: function() {
                this.form = {name: ""};
                this.saveFlag = true;
                this.dialogFormVisible = true;
            },
            update: function(row) {
                this.form = deepClone(row);
                this.form.tagId = row.id;
                this.saveFlag = false;
                this.dialogFormVisible = true;
            },
            submit: function() {
                this.dialogFormVisible = false;
                let url = app.saveFlag ? "/backend/tag/save" : "/backend/tag/update";
                post(app, {
                    url: url,
                    data: app.form,
                    success: function () {
                        app.$message.success("操作成功");
                        app.loadData(app.saveFlag ? 1: app.pager.pageNumber);
                    }
                })
            },
            availableChange: function(row) {
                let url = row.available ? "/backend/tag/show" : "/backend/tag/hide";
                post(app, {
                    url: url,
                    data: {"appId": row.id},
                    successText: "操作成功"
                })
            },
            remove: function(appId) {
                remove(app, {
                    url: "/backend/tag/remove",
                    data: {"appId": appId},
                    success: function () {
                        app.loadData(app.pager.pageNumber);
                    }
                })
            },
            loadData: function(pageNumber) {
                get(this, {
                    loading: true,
                    url: "/backend/tag/list",
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