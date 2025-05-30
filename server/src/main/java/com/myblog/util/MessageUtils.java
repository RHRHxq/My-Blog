package com.myblog.util;

import com.alibaba.fastjson.JSON;
import com.myblog.entity.ResultMessage;


public class MessageUtils {

    public static String getMessage(boolean isSystemMessage,Long fromUserId,Object message){
            ResultMessage result= new ResultMessage();
            result.setSystem(isSystemMessage);
            result.setMessage(message);
            if(fromUserId!=null){
                result.setFromUserId(fromUserId);
            }
            return JSON.toJSONString(result);
    }
}

