package com.mzc.Auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response <T> {
    private String resultCode;

    private T result;

    // error 발생시
    public static Response<Void> error(String resultCode){
        return new Response<Void>(resultCode, null);
    }

    public static Response<Void> success() {
        return new Response<Void>("SUCCESS", null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<T>("SUCCESS", result);
    }

    public String toStream() {
        if (result == null) {
            return "{" +
                    "\"resultCode\":" + "\"" + resultCode + "\"," +
                    "\"result\":" + null +
                    "}";
        }
        return "{" +
                "\"resultCode\":" + "\"" + resultCode + "\"," +
                "\"result\":" + "\"" + result + "\"," +
                "}";
    }

}
