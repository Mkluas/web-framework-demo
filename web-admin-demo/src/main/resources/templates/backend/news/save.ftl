<@override name="title">Calculog</@override>

<@override name="css">
<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {border-color: #409EFF;}
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {width: 178px;height: 178px;display: block;}
</style>
</@override>

<@override name="content">
    <div class="m-content">
        <p class="operation">填写新闻信息</p>

        <div class="form-panel">
            <el-form ref="form" :model="form" ref="form" :rules="rules" label-width="130px">
                <el-row>
                    <el-col :span="16">
                        <el-form-item label="标题" prop="title">
                            <el-input v-model="form.title"></el-input>
                        </el-form-item>
                        <el-form-item label="描述" prop="intro">
                            <el-input type="textarea" :autosize="{ minRows: 6, maxRows: 6}"  v-model="form.intro"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="封面" prop="cover">
                            <el-upload
                                    class="avatar-uploader"
                                    :data="qiniu"
                                    :action="qiniu.action"
                                    :show-file-list="false"
                                    :on-success="uploadIconSuccess">
                                <img v-if="form.cover" :src="form.cover" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            </el-upload>
                        </el-form-item>
                    </el-col>
                </el-row>


                <el-row>
                    <el-col :span="12">
                        <el-form-item label="类别" prop="tagId">
                            <el-select v-model="form.tagId" placeholder="请选择标签">
                                <el-option
                                        v-for="item in tagList"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="16">
                        <el-form-item label="原文链接（选填）" prop="url">
                            <el-input v-model="form.url"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>


                <div class="submit-block">
                    <el-button type="success" @click="onSubmit">保存</el-button>
                </div>

            </el-form>
        </div>
    </div>
</@override>

<@override name="js">
<script>
    var newsId = <#if newsId??>${newsId}<#else>0</#if>;
    var app = new Vue({
        el: "#app",
        mixins: [layout_mixin, app_mixin],
        data: {
            qiniu: {action: "https://up-z2.qiniup.com"},
            tagList: [],
            form: {title: "", intro: "", cover: "", tagId:"", url: ""},
            rules: {
                title: [{required: true, message: "请填写标题"}],
                tagId: [{required: true, message: "请选择标签"}],
                intro: [{required: true, message: "请填写描述"}],
                cover: [{required: true, message: "请上传封面"}],
            }
        },
        methods: {
            uploadIconSuccess: function(res) {
                app.form.cover = app.qiniu.bucket_url  + res.key;
            },
            onSubmit: function () {
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        let url = newsId > 0 ? "/backend/news/update" : "/backend/news/save";
                        post(app, {
                            url: url,
                            data: app.form,
                            success: function () {
                                app.$message.success("保存成功");
                                location.href = "/backend/news";
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
            get(app, {
                url: "/api/qiniu/token",
                success: function (d) {
                    app.qiniu = d;
                }
            });
            get(app, {
                url: "/backend/tag/listAll",
                success: function (d) {
                    app.tagList = d.tagList;
                }
            });
            if (newsId && newsId > 0) {
                get(app, {
                    url: "/backend/news/get",
                    data: {"newsId": newsId},
                    success: function (d) {
                        app.form = d.news;
                        app.form.newsId = d.news.id;
                    }
                })
            }
        }
    });
</script>
</@override>

<@extends name="/backend/app-layout.ftl"/>