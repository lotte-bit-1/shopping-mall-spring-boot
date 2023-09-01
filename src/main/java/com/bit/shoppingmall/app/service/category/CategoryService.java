package com.bit.shoppingmall.app.service.category;

import com.bit.shoppingmall.app.entity.Category;
import com.bit.shoppingmall.app.exception.category.CategoryNotFoundException;
import com.bit.shoppingmall.app.mapper.CategoryMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
  private final CategoryMapper categoryMapper;

  public List<Category> getFirstLevelCategory() {
    List<Category> categories = categoryMapper.selectFirstCategory();
    if (categories.isEmpty()) throw new CategoryNotFoundException();
    return categories;
  }
}
