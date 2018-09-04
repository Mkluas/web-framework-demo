var _createClass = function () {
    function defineProperties(target, props) {
        for (var i = 0; i < props.length; i++) {
            var descriptor = props[i];
            descriptor.enumerable = descriptor.enumerable || false;
            descriptor.configurable = true;
            if ("value" in descriptor)
                descriptor.writable = true;
            Object.defineProperty(target, descriptor.key, descriptor);
        }
    }
    return function (Constructor, protoProps, staticProps) {
        if (protoProps)
            defineProperties(Constructor.prototype, protoProps);
        if (staticProps)
            defineProperties(Constructor, staticProps);
        return Constructor;
    };
}();

class QiniuUploader {

    constructor(builder) {
        console.log(builder);
        console.log(builder.getToken)
    }

    static get Builder() {
        class Builder {
            constructor() {
                this._multiple = false;
            }

            token(_token) {
                this._token = _token;
                return this;
            }

            elementId(_elementId) {
                this._elementId = _elementId;
                return this;
            }

            key(_key) {
                this._key = _key;
                return this;
            }

            next(_nextCb) {
                this._nextCb = _nextCb;
                return this;
            }

            error(_errorCb) {
                this._errorCb = _errorCb;
                return this;
            }

            complete(_completeCb) {
                this._completeCb = _completeCb;
                return this;
            }

            observer(_observer) {
                this._observer = _observer;
                return this;
            }

            multiple() {
                this._multiple = true;
                return this;
            }

            interceptor(_interceptor) {
                this._interceptor = _interceptor
                return this;
            }

            getObserver() {
                if (this._observer) {
                    return this._observer;
                }
                return {
                    next: this._nextCb,
                    error: this._errorCb || function (e) {
                        console.error(e.toString())
                    },
                    complete: this._completeCb || function (r) {
                        alert("上传成功");
                    }
                }
            }

            checkParameter() {
                const trigger = document.getElementById(this._elementId);
                if (!trigger) {
                    console.error("Can't found the element by id: " + this._elementId);
                    return false;
                }
                if (!this._token || this._token.length < 1) {
                    console.error("token must not be empty");
                    return false;
                }
                return true;
            }

            build() {
                // return new QiniuUploader(this)

                if (!this.checkParameter()) {
                    return;
                }

                const token = this._token;
                const key = this._key;
                const observer = this.getObserver();
                const multiple = this._multiple;
                const interceptor = this._interceptor || {};

                const input=document.createElement("input");
                input.type = 'file';
                input.style.display="none";
                if (multiple) {
                    input.multiple = "multiple";
                }

                input.addEventListener("change", function () {
                    if (this.files.length < 1) {
                        console.debug("Not select file, do nothing");
                        return;
                    }
                    if (interceptor.beforeUpload && !interceptor.beforeUpload(this.files)) {
                        return;
                    }
                    for (let i=0; i < this.files.length; i++) {
                        const file = this.files[i];
                        if (interceptor.beforeEachUpload && !interceptor.beforeEachUpload(file)) {
                            continue;
                        }
                        const uploader = qiniu.upload(file, key, token);
                        uploader.subscribe(observer);
                    }
                });

                document.getElementById(this._elementId).addEventListener("click", function () {
                    input.click();
                });
            }
        }
        return Builder;
    }

};