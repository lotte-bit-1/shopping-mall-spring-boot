'use strict';

(function ($) {

  /*------------------
      Preloader
  --------------------*/
  $(window).on('load', function () {
    $(".loader").fadeOut();
    $("#preloder").delay(200).fadeOut("slow");
  });

  $('#common-parent-element').on('click', '.likes-btn', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var likesBtn = $(this);
    likesBtn.addClass();
    var productId = likesBtn.data("product-id");
    var loginMember = likesBtn.data("login-info");

    if (!loginMember) {
      login();
      return;
    }

    $.post(`/api/likes/${productId}`, {
    }, function (data) {
      if (data == 1) {
        likesBtn.removeClass('likes-btn').addClass('likes-cancel-btn');
        likesBtn.find('img').attr('src', '/img/icon/fill_heart.png');
      }
    }).fail(() => {
      Swal.fire({
        icon: 'warning',
        title: '이미 찜한 상품입니다.'
      }).then(() => {
        window.location.reload();
      });
    });
  });

  $('#common-parent-element').on('click', '.likes-cancel-btn', function (e) {
    e.preventDefault();
    e.stopPropagation();

    var likesCancelBtn = $(this);
    var productId = likesCancelBtn.data("product-id");
    var loginMember = likesCancelBtn.data("login-info");

    if (!loginMember) {
      login();
      return;
    }

    $.ajax({
          url: `/api/likes/${productId}`,
          type: 'DELETE',
          success: function (data) {
            if (data == 1) {
              likesCancelBtn.removeClass('likes-cancel-btn').addClass('likes-btn');
              likesCancelBtn.find('img').attr('src', '/img/icon/heart.png');
            } else {
              Swal.fire({
                icon: 'warning',
                title: '이미 찜 취소한 상품입니다.'
              }).then(() => {
                window.location.reload();
              });
            }
          }
        }
    )

  });

})(jQuery);

// 라디오 버튼 변경 이벤트

const addressForm = document.getElementById('addressForm');
const selectedAddressInfo = document.getElementById('selectedAddressInfo');

addressForm.addEventListener('change', function () {
  const selectedRadio = document.querySelector('input[name="selectedAddress"]:checked');
  console.log("selectedRadio : " + selectedRadio);
  if (selectedRadio) {
    console.log("selectedRadio.value : " + selectedRadio.value);
    const address = JSON.parse(selectedRadio.value);
    console.log("address : " + address);
    console.log(typeof(address));
    console.log("address name : " + address.roadName);

    // 정규 표현식을 사용하여 속성과 값을 추출
    const pattern = /(\w+)=(.*?)\s*(?=,|$)/g;

    const result = {};
    let match;

    while ((match = pattern.exec(address)) !== null) {
      const attribute = match[1];
      const value = match[2];
      result[attribute] = value;
    }

    console.log(result);

    if (address) {
      selectedAddressInfo.innerHTML = `
                    <h5>선택한 주소지</h5>
                    <div>${result.roadName}</div>
                    <div>${result.addrDetail}</div>
                    <div>${result.zipCode}</div>
                `;
    }
  } else {
    selectedAddressInfo.innerHTML = '';
  }
});