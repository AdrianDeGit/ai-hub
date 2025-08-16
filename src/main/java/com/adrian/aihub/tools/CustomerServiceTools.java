package com.adrian.aihub.tools;

import com.adrian.aihub.entity.dto.BranchDTO;
import com.adrian.aihub.entity.dto.ProductDTO;
import com.adrian.aihub.entity.po.Branch;
import com.adrian.aihub.entity.po.Consultation;
import com.adrian.aihub.entity.po.Product;
import com.adrian.aihub.service.IBranchService;
import com.adrian.aihub.service.IConsultationService;
import com.adrian.aihub.service.IProductService;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Project: ai-hub
 * @Package: com.adrian.aihub.tools
 * @Date: 2025/8/16 18:17
 * @Author: Adrian
 * @Version: V1.0
 * @Description:
 */
@RequiredArgsConstructor
@Component
public class CustomerServiceTools {

    private final IProductService productService;
    private final IBranchService branchService;
    private final IConsultationService consultationService;

    @Tool(description = "根据条件查询产品/服务")
    public List<Product> queryProduct(@ToolParam(required = false, description = "产品查询条件") ProductDTO productDTO) {
        QueryChainWrapper<Product> wrapper = productService.query();
        wrapper
                .eq(productDTO.getCategory() != null, "category", productDTO.getCategory())
                .eq(productDTO.getSubCategory() != null, "sub_category", productDTO.getSubCategory())
                .eq("status", 1);
        if (productDTO.getSorts() != null) {
            for (ProductDTO.Sort sort : productDTO.getSorts()) {
                wrapper.orderBy(true, sort.getAsc(), sort.getField());
            }
        }
        return wrapper.list();
    }

    @Tool(description = "根据条件查询分支机构，如果不提供查询条件则返回所有分支机构")
    public List<Branch> queryBranches(@ToolParam(required = false, description = "分支机构查询条件") BranchDTO branchDTO) {
        QueryChainWrapper<Branch> wrapper = branchService.query();

        // 如果提供了查询条件，则按条件筛选
        if (branchDTO != null) {
            wrapper
                    .eq(branchDTO.getProvince() != null, "province", branchDTO.getProvince())
                    .eq(branchDTO.getCity() != null, "city", branchDTO.getCity())
                    .like(branchDTO.getAddress() != null, "address", branchDTO.getAddress())
                    .eq("status", 1);
        } else {
            // 如果没有提供查询条件，返回所有营业中的分支机构
            wrapper.eq("status", 1);
        }

        return wrapper.list();
    }

    @Tool(description = "生成咨询预约单,并返回生成的预约单号")
    public String generateConsultation(
            @ToolParam(required = true, description = "咨询产品名称") String productName,
            @ToolParam(required = true, description = "客户姓名") String customerName,
            @ToolParam(required = true, description = "联系电话") String contactPhone,
            @ToolParam(required = true, description = "联系邮箱") String contactEmail,
            @ToolParam(required = true, description = "咨询类型") String consultationType,
            @ToolParam(required = true, description = "分支机构名称") String branchName,
            @ToolParam(required = true, description = "咨询时间") String preferredTime,
            @ToolParam(required = false, description = "备注") String remark) {
        Consultation consultation = new Consultation();
        consultation.setProductName(productName);
        consultation.setCustomerName(customerName);
        consultation.setContactPhone(contactPhone);
        consultation.setContactEmail(contactEmail);
        consultation.setConsultationType(consultationType);
        consultation.setBranchName(branchName);
        consultation.setPreferredTime(preferredTime != null ? LocalDateTime.parse(preferredTime) : null);
        consultation.setRemark(remark);
        consultation.setStatus(0);
        consultationService.save(consultation);
        return String.valueOf(consultation.getId());
    }
}