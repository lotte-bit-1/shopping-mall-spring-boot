<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zxx">

<head>
    <jsp:include page="../common/meta-data.jsp"/>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="css/style.css" type="text/css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<body>
<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>

<script>
    const hostName = location.host;
    const queryParameters = new URLSearchParams(decodeURI(location.search));
    const errorMessage = queryParameters.get("errorMessage");
    const orderId = queryParameters.get("orderId");
    if (errorMessage !== null) {
        Swal.fire({
            icon: 'error',
            title: "ERROR",
            text: errorMessage,
            footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
        }).then((result) => {
            window.location.replace("http://" + hostName + "/order.bit?view=detail&cmd=get&orderId=" + orderId);
        });
    }
</script>

<!-- Header Section Begin -->
<jsp:include page="../common/header.jsp"/>
<!-- Header Section End -->

<!-- Modal Begin -->

<!-- Modal End -->

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__text">
                    <h4>Order Details</h4>
                    <div class="breadcrumb__links">
                        <a href="/main.bit">Home</a>
                        <a href="/order.bit?view=list&cmd=get">Order List</a>
                        <span>Order Details</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Breadcrumb Section End -->

<!-- Shopping Cart Section Begin -->
<section class="shopping-cart spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-8">
                <div class="shopping__cart__table">
                    <table>
                        <thead>
                        <tr>
                            <th>상품 정보</th>
                            <th>수량</th>
                            <th>총 가격</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="product" items="${products}">
                            <tr>
                                <td class="product__cart__item">
                                    <div class="product__cart__item__pic">
                                        <a href="/product.bit?view=shop-detail&productId=${product.productId}">
                                            <img src="${product.thumbnailUrl}" width="80" height="80" alt="">
                                        </a>
                                    </div>
                                    <div class="product__cart__item__text">
                                        <h6>${product.productName}</h6>
                                        <h5>${product.price}원</h5>
                                    </div>
                                </td>
                                <td class="quantity__item">
                                    <div class="quantity">
                                        <h5>${product.quantity}개</h5>
                                    </div>
                                </td>
                                <c:set var="rowTotalPrice" value="${product.price * product.quantity}"/>
                                <td class="cart__price"><c:out value="${rowTotalPrice}"/>원</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-lg-4">
                <button onclick="open_delivery(`${delivery.roadName}`,`${delivery.addrDetail}`, `${delivery.zipCode}`, `${delivery.deliveryStatus.message}`)"
                        class="primary-btn">배송 조회
                </button>
                <br/>
                <br/>
                <div class="cart__total">
                    <h6><b>주문 상세정보</b></h6>
                    <ul>
                        <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${productOrderDetail.orderDate}"
                                       var="parsedOrderDate"/>
                        <li>주문 날짜 <span><fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${parsedOrderDate}"/></span>
                        </li>
                        <li>주문 상태 <span>${productOrderDetail.orderStatus.getMessage()}</span></li>
                        <li>총 가격 <span>${productOrderDetail.getTotalPrice()}원</span></li>
                        <li>할인 가격
                            <span>
                                <c:choose>
                                    <c:when test="${productOrderDetail.getDiscountPrice() == 0}">
                                        ${productOrderDetail.getDiscountPrice()}원
                                    </c:when>
                                    <c:otherwise>
                                        -${productOrderDetail.getDiscountPrice()}원
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </li>
                        <li>결제 금액 <span>${payment.actualAmount}원</span></li>
                        <li>결제 종류 <span>${payment.paymentType.getMessage()}</span></li>
                    </ul>
                    <c:choose>
                        <c:when test="${delivery.deliveryStatus.name() eq 'PROCESSING'}">
                            <a id="orderCancelLink" class="primary-btn" disabled>주문 취소 불가</a>
                        </c:when>
                        <c:when test="${delivery.deliveryStatus.name() eq 'CANCELED'}">
                        </c:when>
                        <c:otherwise>
                            <a id="orderCancelLink" href="#" class="primary-btn">주문 취소</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Shopping Cart Section End -->

<!-- Footer Section Begin -->
<jsp:include page="../common/footer.jsp"/>
<!-- Footer Section End -->

<jsp:include page="../common/search.jsp"/>

<!-- Js Plugins -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.nice-select.min.js"></script>
<script src="js/jquery.nicescroll.min.js"></script>
<script src="js/jquery.magnific-popup.min.js"></script>
<script src="js/jquery.countdown.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script src="js/mixitup.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/main.js"></script>
<script src="js/order.js"></script>

<script>
    $('#orderCancelLink').on('click', function () {
        Swal.fire({
            title: '정말로 취소하시겠습니까?',
            text: "한번 취소하면 되돌릴 수 없습니다.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '예',
            cancelButtonText: '아니오'
        }).then((result) => {
            if (result.isConfirmed) {


                $.ajax({
                    type: "GET",
                    url: "/order-rest.bit?cmd=orderDelete&orderId=${productOrderDetail.orderId}",
                    dataType: "text",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    error: function (request, status, error) {
                        Swal.fire({
                            icon: 'error',
                            title: "ERROR",
                            text: request.responseText,
                            footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
                        })
                    },
                    success: function (data) {
                        Swal.fire(
                            '주문 취소 완료',
                            '해당 주문이 취소되었습니다.',
                            'success'
                        ).then(() => {
                            window.location.replace("/order.bit?view=detail&cmd=get&orderId=" + data);
                        })
                    }
                });
            }
        });
    });

</script>

</body>

</html>
