package com.demo.core.util;

import com.laiai.core.Result;
import com.laiai.core.enums.ResultCode;
import com.laiai.core.exception.FeignException;

public class FeignUtil {

    public static void handleResult(Result result){
        if(!ResultCode.SUCCESS.getCode().equals(result.getCode())){
            throw new FeignException(result.getCode(),result.getMsg());
        }
    }
}
