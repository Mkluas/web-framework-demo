function request(vue, options) {


    options = initOptions(vue, options);

    if (options.loading) {
        const loading = vue.$loading({
            lock: true,
            text: options.loadingText || 'Loading',
            spinner: 'el-icon-loading',
            // background: 'rgba(0, 0, 0, 0.7)'
        });
    }

    $.ajax(options);
}

function initOptions(vue, options) {

    if (options.loadingText) {
        options.loading = true;
    }

    if (!options.error) {
        options.error = function (err) {
            if (options.loading) {
                vue.$loading().close()
            }
            if (err['responseJSON'] && err['responseJSON']['errCode']) {
                options.fail(err['responseJSON']);
            } else {
                vue.$message.error('网路异常');
            }

        }
    }

    options.error = function (err) {
        options.error(err);
        options.complete && options.complete();
    };

    if (!options.fail) {
        options.fail = function (d) {
            vue.$message.error(d.errMsg);
        }
    }

    var successCallback = options.success;

    options.success = function (d) {
        if (options.loading) {
            vue.$loading().close()
        }

        if (d.errCode === 0) {
            if (successCallback) {
                successCallback(d);
            } else {
                vue.$message.success(options.successText || '操作成功');
            }
        } else {
            options.fail(d);
        }

        options.complete && options.complete();
    };

    return options;
}

function post(vue, options) {
    options.type = "post";
    request(vue, options);
}

function get(vue, options) {
    options.type = "get";
    request(vue, options);
}

function remove(vue, options) {
    vue.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(function () {
        post(vue, options);
    }).catch(function () {
        options.cancel && options.cancel();
    });
}