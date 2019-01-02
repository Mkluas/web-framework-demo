function request(options) {
    options = initOptions(options);

    if (options.loading) {
        $.showLoading(options.loadingText || '请求中');
    }

    $.ajax(options);
}

function initOptions(options) {

    if (options.loadingText) {
        options.loading = true;
    }

    if (!options.error) {
        options.error = function (err) {
            if (options.loading) {
                $.hideLoading();
            }
            if (err['responseJSON'] && err['responseJSON']['errCode']) {
                options.fail(err['responseJSON']);
            } else {
                $.alert('网路异常');
            }
        }
    }

    if (!options.fail) {
        options.fail = function (d) {
            $.alert(d.errMsg);
        }
    }

    var successCallback = options.success;

    options.success = function (d) {
        if (options.loading) {
            $.hideLoading();
        }

        if (d.errCode === 0) {
            successCallback && successCallback(d);
        } else {
            options.fail(d);
        }
    };

    return options;
}

function post(options) {
    options.type = "post";
    request(options);
}

function get(options) {
    options.type = "get";
    request(options);
}