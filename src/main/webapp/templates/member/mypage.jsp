<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<style>
    .checkout__title__custom {
      font-weight: 700;
      padding-bottom: 1rem;
    }
</style>

<head>
    <jsp:include page="../common/meta-data.jsp"/>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- Css Styles -->
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.3.1/css/all.css'>
    <link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="/css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="/css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="/css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="/css/style.css" type="text/css">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>

<!-- header begin -->
<jsp:include page="../common/header.jsp"/>
<!-- header end -->

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__text">
                    <h4>My Page</h4>
                    <div class="breadcrumb__links">
                        <a href="/">Home</a>
                        <span>My Page</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- mypage begin -->
<%--<div>--%>
<%--    <div>--%>
<%--        <h2>사용자 정보</h2>--%>
<%--    </div>--%>
<%--    <div>${memberInfo.id}</div>--%>
<%--    <div>${memberInfo.email}</div>--%>
<%--    <div>${memberInfo.name}</div>--%>
<%--    <div>${memberInfo.money}</div>--%>
<%--    <div>--%>
<%--        <h3>주소지</h3>--%>
<%--    </div>--%>
<%--    <c:forEach var="address" items="${memberInfo.address}">--%>
<%--        <li>--%>
<%--            <c:if test="${address.isDefault}">--%>
<%--                <div>--%>
<%--                    <h5>기본 배송지</h5>--%>
<%--                </div>--%>
<%--            </c:if>--%>
<%--            <c:if test="${!address.isDefault}">--%>
<%--                <div>--%>
<%--                    <h5>이외 주소지</h5>--%>
<%--                </div>--%>
<%--            </c:if>--%>
<%--            <div>${address.roadName}</div>--%>
<%--            <div>${address.addrDetail}</div>--%>
<%--            <div>${address.zipCode}</div>--%>
<%--        </li>--%>
<%--    </c:forEach>--%>
<%--    <div>--%>
<%--        <h3>쿠폰 목록</h3>--%>
<%--    </div>--%>
<%--    <c:forEach var="coupons" items="${memberInfo.coupons}">--%>
<%--        <li>--%>
<%--            <div>${coupons.id}</div>--%>
<%--            <div>${coupons.name}</div>--%>
<%--            <div>${coupons.discountPolicy}</div>--%>
<%--            <div>${coupons.discountValue}</div>--%>
<%--            <div>${coupons.status}</div>--%>
<%--        </li>--%>
<%--    </c:forEach>--%>
<%--</div>--%>

<section class="checkout spad">
    <div class="container">
        <div class="checkout__form">
<%--            <form id="order-form" action="" method="post">--%>
                <div class="row">
                    <div class="col-lg-8 col-md-6">
                        <h2 class="checkout__title">사용자 정보</h2>
                        <div class="checkout__input">
                            <h5 class="checkout__title__custom">이름</h5>
                            <input id="memberName" type="text" name="memberName" value="${memberInfo.name}" disabled>
                        </div>
                        <div class="checkout__input">
                            <h5 class="checkout__title__custom">이메일</h5>
                            <input id="memberEmail" type="text" name="memberEmail" value="${memberInfo.email}" disabled>
                        </div>

                        <form id="addressForm">
                            <c:forEach var="address" items="${memberInfo.address}" varStatus="loop">
                                <div>
                                    <label>
                                        <input type="radio" name="selectedAddress">
                                        <input type="text" name="selectedRoadName" value="${address.roadName}" hidden>
                                        <input type="text" name="selectedAddrDetail" value="${address.addrDetail}" hidden>
                                        <input type="text" name="selectedZipCode" value="${address.zipCode}" hidden>
                                            ${loop.index + 1}
                                    </label>
                                </div>
                            </c:forEach>
                        </form>

                        <div id="selectedAddressInfo">

                        </div>

