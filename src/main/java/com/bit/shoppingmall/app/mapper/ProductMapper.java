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
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

  int getProductListTotalPage(int curPage);

  List<ProductListItemOfLike> selectProductListItemOfLike(List<Long> id);

  Product select(Long id);

  ProductDetail selectProductDetail(ProductDetailParameter input);

  List<ProductItemQuantity> selectProductInfo(List<Long> id);

  List<Product> selectAllProduct();

  List<ProductListItem> selectProductListOrderByPrice(Map<String, Object> map);

  int insert(Product product);

  int update(Product product);

  int delete(Long id);

  List<Category> selectCategory(Long id);

  int selectProductQuantity(Long id);

  List<ProductDetailForOrder> productDetailForOrder(Long id);

  List<ProductListItem> searchByWord(Map<String, Object> map);

  List<ProductListItem> searchSubCategoryProduct(Map<String, Object> map);
}
