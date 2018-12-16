<@override name="css">
 <link rel="stylesheet" href="/static/plugin/quill/quill.snow.css">
</@override>

<@override name="content">
    <div id="app">
        <div id="toolbar-container">
                <span class="ql-formats">
                    <button class="ql-bold"></button>
                    <button class="ql-italic"></button>
                    <button class="ql-underline"></button>
                    <button class="ql-strike"></button>
                     <button class="ql-blockquote"></button>
                </span>
            <span class="ql-formats">
                     <select class="ql-size">
                        <option value="10px">10px</option>
                        <option value="12px">12px</option>
                        <option value="14px">14px</option>
                        <option value="15px">15px</option>
                        <option selected>16px</option>
                        <option value="18px">18px</option>
                        <option value="20px">20px</option>
                      </select>
                </span>
            <span class="ql-formats">
                    <select class="ql-color"></select>
                    <select class="ql-background"></select>
                </span>
            <span class="ql-formats">
                    <button class="ql-list" value="ordered"></button>
                    <button class="ql-list" value="bullet"></button>
                    <button class="ql-indent" value="-1"></button>
                    <button class="ql-indent" value="+1"></button>
                </span>
            <span class="ql-formats">
                    <button class="ql-direction" value="rtl"></button>
                    <select class="ql-align"></select>
                </span>
            <span class="ql-formats">
                    <button class="ql-link"></button>
                    <button id="upload-img-btn">
                        <svg class="icon" aria-hidden="true" style="font-size: 16px">
                            <use xlink:href="#icon-tupian"></use>
                        </svg>
                    </button>
                </span>
            <span class="ql-formats">
                    <button class="ql-clean"></button>
                </span>
        </div>
        <div id="editor-container"></div>
        <button @click="save">保存</button>
    </div>
</@override>

<@override name="js">
<script src="/static/plugin/quill/quill.min.js"></script>
<script src="/static/plugin/quill/ImageResize.js"></script>
<script src="/static/plugin/qiniu/qiniu.min.js"></script>
<script src="/static/plugin/qiniu/qiniu.tool.js"></script>
<script>
    let editor;
    $(function () {
        let fontSizeStyle = Quill.import('attributors/style/size');
        fontSizeStyle.whitelist = ['10px', '12px', '14px', '15px', '16px', '18px', '20px'];
        Quill.register(fontSizeStyle, true);
        editor = new Quill('#editor-container', {
            modules: {toolbar: "#toolbar-container"},
            theme: 'snow'
        });
        Quill.register('modules/imageResize', new ImageResize(editor));
        app.content && editor.setContents(JSON.parse(app.content));
    });

    let app = new Vue({
        el: '#app',
        data: {
            qiniu: {},
            content: "{\"ops\":[{\"attributes\":{\"size\":\"20px\",\"color\":\"#e60000\"},\"insert\":\"hello\"},{\"insert\":\"\\n\"}]}",
        },
        methods: {
            createImageUploader: function () {
                new QiniuUploader.Builder()
                        .elementId("upload-img-btn")
                        .token(app.qiniu.token)
                        .complete(function (r) {
                            let link = app.qiniu['bucket_url'] + r['key'];
                            let range = editor.getSelection(true);
                            editor.insertEmbed(range.index, 'image', link);
                            editor.setSelection(range.index + 1, 0);
                        })
                        .build();
            },
            save: function () {
                var content = JSON.stringify(editor.getContents());
                console.log(content)
            }
        },
        created: function () {
            const self = this;
            get({
                url:"/api/qiniu/token",
                success: function (d) {
                    self.qiniu = d;
                    self.createImageUploader();
                }
            })
        }
    });

</script>
</@override>

<@extends name="/layout/mobile-weui-vue.ftl"/>