<%--                        <div class="checkout__input">--%>
<%--                            <p>주소<span>*</span></p>--%>
<%--                            <input type="text" placeholder="도로명 주소" class="checkout__input__add"--%>
<%--                                   id="roadName"--%>
<%--                                   name="roadName"--%>
<%--                                   value="<c:if test="${defaultAddress.roadName != null}">${defaultAddress.roadName}</c:if>"--%>
<%--                                   required--%>
<%--                                   onclick="getDaumPostcode()" readonly--%>
<%--                                   oninvalid="this.setCustomValidity('도로명 주소를 입력해주세요.')"--%>
<%--                                   oninput="this.setCustomValidity('')" style="color: black;">--%>
<%--                            <input type="text" placeholder="상세 주소" class="checkout__input__add"--%>
<%--                                   id="addrDetail"--%>
<%--                                   name="addrDetail"--%>
<%--                                   value="<c:if test="${defaultAddress.addrDetail != null}">${defaultAddress.addrDetail}</c:if>"--%>
<%--                                   required--%>
<%--                                   onclick="getDaumPostcode()" readonly--%>
<%--                                   oninvalid="this.setCustomValidity('상세 주소를 입력해주세요.')"--%>
<%--                                   oninput="this.setCustomValidity('')" style="color: black;">--%>
<%--                        </div>--%>
<%--                        <div class="checkout__input">--%>
<%--                            <p>우편번호<span>*</span></p>--%>
<%--                            <input type="text" placeholder="우편번호" class="checkout__input__add"--%>
<%--                                   id="zipCode"--%>
<%--                                   name="zipCode"--%>
<%--                                   value="<c:if test="${defaultAddress.zipCode != null}">${defaultAddress.zipCode}</c:if>"--%>
<%--                                   required--%>
<%--                                   onclick="getDaumPostcode()" readonly--%>
<%--                                   oninvalid="this.setCustomValidity('우편번호를 입력해주세요.')"--%>
<%--                                   oninput="this.setCustomValidity('')" style="color: black;">--%>
<%--                        </div>--%>

                        <div class="checkout__input">
                            <h5 class="checkout__title__custom">쿠폰 목록</h5>
                            <select id="coupon" name="couponId" class="checkout__input__add">
                                <c:forEach var="coupon" items="${memberInfo.coupons}">
                                    <option id="${coupon.discountValue}" name="${coupon.discountPolicy}"
                                            value="${coupon.id}">${coupon.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

<%--                    <div class="col-lg-4 col-md-5">--%>
<%--                        <div class="checkout__order">--%>
<%--                            <h4 class="order__title">주문 목록</h4>--%>
<%--                            <div class="checkout__order__products">상품 정보<span>가격</span></div>--%>
<%--                            <ul class="checkout__total__products">--%>
<%--                                <li class="product-item">--%>
<%--                                    <input type="hidden" class="product-id" name="productId" value="${product.id}">--%>
<%--                                    <input type="hidden" class="product-name" name="productName"--%>
<%--                                           value="${product.name}">--%>
<%--                                    <input type="hidden" class="product-price" name="price"--%>
<%--                                           value="${product.price}">--%>
<%--                                    <input type="hidden" class="product-quantity" name="quantity"--%>
<%--                                           value="${productQuantity}">--%>
<%--                                    ${product.name} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${productQuantity}개<span--%>
<%--                                        class="product-price">${product.price}원</span>--%>
<%--                                </li>--%>
<%--                            </ul>--%>
<%--                            <ul class="checkout__total__all">--%>
<%--                                <li>할인 가격 <span id="discountPrice"></span></li>--%>
<%--                                <input type="hidden" id="totalPrice" name="totalPrice">--%>
<%--                                <li>총 가격 <span id="calculated-total"></span></li>--%>
<%--                            </ul>--%>
<%--                            <a id="payment-btn" href="#"><img src="/img/payments/payment_icon_yellow_large.png"--%>
<%--                                                              height="70"></a>--%>
<%--                        </div>--%>
<%--                    </div>--%>

                </div>
<%--            </form>--%>
        </div>
    </div>
</section>

<!-- mypage end -->

<!-- footer begin -->
<jsp:include page="../common/footer.jsp"/>
<!-- footer end -->

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
<script src="/js/mypage.js"></script>

</body>

</html>
