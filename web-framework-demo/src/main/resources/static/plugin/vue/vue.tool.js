Vue.filter('money', function(value) {
    var money = value;
    money = money + '';
    var parts = money.split('.');
    if (parts.length === 1) {
        return (money + '.00');
    } else if (parts[1].length === 1) {
        return (money + '0');
    }
    return value;
});

Vue.filter("img", function (value, w, h, q) {
    return value + "?imageView2/1/w/" + w + "/h/" + h + (q? ('/q/' + q) : '');
});

Vue.filter("short", function (value, len) {
    if (!len) {
        len = 10;
    }
   if (value.length <= len) {
       return value;
   } else {
       return value.substring(0, len) + '...';
   }
});

function parseTime(time, cFormat) {
    if (arguments.length === 0) {
        return null
    }
    if ((time + '').length === 10) {
        time = +time * 1000
    }
    const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
    var date;
    if (typeof time === 'object') {
        date = time
    } else {
        date = new Date(parseInt(time))
    }
    var formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay()
    };
    return format.replace(/{(y|m|d|h|i|s|a)+}/g, function(result, key) {
        var value = formatObj[key];
        if (key === 'a') return ['一', '二', '三', '四', '五', '六', '日'][value - 1];
        if (result.length > 0 && value < 10) {
            value = '0' + value
        }
        return value || 0
    });
}

function parseDate(time) {
    return parseTime(time, '{y}-{m}-{d}');
}

function parseDateTime(time) {
    return parseTime(time, '{m}-{d} {h}:{i}');
}

function remainTime(time) {
    function format(num) {
        var n = parseInt(num);
        if (n < 10) {
            return '0' + n;
        }
        return n + '';
    }
    var h = time / 3600;
    var m = (time % 3600) / 60;
    var s = time % 60;
    return "即将开抢 " +  format(h) + ":" + format(m) + ":" + format(s);
}


Vue.filter("time", parseTime);
Vue.filter("date", parseDate);
Vue.filter("datetime", parseDateTime);
Vue.filter("remaintime", remainTime);

Vue.mixin({
    methods : {
        hello : function (name) {
            return "hello, " + name;
        }
    }
});

