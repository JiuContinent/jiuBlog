package com.jiu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiu.domain.ResponseResult;
import com.jiu.domain.entity.Category;
import org.springframework.stereotype.Service;


/**
 * 分类表(SgCategory)表服务接口
 *
 * @author makejava
 * @since 2022-06-04 17:59:07
 */
@Service
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

