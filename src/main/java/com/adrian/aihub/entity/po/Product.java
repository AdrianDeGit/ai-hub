package com.adrian.aihub.entity.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品/服务表
 * </p>
 *
 * @author Adiran
 * @since 2025-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 产品/服务名称
     */
    private String name;

    /**
     * 产品分类
     */
    private String category;

    /**
     * 子分类
     */
    private String subCategory;

    /**
     * 产品描述
     */
    private String description;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 状态：0-下架，1-上架
     */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
