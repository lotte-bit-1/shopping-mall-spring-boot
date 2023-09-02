<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zxx">

<head>
    <jsp:include page="../common/meta-data.jsp"/>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="/css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="/css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="/css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="/css/style.css" type="text/css">
</head>

<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>
<!-- Header Section Begin -->
<jsp:include page="../common/header.jsp"/>
<!-- Header Section End -->

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__text">
                    <h4>Shop</h4>
                    <div class="breadcrumb__links">
                        <a href="main.bit">Home</a>
                        <a href="/product.bit?view=shop&curPage=1&sort=PRICE_ASC">Shop</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Shop Section Begin -->
<section class="shop spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <div class="shop__sidebar">
                    <%--  category --%>
                    <div class="shop__sidebar__accordion">
                        <div class="accordion" id="accordionExample">
                            <div class="card">
                                <div class="card-heading">
                                    <a data-toggle="collapse"
                                       data-target="#collapseOne">Categories</a>
                                </div>
                                <div id="collapseOne" class="collapse show"
                                     data-parent="#accordionExample">
                                    <div class="card-body">
                                        <div class="shop__sidebar__categories">
                                            <ul class="nice-scroll">
                                                <c:forEach var="category" items="${categories}">
                                                    <li>
                                                        <a href="/product/1/category?keyword=${category.name}">${category.name}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-9">
                <div class="shop__product__option">
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6">
                            <div class="shop__product__option__left">
                            </div>
                        </div>
                    </div>
                </div>
                <c:choose>
                    <c:when test="${not empty error}">
                        <div class="d-flex justify-content-center">검색된 상품이 없습니다.</div>
                    </c:when>
                    <c:otherwise>
                        <%--  product item list --%>
                        <div class="row" id="common-parent-element">
                            <c:forEach var="product" items="${products}">
                                <%-- each item --%>
                                <div class="col-lg-4 col-md-6 col-sm-6">
                                    <div class="product__item">
                                        <div class="product__item__pic set-bg"
                                             data-setbg="${product.url}">
                                            <ul class="product__hover">
                                                <c:if test="${!product.isLiked}">
                                                    <li>
                                                        <a href="#" class="likes-btn"
                                                           data-product-id="${product.id}"
                                                           data-login-info="${loginMember}"
                                                        ><img
                                                                src="/img/icon/heart.png"
                                                                alt=""></a>
                                                    </li>
                                                </c:if>
                                                <c:if test="${product.isLiked}">
                                                    <li>
                                                        <a href="#" class="likes-cancel-btn"
                                                           data-product-id="${product.id}"
                                                           data-login-info="${loginMember}"><img
                                                                src="/img/icon/fill_heart.png"
                                                                alt=""></a>
                                                    </li>
                                                </c:if>
                                                <li>
                                                    <a href="/product.bit?view=shop-detail&productId=${product.id}"><img
                                                            src="/img/icon/search.png" alt=""></a>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="product__item__text">
                                            <h6>${product.name}</h6>
                                            <a href="#" data-product-id="${product.id}"
                                               class="add-cart">+ Add To
                                                Cart</a>
                                                <%-- price--%>
                                            <h5>${product.price}원</h5>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="product__pagination">
                                    <c:if test="${page.currentPage > 1}">
                                        <a class="mr-3"
                                           href="/product.bit?view=shop&curPage=${page.currentPage - 1}&sort=PRICE_ASC">PREV</a>
                                    </c:if>

                                    <c:set var="startPage"
                                           value="${page.currentPage - 2}"/>
                                    <c:set var="endPage"
                                           value="${page.currentPage + 2}"/>

                                    <c:if test="${startPage < 1}">
                                        <c:set var="startPage" value="1"/>
                                        <c:set var="endPage" value="5"/>
                                    </c:if>

                                    <c:if test="${endPage > page.totalPage}">
                                        <c:set var="endPage"
                                               value="${page.totalPage}"/>
                                        <c:set var="startPage"
                                               value="${page.totalPage - 4}"/>
                                        <c:choose>
                                            <c:when test="${startPage < 1}">
                                                <c:set var="startPage" value="1"/>
                                            </c:when>
                                        </c:choose>
                                    </c:if>
                                    <c:forEach begin="${startPage}" end="${endPage}" var="p">
                                        <c:choose>
                                            <c:when test="${p == page.currentPage}">
                                                <a id="curPage">${p}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/product.bit?view=shop&curPage=${p}&sort=PRICE_ASC">${p}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>

                                    <c:if test="${page.currentPage < page.totalPage}">
                                        <a href="/product.bit?view=shop&curPage=${page.currentPage + 1}&sort=PRICE_ASC">NEXT</a>
                                    </c:if>
                                </div>

                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</section>
<!-- Shop Section End -->
<!-- Footer Section Begin -->
<jsp:include page="../common/footer.jsp"/>
<!-- Footer Section End -->

<jsp:include page="../common/search.jsp"/>

<!-- Js Plugins -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.nice-select.min.js"></script>
<script src="/js/jquery.nicescroll.min.js"></script>
<script src="/js/jquery.magnific-popup.min.js"></script>
<script src="/js/jquery.countdown.min.js"></script>
<script src="/js/jquery.slicknav.js"></script>
<script src="/js/mixitup.min.js"></script>
<script src="/js/owl.carousel.min.js"></script>
<script src="/js/main.js"></script>
<script src="/js/likes.js"></script>

</body>

</html>