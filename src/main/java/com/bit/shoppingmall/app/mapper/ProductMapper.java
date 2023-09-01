package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.dto.product.ProductDetail;
import com.bit.shoppingmall.app.dto.product.ProductDetailParameter;
import com.bit.shoppingmall.app.dto.product.ProductItemQuantity;
import com.bit.shoppingmall.app.dto.product.ProductListItem;
import com.bit.shoppingmall.app.dto.product.ProductListItemOfLike;
import com.bit.shoppingmall.app.dto.product.ProductListParameter;
import com.bit.shoppingmall.app.dto.product.response.ProductDetailForOrder;
import com.bit.shoppingmall.app.entity.Product;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;

@Mapper
public interface ProductMapper {

  int getProductListTotalPage(int curPage);

  List<ProductListItemOfLike> selectProductListItemOfLike(List<Long> id);

  Optional<Product> select(Long id);

  Optional<ProductDetail> selectProductDetail(ProductDetailParameter input);

  List<ProductItemQuantity> selectProductInfo(List<Long> id);

  List<Product> selectAllProduct();

  List<ProductListItem> selectProductListOrderByPrice(ProductListParameter parameter);

  int insert(Product product);

  int update(Product product);

  int delete(Long id);

  int selectProductQuantity(Long id);

  List<ProductListItem> searchByWord(Map<String, Object> map);

  List<ProductListItem> searchSubCategoryProduct(Map<String, Object> map);

  ProductDetailForOrder selectProductDetailForOrder(Long id);
}
