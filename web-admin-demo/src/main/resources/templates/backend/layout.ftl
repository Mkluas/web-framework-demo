<!doctype html>
<html>
<head>
    <title><@block name="title">Calculog</@block></title>
    <@block name="meta"></@block>
    <meta charset="utf-8">
    <link rel="stylesheet" href="/static/css/reset.css">
    <link rel="stylesheet" href="/static/css/backend.css">
    <link rel="stylesheet" href="/static/plugin/element/element.css">
    <script src="/static/plugin/vue/vue.dev.js"></script>
    <script src="/static/plugin/vue/vue.tool.js"></script>
    <@block name="layout-css" ></@block>
</head>
<body>
<div id="app">
    <header>
        <el-menu :default-active="moduleActive()" class="m-module-nav" mode="horizontal" background-color="#324157" text-color="#fff" active-text-color="#67C23A" @select="handleSelect">
            <div class="m-logo">
                <img src="/static/img/logo.png">
                <a href="/backend"><p class="logo-name">Calculog</p></a>
            </div>
            <#--<el-menu-item index="1">应用中心</el-menu-item>-->
            <#--<el-menu-item index="2">资讯管理</el-menu-item>-->
            <el-submenu class="account" index="3">
                <template slot="title">我的工作台</template>
                <el-menu-item index="passwd">修改密码</el-menu-item>
                <el-menu-item index="logout">退出登录</el-menu-item>
            </el-submenu>
        </el-menu>
    </header>

    <@block name="layout-content"></@block>
</div>
</body>

<script src="/static/js/jquery.min.js"></script>
<script src="/static/plugin/element/element.js"></script>
<script src="/static/js/tools.js"></script>
<script src="/static/plugin/element/element.request.js"></script>
<script>
    var module1 = ["/backend/news", "/backend/game", "/backend/tag", "/backend/advertising"];
    var module2 = [];
    var layout_mixin = {
        data: {

        },
        methods: {
            moduleActive: function() {
                let url = location.href;
                for (let i = 0; i < module1.length; i++) {
                    if (url.indexOf(module1[i]) > -1) {
                        return "1"
                    }
                }
                for (let i = 0; i < module2.length; i++) {
                    if (url.indexOf(module2[i]) > -1) {
                        return "2"
                    }
                }
                return "-1";
            },
            handleSelect: function (index) {
                console.log('handle select', index);
                switch (index) {
                    case '1': location.href = '/backend'; break;
                    case 'passwd': location.href = "/backend/passwd"; break;
                    case 'logout': post(app, {
                        url: "/auth/logout",
                        success: function () {
                            location.href = "/auth/login";
                        }
                    }); break;
                }
            },
        }
    };
</script>
<@block name="layout-js"></@block>
</html>

