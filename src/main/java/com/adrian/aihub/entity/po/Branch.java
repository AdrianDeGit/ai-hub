package com.adrian.aihub.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分支机构表
 * </p>
 *
 * @author Adiran
 * @since 2025-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("branch")
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分支机构名称
     */
    private String name;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 营业时间
     */
    private String businessHours;

    /**
     * 状态：0-关闭，1-营业
     */
    private Integer status;


}
