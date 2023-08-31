package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.dto.category.response.CategoryHierarchy;
import com.bit.shoppingmall.app.dto.category.response.SubCategory;
import com.bit.shoppingmall.app.dto.product.ProductListItem;
import com.bit.shoppingmall.app.entity.Category;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    Category select(Long id);

    List<CategoryHierarchy> selectAll();

    int insert(Category category);

    int update(Category category);

    int delete(Long id);

    List<Category> firstCategory();

    List<ProductListItem> searchProductByCategory(Map<String, Object> map);

    List<SubCategory> selectSubcategory(String keyword);


}
