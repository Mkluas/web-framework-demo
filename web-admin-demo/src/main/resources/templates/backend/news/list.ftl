<@override name="title">Calculog</@override>

<@override name="css">
<style>
</style>
</@override>

<@override name="content">
    <div class="m-content">
        <p class="operation">新闻列表 <a href="/backend/news/save" class="save"><i class="el-icon-circle-plus-outline"></i></a></p>

        <div id="data-container">
            <el-table
                    v-loading="loading"
                    element-loading-text="拼命加载中"
                    :data="pager.list"
                    border
                    style="width: 100%">
                <#--<el-table-column-->
                        <#--prop="id"-->
                        <#--label="ID"-->
                        <#--min-width="30">-->
                <#--</el-table-column>-->
                <el-table-column align="center" label="封面"  width="200">
                    <div slot-scope="scope">
                        <img :src="scope.row.cover | img(200, 200)" height="120px">
                    </div>
                </el-table-column>
                <el-table-column
                        prop="title"
                        label="标题"
                        min-width="100">
                </el-table-column>
                <el-table-column
                        prop="intro"
                        label="简介"
                        min-width="200">
                </el-table-column>
                    <el-table-column label="创建时间" width="120">
                        <div slot-scope="scope">
                            {{scope.row.createTime | datetime}}
                        </div>
                    </el-table-column>
                <el-table-column align="center" label="操作" min-width="100">
                    <div slot-scope="scope">
                        <a :href="'/backend/news/update?newsId=' + scope.row.id"><el-button type="text">编辑</el-button></a>
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
            remove: function(appId) {
                remove(app, {
                    url: "/backend/news/remove",
                    data: {"newsId": newsId},
                    success: function () {
                        app.loadData(app.pager.pageNumber);
                    }
                })
            },
            loadData: function(pageNumber) {
                get(this, {
                    loading: true,
                    url: "/backend/news/list",
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