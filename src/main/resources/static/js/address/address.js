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

const modal = document.getElementById('exampleModal');

function closeModal() {
  modal.style.display = 'none';
}

let saveAddr = document.getElementById("addrSubmit");
saveAddr.addEventListener('click', () => {
  let roadNameInput = document.getElementById('roadName').value;
  let addrDetailInput = document.getElementById("addrDetail").value;
  let zipCodeInput = document.getElementById('zipCode').value;

  // saveAddr.href = "/api/address/save?roadName=" + roadNameInput + "&addrDetail="
  //     + addrDetailInput + "&zipCode=" + zipCodeInput;
  let url = "/api/address/save?roadName=" + roadNameInput + "&addrDetail="
      + addrDetailInput + "&zipCode=" + zipCodeInput;
  fetch(url).then(res => res).then(data => {
    alert("배송지가 추가되었습니다")
  });

});