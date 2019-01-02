<!doctype html>
<html>
<head>
    <title><@block name="title"></@block></title>
    <@block name="meta"></@block>
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <link rel="stylesheet" href="/static/css/reset.css">
    <script src="/static/plugin/vue/vue.dev.js"></script>
    <script src="/static/plugin/vue/vue.tool.js"></script>
    <@block name="css" ></@block>
</head>

<body>
<@block name="content"></@block>
</body>
<script src="/static/js/tools.js"></script>
<@block name="js"></@block>
</html>


