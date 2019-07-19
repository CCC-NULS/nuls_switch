package io.nuls.nulsswitch.constant;

import io.nuls.nulsswitch.web.exception.ErrorCode;

/**
 * @Author: qinyifeng
 * @Time: 2019-07-19 17:48
 * @Description: 公共错误码
 */
public interface CommonErrorCode {

    ErrorCode SUCCESS = ErrorCode.init(10000);
    ErrorCode FAILED = ErrorCode.init(10001);
    ErrorCode SYS_UNKOWN_EXCEPTION = ErrorCode.init(10002);
    ErrorCode PARAMETER_ERROR = ErrorCode.init(10003);
    ErrorCode PARAMETER_NULL = ErrorCode.init(10004);
    ErrorCode DATA_NOT_FOUND = ErrorCode.init(10005);
    ErrorCode DB_SAVE_ERROR = ErrorCode.init(10006);
    ErrorCode DB_QUERY_ERROR = ErrorCode.init(10007);
    ErrorCode SIGNATURE_ERROR = ErrorCode.init(10008);
}
