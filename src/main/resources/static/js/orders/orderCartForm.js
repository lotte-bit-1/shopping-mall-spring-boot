'use strict';

(function ($) {

    $(document).ready(() => {
        // 총 가격 계산
        updateTotalPrice();

        const queryParameters = new URLSearchParams(decodeURI(location.search));
        const pgToken = queryParameters.get("pg_token");
        if(pgToken !== null) {
            createOrder(pgToken);
        }
    });

    /* 카카오페이 결제하기 버튼 클릭 event */
    $('#payment-btn').on('click', () => {
        const memberName = document.getElementById("memberName").value;
        const roadName = document.getElementById("roadName").value;
        const addrDetail = document.getElementById("addrDetail").value;
        const zipCode = document.getElementById("zipCode").value;
        if (memberName === '' || roadName === '' || addrDetail === '' || zipCode === '') {
            Swal.fire({
                icon: 'error',
                title: "ERROR",
                text: '모든 필드를 입력해주세요.',
                footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
            });
        } else {
            kakaoPay();
        }
    });

    /* kakao payment ready api call */
    function kakaoPay() {
        // const form = document.getElementById('order-form');
        // const formData = new FormData(form);
        //
        // const formDataObject = {};
        // formData.forEach((value, key) => {
        //     formDataObject[key] = value;
        // });
        //
        // const productsArray = [];
        // $('.checkout__total__products .product-item').each(function() {
        //     productsArray.push({
        //         productId: $(this).find('.product-id').val(),
        //         price: $(this).find('.product-price').val(),
        //         quantity: $(this).find('.product-quantity').val()
        //     });
        //
        //     console.log($(this).find('.product-quantity').val());
        // });
        //
        // const jsonData = {
        //     roadName: formDataObject['roadName'],
        //     addrDetail: formDataObject['addrDetail'],
        //     zipCode: formDataObject['zipCode'],
        //     products: productsArray,
        //     totalPrice: formDataObject['totalPrice']
        // };
        //
        // const jsonString = JSON.stringify(jsonData);

        const form = document.getElementById('order-form');
        const formData = new FormData(form);
        const jsonObject = Object.fromEntries(formData);
        const jsonString = JSON.stringify(jsonObject);
        console.log(jsonString);

        $.ajax({
            type: "POST",
            url: "/api/payments/kakao/ready/cart",
            data: jsonString,
            contentType: "application/json",
            error: function (request, status, error) {
                Swal.fire({
                    icon: 'error',
                    title: "ERROR",
                    text: request.responseText,
                    footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
                });
            },
            success: function (data) {
                window.location.replace(data.next_redirect_pc_url);
            }
        });
    }

    /* create order and kakao payment approve api call */
    function createOrder(pgToken) {
        const form = document.getElementById('order-form');
        const formData = new FormData(form);
        const jsonObject = Object.fromEntries(formData);
        const jsonString = JSON.stringify(jsonObject);
        $.ajax({
            type: "POST",
            url: "/api/orders/cart",
            data: jsonString,
            contentType: "application/json",
            error: function (request, status, error) {
                Swal.fire({
                    icon: 'error',
                    title: "ERROR",
                    text: request.responseText,
                    footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
                });
            },
            success: function (orderId) {
                // payment approve
                $.ajax({
                    type: "GET",
                    url: "/api/payments/kakao/approve?pg_token=" + pgToken + "&orderId=" + orderId,
                    contentType: "application/json",
                    error: function (request, status, error) {
                        Swal.fire({
                            icon: 'error',
                            title: "ERROR",
                            text: request.responseText,
                            footer: '<a href="https://github.com/lotte-bit-1/shopping-mall-servlet-jsp/issues">이슈 남기러 가기</a>'
                        });
                    },
                    success: function (orderId) {
                        window.location.href = "/orders/" + orderId;
                    }
                });
            }
        });
    }
})(jQuery);

/* 할인, 총 가격 업데이트 */
function updateTotalPrice() {
    const productItems = document.querySelectorAll(".product-item");
    const calculatedTotalElem = document.getElementById("calculated-total");
    const calculatedTotalPrice = document.getElementById("totalPrice");
    const calculatedDiscountPrice = document.getElementById("discountPrice");
    const couponSelect = document.getElementById("coupon");

    let calculatedTotal = 0;

    productItems.forEach(item => {
        const productPrice = parseInt(item.querySelector(".product-price").value);
        const productQuantity = parseInt(item.querySelector(".product-quantity").value);

        const totalItemPrice = productPrice * productQuantity;
        calculatedTotal += totalItemPrice;
    });

    const selectedOption = couponSelect.options[couponSelect.selectedIndex];
    const couponDiscountPolicy = selectedOption.getAttribute("name");
    const couponDiscountValue = parseInt(selectedOption.getAttribute("id"));

    calculatedDiscountPrice.textContent = '0원';
    if (couponDiscountPolicy === 'CASH') {
        calculatedTotal -= couponDiscountValue;
        calculatedDiscountPrice.textContent = '-' + couponDiscountValue + '원';
    }
    if (couponDiscountPolicy === 'DISCOUNT') {
        calculatedTotal -= (calculatedTotal * (couponDiscountValue / 100));
        calculatedDiscountPrice.textContent = '-' + (calculatedTotal * (couponDiscountValue / 100)) + '원';
    }

    calculatedTotalElem.textContent = calculatedTotal < 0 ? 0 : calculatedTotal + '원';
    calculatedTotalPrice.value = calculatedTotal < 0 ? 0 : calculatedTotal;
}

/* 주소 검색 로직 */
function getDaumPostcode() {
    let roadNameInput = document.getElementById('roadName');
    let addrDetailInput = document.getElementById("addrDetail");
    let zipCodeInput = document.getElementById('zipCode');

    new daum.Postcode({
        oncomplete: function (data) {
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            roadNameInput.value = data.roadAddress;
            addrDetailInput.value = data.jibunAddress;
            zipCodeInput.value = data.zonecode;
        }
    }).open();
}
