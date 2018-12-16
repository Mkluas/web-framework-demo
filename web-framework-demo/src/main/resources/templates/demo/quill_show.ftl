<@override name="css">
 <link rel="stylesheet" href="/static/plugin/quill/quill.snow.css">
</@override>

<@override name="content">
    <div id="app">
        <div id="content"></div>
    </div>
</@override>

<@override name="js">
<script src="/static/plugin/quill/quill.min.js"></script>
<script>
    $(function () {
        var fontSizeStyle = Quill.import('attributors/style/size');
        fontSizeStyle.whitelist = ['10px', '12px', '14px', '15px', '16px', '18px', '20px'];
        Quill.register(fontSizeStyle, true);
        var editor = new Quill('#content', {readOnly: true});
        editor.setContents(JSON.parse(app.content));
    });

    let app = new Vue({
        el: '#app',
        data: {
            content: "{\"ops\":[{\"attributes\":{\"size\":\"20px\",\"color\":\"#e60000\"},\"insert\":\"hello\"},{\"insert\":\"\\n\"}]}",
        },
        methods: {

        },
        created: function () {

        }
    });

</script>
</@override>

<@extends name="/layout/mobile-weui-vue.ftl"/>