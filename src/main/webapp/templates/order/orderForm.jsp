<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

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

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>

<!-- Header Section Begin -->
<jsp:include page="../common/header.jsp"/>
<!-- Header Section End -->

<script>
    const hostName = location.host;
    const queryParameters = new URLSearchParams(decodeURI(location.search));
    const errorMessage = queryParameters.get("errorMessage");
    if (errorMessage !== null) {
        Swal.fire({
            icon: 'error',
            title: "ERROR",
            text: errorMessage,
            footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
        }).then((result) => {
            window.location.replace("http://" + hostName);
        });
    }
</script>

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__text">
                    <h4>Direct Order</h4>
                    <div class="breadcrumb__links">
                        <a href="/">Home</a>
                        <a href="/">Product</a>
                        <span>Direct Order</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Checkout Section Begin -->
<section class="checkout spad">
    <div class="container">
        <div class="checkout__form">
            <form id="order-form" action="" method="post">
                <div class="row">
                    <div class="col-lg-8 col-md-6">
                        <h6 class="checkout__title">사용자 정보</h6>
                        <div class="checkout__input">
                            <p>이름<span></span></p>
                            <input id="memberName" type="text" name="memberName" value="${memberName}" disabled>
                        </div>
                        <div class="checkout__input">
                            <p>주소<span>*</span></p>
                            <input type="text" placeholder="도로명 주소" class="checkout__input__add"
                                   id="roadName"
                                   name="roadName"
                                   value="<c:if test="${defaultAddress.roadName != null}">${defaultAddress.roadName}</c:if>"
                                   required
                                   onclick="getDaumPostcode()" readonly
                                   oninvalid="this.setCustomValidity('도로명 주소를 입력해주세요.')"
                                   oninput="this.setCustomValidity('')" style="color: black;">
                            <input type="text" placeholder="상세 주소" class="checkout__input__add"
                                   id="addrDetail"
                                   name="addrDetail"
                                   value="<c:if test="${defaultAddress.addrDetail != null}">${defaultAddress.addrDetail}</c:if>"
                                   required
                                   onclick="getDaumPostcode()" readonly
                                   oninvalid="this.setCustomValidity('상세 주소를 입력해주세요.')"
                                   oninput="this.setCustomValidity('')" style="color: black;">
                        </div>
                        <div class="checkout__input">
                            <p>우편번호<span>*</span></p>
                            <input type="text" placeholder="우편번호" class="checkout__input__add"
                                   id="zipCode"
                                   name="zipCode"
                                   value="<c:if test="${defaultAddress.zipCode != null}">${defaultAddress.zipCode}</c:if>"
                                   required
                                   onclick="getDaumPostcode()" readonly
                                   oninvalid="this.setCustomValidity('우편번호를 입력해주세요.')"
                                   oninput="this.setCustomValidity('')" style="color: black;">
                        </div>
                        <div class="checkout__input">
                            <p>쿠폰 선택<span></span></p>
                            <select id="coupon" name="couponId" class="checkout__input__add"
                                    onchange="updateTotalPrice()">
                                <option value="">적용 안함</option>
                                <c:forEach var="coupon" items="${coupons}">
                                    <option id="${coupon.discountValue}" name="${coupon.discountPolicy}"
                                            value="${coupon.id}">${coupon.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-5">
                        <div class="checkout__order">
                            <h4 class="order__title">주문 목록</h4>
                            <div class="checkout__order__products">상품 정보<span>가격</span></div>
                            <ul class="checkout__total__products">
                                <li class="product-item">
                                    <input type="hidden" class="product-id" name="productId" value="${product.id}">
                                    <input type="hidden" class="product-name" name="productName"
                                           value="${product.name}">
                                    <input type="hidden" class="product-price" name="price"
                                           value="${product.price}">
                                    <input type="hidden" class="product-quantity" name="quantity"
                                           value="${productQuantity}">
                                    ${product.name} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${productQuantity}개<span
                                        class="product-price">${product.price}원</span>
                                </li>
                            </ul>
                            <ul class="checkout__total__all">
                                <li>할인 가격 <span id="discountPrice"></span></li>
                                <input type="hidden" id="totalPrice" name="totalPrice">
                                <li>총 가격 <span id="calculated-total"></span></li>
                            </ul>
                            <a id="payment-btn" href="#"><img src="/img/payments/payment_icon_yellow_large.png"
                                                              height="70"></a>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>

<!-- Checkout Section End -->

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
<script src="/js/orders/orderForm.js"></script>

</body>

</html>
