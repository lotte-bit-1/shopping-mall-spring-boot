package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.dto.product.ProductDetail;
import com.bit.shoppingmall.app.dto.product.ProductDetailParameter;
import com.bit.shoppingmall.app.dto.product.ProductItemQuantity;
import com.bit.shoppingmall.app.dto.product.ProductListItem;
import com.bit.shoppingmall.app.dto.product.ProductListItemOfLike;
import com.bit.shoppingmall.app.dto.product.response.ProductDetailForOrder;
import com.bit.shoppingmall.app.entity.Category;
import com.bit.shoppingmall.app.entity.Product;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

  int getTotalPage(int curPage);

  List<ProductListItemOfLike> selectProductListItemOfLike(List<Long> id);

  Optional<Product> select(Long id);

  ProductDetail selectDetail(ProductDetailParameter input);

  List<ProductItemQuantity> selectOne(List<Long> id);

  List<Product> selectAll();

  List<ProductListItem> sortByPriceDesc(Map<String, Object> map);

  List<ProductListItem> sortByDate(Map<String, Object> map);

  List<ProductListItem> sortByPrice(Map<String, Object> map);

  int insert(Product product);

  int update(Product product);

  int delete(Long id);

  Category getCategory(Long id);

  int checkQuantity(Long id);

  List<ProductDetailForOrder> productDetailForOrder(Long id);

  List<ProductListItem> searchByWord(Map<String, Object> map);

  List<ProductListItem> searchSubCategoryProduct(Map<String, Object> map);
}
