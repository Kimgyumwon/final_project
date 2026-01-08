function removeSelectedItems() {

  const checkedItems = document.querySelectorAll(".order-check:checked");

  if (checkedItems.length === 0) {
    alert("취소할 상품을 선택하세요.");
    return;
  }

  checkedItems.forEach(checkbox => {
    const tr = checkbox.closest("tr");
    tr.remove();
  });

  // 삭제 후 총 금액 재계산
  updateGrandTotal();
}

function toggleAllCheckboxes(){
	const isChecked = document.getElementById("checkAll").checked;
	const items = document.querySelectorAll(".order-check");

	items.forEach(checkbox => {
	  checkbox.checked = isChecked;
	});
	
}

/* 목록 중 하나라도 체크해제 시 ALL체크도 해제 */
document.addEventListener("change", function (e) {
  if (e.target.classList.contains("order-check")) {
    const all = document.querySelectorAll(".order-check");
    const checked = document.querySelectorAll(".order-check:checked");

    document.getElementById("checkAll").checked =
      all.length > 0 && all.length === checked.length;
  }
});