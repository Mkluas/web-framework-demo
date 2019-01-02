<!doctype html>
<html>
<head>
    <title><@block name="title"></@block></title>
    <@block name="meta"></@block>
    <meta charset="utf-8">
    <link rel="stylesheet" href="/static/css/reset.css">
    <link rel="stylesheet" href="/static/plugin/element/element.css">
    <script src="/static/plugin/vue/vue.dev.js"></script>
    <script src="/static/plugin/vue/vue.tool.js"></script>
    <@block name="css" ></@block>
</head>

<body>
<@block name="content"></@block>
</body>
<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/tools.js"></script>
<script src="/static/plugin/element/element.js"></script>
<script src="/static/plugin/element/element.request.js"></script>
<@block name="js"></@block>
</html>


