<@override name="title">Calculog</@override>

<@override name="layout-css">
    <style>
        .g-side .el-menu { height: 100%; min-height: calc(100vh - 60px)}
        .g-body { width:100%; padding: 20px; background-color: #f8f9fb;}
    </style>
    <@block name="css"></@block>
</@override>
<@override name="layout-content">
<el-container>
    <el-aside width="200px" class="g-side">
        <el-menu :default-active="sectionActive()" class="" @select="sectionSelect">
            <el-menu-item index="1">
                <i class="el-icon-menu"></i>
                <span slot="title">管理员</span>
            </el-menu-item>
            <#--<el-menu-item index="2">-->
                <#--<i class="el-icon-star-off"></i>-->
                <#--<span slot="title">标签</span>-->
            <#--</el-menu-item>-->
        </el-menu>
    </el-aside>

    <el-container class="g-body">
        <@block name="content"></@block>
    </el-container>
<el-container>
</@override>

<@override name="layout-js">
    <script>
        var app_mixin = {
            data: {
            },
            methods: {
                sectionActive: function() {
                    if (location.href.indexOf("/backend/admin") > -1) {
                        return "1"
                    }
                    // if (location.href.indexOf("/backend/tag") > -1) {
                    //     return "2"
                    // }
                    return "1";
                },
                sectionSelect: function (index) {
                    switch (index) {
                        case '1': location.href="/backend/admin"; break;
                        // case '2': location.href="/backend/tag"; break;
                    }
                }
            }
        };
    </script>
    <@block name="js"></@block>
</@override>

<@extends name="/backend/layout.ftl"/>
