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
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

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
<%-- 사용자 정보 : memberInfo --%>
<section class="checkout spad">
    <div class="container">
        <div class="checkout__form">
            <div class="row">
                <div class="col-lg-8 col-md-6">
                    <h2 class="checkout__title">사용자 정보</h2>
                    <div class="checkout__input">
                        <h5 class="checkout__title__custom">이름</h5>
                        <input id="memberName" type="text" name="memberName"
                               value="${memberInfo.name}" disabled>
                    </div>
                    <div class="checkout__input">
                        <h5 class="checkout__title__custom">이메일</h5>
                        <input id="memberEmail" type="text" name="memberEmail"
                               value="${memberInfo.email}" disabled>
                    </div>

                    <div>
                        <div class="row justify-content-center">
                            <h5 class="checkout__title__custom">주소지</h5>
                            <!-- Button trigger modal -->
                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    data-target="#exampleModal">
                                Launch demo modal
                            </button>
                        </div>


                        <c:if test="${not empty defaultError}">
                            <div>${defaultError}</div>
                        </c:if>
                        <c:if test="${empty defaultError}">
                            <h4>기본 배송지</h4>
                            <div>${defaultAddr.roadName}</div>
                            <div>${defaultAddr.addrDetail}</div>
                            <div>${defaultAddr.zipCode}</div>
                        </c:if>
                        <br/>
                        <c:if test="${not empty subError}">
                            <div>${subError}</div>
                        </c:if>
                        <c:if test="${empty subError}">
                            <c:forEach var="address" items="${sub}" varStatus="loop">
                                <div>${address.roadName}</div>
                                <div>${address.addrDetail}</div>
                                <div>${address.zipCode}</div>
                            </c:forEach>
                        </c:if>
                    </div>
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
            </div>
        </div>
    </div>
</section>


<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="checkout__input">
                    <p>주소<span>*</span></p>
                    <input type="text" placeholder="도로명 주소" class="checkout__input__add"
                           id="roadName"
                           name="roadName"
                           required
                           onclick="getDaumPostcode()" readonly
                           oninvalid="this.setCustomValidity('도로명 주소를 입력해주세요.')"
                           oninput="this.setCustomValidity('')" style="color: black;">
                    <input type="text" placeholder="상세 주소" class="checkout__input__add"
                           id="addrDetail"
                           name="addrDetail"
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
                           required
                           onclick="getDaumPostcode()" readonly
                           oninvalid="this.setCustomValidity('우편번호를 입력해주세요.')"
                           oninput="this.setCustomValidity('')" style="color: black;">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                <a id="addrSubmit" type="button" class="btn btn-primary" data-dismiss="modal">주소 추가하기</a>
            </div>
        </div>
    </div>
</div>
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
<script src="/js/address/address.js"></script>
</body>

</html>
