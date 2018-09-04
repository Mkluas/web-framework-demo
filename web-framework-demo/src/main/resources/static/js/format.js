function formatMoney(money) {
    money = money + '';
    money = money.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
    money = money.replace(/^\./g,""); //验证第一个字符是数字
    money = money.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
    money = money.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    money = money.replace(/^(-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
    return +money;
}

/**
 * 保留两位小数
 * @param money 需要格式化的金额
 * @returns {*}
 */
function formatCurrency(money) {
    money = formatMoney(money);
    var text;
    var parts = money.split('.');
    if (parts.length === 1) {
        text = (parts[0].length === 0) ? '0.00' : money + '.00';
    } else {
        if (parts[1].length === 1){
            text = money + '0';
        } else {
            text = parts[0] + "." + parts[1].substring(0,2);
        }
    }
    return text;
}

/**
 * 检验手机号码格式
 * @param mobile
 * @returns {Array|{index: number, input: string}}
 */
function isMobile(mobile) {
    var pattern = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/
    return pattern.test(mobile);
}

function isEmail(email){
    var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    return pattern.test(email);
}