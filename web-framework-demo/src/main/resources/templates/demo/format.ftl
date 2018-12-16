<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Format</title>
    <link rel="stylesheet" href="/static/plugin/weui/weui.min.css" />
    <style>
        body {
            background-color: #f7f7f7;
        }
    </style>
</head>
<body>

<div class="weui-cells weui-cells_form">
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">金额</label></div>
        <div class="weui-cell__bd">
            <input id="money" class="weui-input" type="number" pattern="[0-9.]*" placeholder="0.00">
        </div>
    </div>
</div>
<div class="weui-cells__tips">你输入的金额是：<span id="money-text">0.00</span></div>

</body>
<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/format.js"></script>
<script>
    $("#money").on("input", function () {
        var money  = $(this).val();
        var format = formatMoney(money);
        $(this).val(format);
        $("#money-text").html(formatCurrency(money));
    });
</script>
</html>