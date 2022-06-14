package com.jiu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiu.constants.SystemConstants;
import com.jiu.domain.ResponseResult;
import com.jiu.domain.entity.Article;
import com.jiu.domain.entity.Category;
import com.jiu.domain.vo.CategoryVo;
import com.jiu.mapper.CategoryMapper;
import com.jiu.service.ArticleService;
import com.jiu.service.CategoryService;
import com.jiu.utils.BeanCopyUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(SgCategory)表服务实现类
 *
 * @author makejava
 * @since 2022-06-04 17:59:07
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章状态是已发布的文章
        LambdaQueryWrapper<Article> articleQuery = new LambdaQueryWrapper<>();
        articleQuery.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleQuery);
        //获取文章的分类id并且去重(使用String流的方式)
        Set<Long> categoryId = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表 并且是正常状态下的分类
        List<Category> categories = listByIds(categoryId);
        categories.stream().filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装io
        BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return null;
    }
}

