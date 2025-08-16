package com.adrian.aihub.service.impl;

import com.adrian.aihub.entity.po.Product;
import com.adrian.aihub.mapper.ProductMapper;
import com.adrian.aihub.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品/服务表 服务实现类
 * </p>
 *
 * @author Adiran
 * @since 2025-08-16
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
