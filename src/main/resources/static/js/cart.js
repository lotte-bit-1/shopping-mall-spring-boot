document.addEventListener("DOMContentLoaded", function () {
    const deleteButtons = document.querySelectorAll(".delete-button");
    const subtotalElement = document.getElementById("subtotal");
    const totalElement = document.getElementById("total");

    deleteButtons.forEach((button) => {
        button.addEventListener("click", function () {
          const productRow = this.parentElement.parentElement;
          const productId = productRow.querySelector(
              ".cart__close").getAttribute(
              "product-id");
          const productPrice = parseFloat(
              productRow.querySelector(".cart__price").textContent);
          const selectedQuantityElement = productRow.querySelector(
              ".quantity-select");
          const selectedQuantity = parseFloat(selectedQuantityElement.value);
          let totalPrice = selectedQuantity * productPrice;
          let previousTotalPrice = parseFloat(subtotalElement.textContent);
          let newTotalPrice = previousTotalPrice - totalPrice;
          const params = new URLSearchParams();
          params.append("productId", productId);

          $.ajax({
            url: '/carts/products/' + productId,
            type: 'DELETE',
            dataType: 'text',
            success: function (response) {
              console.log(response);
              productRow.remove()
              const subtotalElement = document.getElementById("subtotal");
              const totalElement = document.getElementById("total");
              subtotalElement.textContent = `${newTotalPrice}원`;
              totalElement.textContent = `${newTotalPrice}원`

            }
            ,
            error: function (response) {
              console.log(response);
            }
          })

        });
    });
});

$('.shopping__cart__table').on('change', '.quantity-select',
    function (event) {
      var productId = $(this).data('product-id');
      var selectedQuantity = parseInt($(this).val());
      var productRows = $('.shopping__cart__table tbody tr');
      var productTotalPrices = {};
      productRows.each(function () {
        var id = $(this).find('.quantity-select').data('product-id');
        var quantity = parseInt(
            $(this).find('.quantity-select').val());
        var productPrice = parseFloat(
            $(this).find('.cart__price').text().replace(/[^0-9.]/g, ""));

        var totalPrice = quantity * productPrice;
        productTotalPrices[id] = totalPrice;
      });
      var totalPriceSum = 0;
      for (var pk in productTotalPrices) {
        totalPriceSum += productTotalPrices[pk];
      }

      $.ajax({
        url: '/carts/products/' + productId,
        type: 'PUT',
        data: {
          requestQuantity: selectedQuantity
        },
        dataType: 'text',
        success: function (response) {
          console.log(response);
          const subtotalElement = document.getElementById("subtotal");
          const totalElement = document.getElementById("total");
          subtotalElement.textContent = `${totalPriceSum}원`;
          totalElement.textContent = `${totalPriceSum}원`
        }

        ,
        error: function (response) {
          console.log(response);

        }
      })
    });

