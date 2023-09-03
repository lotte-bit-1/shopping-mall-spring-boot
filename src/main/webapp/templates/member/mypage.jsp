<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
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
</header>

<!-- header begin -->
<jsp:include page="../common/header.jsp"/>
<!-- header end -->

<!-- mypage begin -->
<div>
    <div>
        <h2>사용자 정보</h2>
    </div>
    <div>${memberInfo.id}</div>
    <div>${memberInfo.email}</div>
    <div>${memberInfo.name}</div>
    <div>${memberInfo.money}</div>
    <div>
        <h3>주소지</h3>
    </div>
    <c:forEach var="address" items="${memberInfo.address}">
        <li>
            <c:if test="${address.isDefault}">
                <div>
                    <h5>기본 배송지</h5>
                </div>
            </c:if>
            <c:if test="${!address.isDefault}">
                <div>
                    <h5>이외 주소지</h5>
                </div>
            </c:if>
            <div>${address.roadName}</div>
            <div>${address.addrDetail}</div>
            <div>${address.zipCode}</div>
        </li>
    </c:forEach>
    <div>
        <h3>쿠폰 목록</h3>
    </div>
    <c:forEach var="coupons" items="${memberInfo.coupons}">
        <li>
            <div>${coupons.id}</div>
            <div>${coupons.name}</div>
            <div>${coupons.discountPolicy}</div>
            <div>${coupons.discountValue}</div>
            <div>${coupons.status}</div>
        </li>
    </c:forEach>
</div>

<!-- mypage end -->

<!-- footer begin -->
<jsp:include page="../common/footer.jsp"/>
<!-- footer end -->

<jsp:include page="../common/search.jsp"/>