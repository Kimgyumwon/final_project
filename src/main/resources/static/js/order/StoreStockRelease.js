let selectedItems = [];

const releaseModal = new bootstrap.Modal(document.getElementById("releaseModal"));
const itemModal = new bootstrap.Modal(document.getElementById("itemModal"));

document.getElementById("btnAddItem").addEventListener("click", () => {
  itemModal.show();
  loadInventory();
});

function loadInventory() {
  fetch("/order/storeInventory")   // ⭐ 절대경로
    .then(res => {
      if (!res.ok) throw new Error("HTTP " + res.status);
      return res.json();
    })
    .then(data => {
      const ul = document.getElementById("inventoryList");
      ul.innerHTML = "";

      data.forEach(item => {
        const li = document.createElement("li");
        li.className = "list-group-item d-flex justify-content-between align-items-center";

        li.innerHTML = `
          <div>
            <div class="fw-semibold">${item.itemName}</div>
            <small class="text-muted">보유수량: ${item.stockQuantity}</small>
          </div>
          <button type="button" class="btn btn-sm btn-outline-primary">선택</button>
        `;

        li.querySelector("button").addEventListener("click", () => {
          selectItem(item);
          itemModal.hide();
        });

        ul.appendChild(li);
      });
    })
    .catch(err => console.error(err));
}

function selectItem(item) {
  if (selectedItems.find(i => i.itemCode === item.itemCode)) {
    alert("이미 선택된 상품입니다.");
    return;
  }

  selectedItems.push({
    itemCode: item.itemCode,
    itemName: item.itemName,
    quantity: 1
  });

  renderSelectedItems();
}

document.getElementById("btnRelease").addEventListener("click", () => {
  const form = document.createElement("form");
  form.method = "post";
  form.action = "/order/store/use";

  selectedItems.forEach(item => {
    const code = document.createElement("input");
    code.type = "hidden";
    code.name = "itemCodes";
    code.value = item.itemCode;

    const qty = document.createElement("input");
    qty.type = "hidden";
    qty.name = "quantities";
    qty.value = item.quantity;

    form.appendChild(code);
    form.appendChild(qty);
  });

  document.body.appendChild(form);
  form.submit();
});
