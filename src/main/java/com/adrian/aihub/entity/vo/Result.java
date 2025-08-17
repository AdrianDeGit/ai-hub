package com.adrian.aihub.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.entity.vo
 * @Date: 2025/8/17 17:36
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@Data
@NoArgsConstructor
public class Result {
    private Integer ok;
    private String msg;

    private Result(Integer ok, String msg) {
        this.ok = ok;
        this.msg = msg;
    }

    public static Result ok() {
        return new Result(1, "ok");
    }

    public static Result fail(String msg) {
        return new Result(0, msg);
    }
}