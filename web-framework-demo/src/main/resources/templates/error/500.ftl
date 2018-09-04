<html>
<header>
    <style>
        .error {
            max-width: 720px;
            margin: 30px auto;
        }

        .error-header {
            font-size: 32px;
            text-align: center;
            margin: 0 0 40px;
        }
    </style>
</header>
<body>
    <div class="error">
        <p class="error-header">Ops, Internal Server Error...</p>
        <div class="error-body">
            <h4>请求路径</h4>
            <p>${requestUrl}</p>

            <h4>错误</h4>
            <p><label style="color: red">[${errCode}] : ${errMsg}</label></p>

            <#if parameters??>
            <h4>请求参数</h4>
            <p><#if parameters?length gt 1>${parameters}<#else >无</#if></p>
            </#if>

            <#if headers??>
            <h4>请求头部</h4>
            <p>${headers}</p>
            </#if>

            <#if cookies??>
            <h4>Cookies</h4>
            <p><#if cookies?length gt 1>${cookies}<#else >无</#if></p>
            </#if>
        </div>
    </div>
</body>
</html>