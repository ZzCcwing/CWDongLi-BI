package com.yupi.springbootinit.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;

//@Service
public class OpenAiApi {
    public static void main(String[] args) {
        String url = "https://api.openai.com/v1/chat/completions";
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("message", "用户的消息，请帮我分析");
        String json = JSONUtil.toJsonStr(hashMap);
        String result = HttpRequest.post(url)
                .header("Authorization", "Bearer 替换为你自己的key")
                .body(json)
                .execute()
                .body();

        // 最终结果


    }
}
