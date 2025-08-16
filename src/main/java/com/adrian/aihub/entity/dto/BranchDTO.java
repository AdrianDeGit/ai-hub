package com.adrian.aihub.entity.dto;

import lombok.Data;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.entity.dto
 * @Date: 2025/8/16 18:16
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@Data
public class BranchDTO {
    @ToolParam(required = false, description = "所在省份：北京市、上海市、广东省、浙江省、四川省、湖北省、陕西省、江苏省、重庆市等")
    private String province;

    @ToolParam(required = false, description = "所在城市：北京、上海、深圳、杭州、广州、成都、武汉、西安、南京、重庆等")
    private String city;

    @ToolParam(required = false, description = "地址关键词，支持模糊查询")
    private String address;
}
