package com.adrian.aihub.entity.dto;

import lombok.Data;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.List;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.entity.dto
 * @Date: 2025/8/16 17:47
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@Data
public class ProductDTO {
    @ToolParam(required = false, description = "产品分类：软件服务、教育培训、技术服务、咨询服务")
    private String category;

    @ToolParam(required = false, description = "子分类：AI客服、电商培训、网站建设、数据分析、APP开发、网络营销、设计培训、云服务、财务咨询、管理培训")
    private String subCategory;

    @ToolParam(required = false, description = "排序方式")
    private List<Sort> sorts;

    @Data
    public static class Sort {
        @ToolParam(required = false, description = "排序字段: price")
        private String field;
        @ToolParam(required = false, description = "是否是升序: true/false")
        private Boolean asc;
    }
}
