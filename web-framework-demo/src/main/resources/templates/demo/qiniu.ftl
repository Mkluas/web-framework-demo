<@override name="title">七牛上传</@override>

<@override name="css">
<style>
    .m-upload {
        margin: 10vh auto;
        width: 400px;
        text-align: center;
    }
    .m-upload img {width: 100%}
</style>
</@override>

<@override name="content">
<div class="g-body" id="app">
    <div class="m-upload">
        <button id="upload-img-btn">上传图片</button>
        <img :src="img_link" v-if="img_link.length > 0">
    </div>
    <div class="m-upload">
        <button id="upload-img-list-btn">上传多张图片</button>
        <img v-for="img in img_list" :src="img">
    </div>
</div>
</@override>

<@override name="js">
<script src="/static/plugin/qiniu/qiniu.min.js"></script>
<script src="/static/plugin/qiniu/qiniu.tool.js"></script>
<script>
    const app = new Vue({
        el: "#app",
        data: {
            img_link: '',
            img_list: [],
            qiniu: {},
            max: 3,
            remain: 0
        },
        methods: {
            createUploader: function () {
                new QiniuUploader.Builder()
                        .elementId("upload-img-btn")
                        .token(app.qiniu.token)
                        .complete(function (r) {
                            app.img_link = app.qiniu['bucket_url'] + '/' + r['key'];
                        })
                        .build();

                new QiniuUploader.Builder()
                        .elementId("upload-img-list-btn")
                        .token(app.qiniu.token)
                        .multiple()
                        .complete(function (r) {
                            const img_url = app.qiniu['bucket_url'] + '/' + r['key'];
                            app.img_list.push(img_url);
                        })
                        .interceptor({
                            beforeUpload: function (files) {
                                console.log("Upload " + files.length + " files.");
                                app.remain = app.max - app.img_list.length;
                                console.log("Remain " + app.remain + " files can upload.");
                                return true;
                            },
                            beforeEachUpload: function (file) {
                                if (app.remain < 1) {
                                    return false;
                                }
                                app.remain--;
                                return true;
                            }
                        })
                        .build();
            }
        },
        created: function () {
            const self = this;
            get({
                url:"/api/qiniu/token",
                success: function (d) {
                    self.qiniu = d;
                    self.createUploader();
                }
            })
        }
    })
</script>
</@override>

<@extends name="/layout/mobile-weui-vue.ftl"/>