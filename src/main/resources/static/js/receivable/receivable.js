
window.onPageChange = function(page) {
	const pageInput = document.querySelector('input[name="pager.page"]');
	if(pageInput) pageInput.value = page;
	
	searchReceivables();
}

flatpickr("#baseMonth", {
  locale: "ko",
  plugins: [
    new monthSelectPlugin({
      shorthand: true,
      dateFormat: "Y-m",     // 서버로 보내는 값
      altFormat: "Y년 m월"   // 화면 표시
    })
  ]
});


function searchReceivables() {
  const oldError = document.getElementById('searchError');
  if (oldError) {
    oldError.remove();
  }

  
  const form = document.getElementById('searchForm');
  const formData = new FormData(form);
  const storeName = formData.get('storeName');
  
  if (storeName && storeName.length > 50) {
    alert('지점명은 최대 50자까지 입력 가능합니다.');
    return;
  }
  // 조회 전 안내 숨기기
  document.getElementById('emptyMessage').classList.add('d-none');
  document.getElementById('receivableTableArea').classList.remove('d-none');
	
  
  
  fetch('/receivable/search', {
    method: 'POST',
    body: formData
  })
  .then(res => {
    return res.text();
  })
  .then(html => {
    document.getElementById('receivableTableArea').innerHTML = html;
  })
  .catch(err => {
    alert('시스템 오류가 발생했습니다.');
  });
  fetchSummary(formData);
}


function onSearchClick() {
  const pageInput = document.querySelector('input[name="pager.page"]');
  if (pageInput) pageInput.value = 1;

  searchReceivables();
}


/* ================================
 * 요약 조회
 * ================================ */
function fetchSummary(formData) {
  fetch('/receivable/summary', {
    method: 'POST',
    body: formData
  })
    .then(res => res.json())
    .then(summary => {
      renderSummary(summary);
    })
    .catch(err => {
      console.error('summary 조회 실패', err);
    });
}

/* ================================
 * 요약 렌더링
 * ================================ */
function renderSummary(summary) {
  // 총 미수금
  document.getElementById('totalUnpaidAmount').innerText =
    Number(summary.totalUnpaidAmount || 0).toLocaleString();

  // 미수 채권 수
  document.getElementById('receivableCount').innerText =
    (summary.receivableCount || 0) + '건';

  // 기준 월 (입력값 그대로 표시)
  document.getElementById('summaryBaseMonth').innerText =
    document.getElementById('baseMonth').value || '-';

  // 영역 표시
  document.getElementById('summaryArea').classList.remove('d-none');
}

