package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.dto.product.ParameterForProductList;
import com.bit.shoppingmall.app.dto.product.ParameterForSearchProductForKeyword;
import com.bit.shoppingmall.app.dto.product.ParameterForSubCategorySearch;
import com.bit.shoppingmall.app.dto.product.ProductDetail;
import com.bit.shoppingmall.app.dto.product.ProductDetailParameter;
import com.bit.shoppingmall.app.dto.product.ProductItemQuantity;
import com.bit.shoppingmall.app.dto.product.ProductListItem;
import com.bit.shoppingmall.app.dto.product.ProductListItemOfLike;
import com.bit.shoppingmall.app.dto.product.response.ProductDetailForOrder;
import com.bit.shoppingmall.app.entity.Product;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

  int getProductListTotalPage(int curPage);

  List<ProductListItemOfLike> selectProductListItemOfLike(List<Long> id);

  Optional<Product> select(Long id);

  Optional<ProductDetail> selectProductDetail(ProductDetailParameter input);

  List<ProductItemQuantity> selectProductInfo(List<Long> id);

  List<Product> selectAllProduct();

  List<ProductListItem> selectProductListOrderByPrice(ParameterForProductList parameter);

  int insert(Product product);

  int update(Product product);

  int delete(Long id);

  int selectProductQuantity(Long id);

  List<ProductListItem> searchByWord(ParameterForSearchProductForKeyword parameter);

  List<ProductListItem> searchSubCategoryProduct(
      ParameterForSubCategorySearch parameterForSubCategorySearch);

  ProductDetailForOrder selectProductDetailForOrder(Long id);
  int selectProductListOrderByPriceTotalCount(ParameterForProductList parameter);

  int searchByWordTotalCount(ParameterForSearchProductForKeyword parameter);

  int searchSubCategoryProductTotalCount(
      ParameterForSubCategorySearch parameterForSubCategorySearch);
}
