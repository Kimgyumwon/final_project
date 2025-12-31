
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
}


function onSearchClick() {
  const pageInput = document.querySelector('input[name="pager.page"]');
  if (pageInput) pageInput.value = 1;

  searchReceivables();
}


