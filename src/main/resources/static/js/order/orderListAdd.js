function selectItem(itemId, itemCode, itemName, itemSupplyPrice, vendorCode) {
  
  let check = confirm("물품을 선택하시겠습니까?");
  if (!check){
	return;
  }
	
  const tbody = document.getElementById("orderItemBody");

  /* 1️ 중복 상품 체크 */
  if (document.querySelector(`tr[data-item-code="${itemCode}"]`)) {
    alert("이미 선택된 물품입니다.");
    return;
  }
  
  /* 2️ index 계산 (중요) */
  const index = tbody.querySelectorAll("tr").length;

  /* 3️ row 생성 */
  const tr = document.createElement("tr");
  tr.classList.add("item-row");
  tr.setAttribute("data-item-code", itemCode);
  tr.setAttribute("data-price", itemSupplyPrice);

  tr.innerHTML = `
    <td>
      <input type="checkbox" class="order-check">
    </td>
    <td>
		<input type="hidden" name="items[${index}].itemId" value="${itemId}">
		${itemCode}
		<input type="hidden" name="items[${index}].itemCode" value="${itemCode}">
		<input type="hidden" name="items[${index}].itemName" value="${itemName}">
		<input type="hidden" name="items[${index}].vendorCode" value="${vendorCode}">
	</td>
    <td>${itemName}</td>
    <td class="text-end">
		${itemSupplyPrice.toLocaleString()}
		<input type="hidden" name="items[${index}].itemSupplyPrice" value="${itemSupplyPrice}">	
	</td>
    <td>
      <input type="text"
             class="form-control text-end qty"
			 name="items[${index}].ItemQuantity"
             oninput="updateRowTotal(this)">
    </td>
    <td class="row-total text-end">0</td>
  `;

  /* 3️ 테이블에 추가 */
  tbody.appendChild(tr);

}