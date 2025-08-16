package com.adrian.aihub.entity.po;

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
 * 咨询预约表
 * </p>
 *
 * @author Adiran
 * @since 2025-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("consultation")
public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 咨询产品ID
     */
    private Integer productId;

    /**
     * 咨询产品名称
     */
    private String productName;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 咨询类型：产品咨询、技术支持、商务合作等
     */
    private String consultationType;

    /**
     * 分支机构ID
     */
    private Integer branchId;

    /**
     * 分支机构名称
     */
    private String branchName;

    /**
     * 期望咨询时间
     */
    private LocalDateTime preferredTime;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 状态：0-待处理，1-已联系，2-已完成，3-已取消
     */
    private Integer status;

    private LocalDateTime createTime;


}
