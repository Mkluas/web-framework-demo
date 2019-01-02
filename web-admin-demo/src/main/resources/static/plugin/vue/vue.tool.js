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
    if (!value) {
        return "";
    }
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


function timeago(dateTimeStamp){   //dateTimeStamp是一个时间毫秒，注意时间戳是秒的形式，在这个毫秒的基础上除以1000，就是十位数的时间戳。13位数的都是时间毫秒。
    dateTimeStamp = dateTimeStamp * 1000;
    var minute = 1000 * 60;      //把分，时，天，周，半个月，一个月用毫秒表示
    var hour = minute * 60;
    var day = hour * 24;
    var week = day * 7;
    var halfamonth = day * 15;
    var month = day * 30;
    var now = new Date().getTime();   //获取当前时间毫秒
    var diffValue = now - dateTimeStamp;//时间差

    if(diffValue < 0){
        return;
    }
    var minC = diffValue/minute;  //计算时间差的分，时，天，周，月
    var hourC = diffValue/hour;
    var dayC = diffValue/day;
    var weekC = diffValue/week;
    var monthC = diffValue/month;

    if(monthC >= 1 && monthC <= 3){
        result = " " + parseInt(monthC) + "月前"
    }else if(weekC >= 1 && weekC < 5){
        result = " " + parseInt(weekC) + "周前"
    }else if(dayC >= 1 && dayC < 7){
        result = " " + parseInt(dayC) + "天前"
    }else if(hourC >= 1 && hourC < 25){
        result = " " + parseInt(hourC) + "小时前"
    }else if(minC >= 1 && minC < 60){
        result =" " + parseInt(minC) + "分钟前"
    }else if(diffValue >= 0 && diffValue <= minute){
        result = "刚刚"
    }else {
        var datetime = new Date();
        datetime.setTime(dateTimeStamp);
        var Nyear = datetime.getFullYear();
        var Nmonth = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var Ndate = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        var Nhour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
        var Nminute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
        var Nsecond = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
        result = Nyear + "-" + Nmonth + "-" + Ndate
    }
    return result;
}


Vue.filter("time", parseTime);
Vue.filter("date", parseDate);
Vue.filter("datetime", parseDateTime);
Vue.filter("remaintime", remainTime);
Vue.filter("timeago", timeago);

// 注册一个全局自定义指令 `v-focus`
Vue.directive('focus', {
    // 当被绑定的元素插入到 DOM 中时……
    inserted: function (el, {value}) {
        // 聚焦元素
        if (value) {
            el.focus()
        }
    }
})

Vue.mixin({
    methods : {
        hello : function (name) {
            return "hello, " + name;
        }
    }
});

