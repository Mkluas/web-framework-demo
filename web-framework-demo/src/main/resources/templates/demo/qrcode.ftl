<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>QRCode</title>
    <style>

        #qrcode {
            margin: 0 auto;
            width: 256px;
        }

    </style>
</head>
<body>
    <div id="qrcode"></div>
</body>
<script src="/static/js/jquery.min.js"></script>
<script src="/static/plugin/qrcode/qrcode.min.js"></script>
<script>
    $(function () {

        // 简单方式
//        new QRCode(document.getElementById('qrcode'), 'your content');

        // 设置参数方式 http://code.ciaoca.com/javascript/qrcode/
        var qrcode = new QRCode('qrcode', {
            text: 'your content',
            width: 256,
            height: 256,
            colorDark : '#000000',
            colorLight : '#ffffff',
            correctLevel : QRCode.CorrectLevel.H
        });

        // 使用 API
        qrcode.clear();
        qrcode.makeCode('new content');
    })
</script>
</html>