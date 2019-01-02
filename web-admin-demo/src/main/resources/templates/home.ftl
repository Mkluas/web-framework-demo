<@override name="title"></@override>

<@override name="css">
</@override>

<@override name="content">
<div id="app">
    Welcome
</div>
</@override>

<@override name="js">
<script>
    let app = new Vue({
        el: "#app",
        data: {

        },
        methods: {

        },
        created: function () {

        }
    });
</script>

</@override>

<@extends name="/layout/mobile-weui-vue.ftl"></@extends>