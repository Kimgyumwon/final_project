/**
 * 
 */

document.addEventListener('DOMContentLoaded', function () {

  const modal = document.getElementById('itemModal');
  const tbody = document.getElementById('itemModalTbody');
  
  modal.addEventListener('show.bs.modal', function (e) {

    // 클릭된 버튼
    const button = e.relatedTarget;

    // data-* 값 추출
    const receivableId = button.dataset.receivableId;
    const orderDate = button.dataset.orderDate;

    // 모달 상단에 세팅
    document.getElementById('modalReceivableId').innerText = receivableId;
    document.getElementById('modalOrderDate').innerText = orderDate;
	
	tbody.innerHTML = `
		<tr>
			<td colspan="6" class="text-center text-muted">로딩 중..</td>
		</tr>
	`;
	fetch(`/receivable/items?receivableId=${receivableId}`)
		.then(res => res.json())
		.then(list => {
			tbody.innerHTML='';
			
			if(!list || list.length === 0) {
				tbody.innerHTML = `
					<tr>
		              <td colspan="6" class="text-center text-muted">품목 데이터 없음</td>
		            </tr>
				`;
				return;
			}
			
			list.forEach(item => {
				const tr = document.createElement('tr');
				
				tr.innerHTML = `
					<td class="text-center">${item.itemName}</td>
		            <td class="text-end">${item.quantity}</td>
		            <td class="text-end">${Number(item.unitPrice).toLocaleString()}</td>
		            <td class="text-end">${Number(item.supplyAmount).toLocaleString()}</td>
		            <td class="text-end">${Number(item.taxAmount).toLocaleString()}</td>
		            <td class="text-end fw-bold">${Number(item.totalAmount).toLocaleString()}</td>
				`;
				tbody.appendChild(tr);
			});
			
		});
  });
});







