/* ===============================
 * 숫자 유틸
 * =============================== */
function formatNumber(num) {
  return Number(num).toLocaleString();
}

function unformatNumber(str) {
  return Number(String(str).replace(/,/g, '')) || 0;
}

/* ===============================
 * 지급 버튼 클릭 → 모달 오픈
 * =============================== */
document.addEventListener('click', function (e) {
  const btn = e.target.closest('.btn-pay');
  if (!btn) return;

  const modalEl = document.getElementById('payModal');
  const modal = new bootstrap.Modal(modalEl);

  const vendorCode = btn.dataset.vendorCode;
  const vendorName = btn.dataset.vendorName;
  const baseMonth = btn.dataset.baseMonth;
  const remainAmount = Number(btn.dataset.remainAmount);

  // hidden / readonly 값 세팅
  modalEl.querySelector('[name=vendorCode]').value = vendorCode;
  modalEl.querySelector('[name=vendorName]').value = vendorName;
  modalEl.querySelector('[name=baseMonth]').value = baseMonth;
  modalEl.querySelector('[name=baseMonthView]').value = baseMonth;

  modalEl.querySelector('[name=remainAmount]').value = remainAmount;
  modalEl.querySelector('[name=remainAmountView]').value =
    formatNumber(remainAmount);

  // ⭐ 지급 금액은 항상 0부터 시작
  modalEl.querySelector('[name=payAmount]').value = '0';

  modalEl.querySelector('[name=memo]').value = '';

  modal.show();
});

/* ===============================
 * 금액 버튼 (1만 / 5만 / 10만)
 * =============================== */
document.addEventListener('click', function (e) {
  const btn = e.target.closest('.pay-btn');
  if (!btn) return;

  const form = document.getElementById('payForm');
  const payInput = form.querySelector('[name=payAmount]');

  const current = unformatNumber(payInput.value);
  const add = Number(btn.dataset.amount);
  const remain = unformatNumber(
    form.querySelector('[name=remainAmount]').value
  );

  let next = current + add;
  if (next > remain) {
    next = remain;
  }

  payInput.value = formatNumber(next);
});

/* ===============================
 * 완납 버튼
 * =============================== */
document.getElementById('btnPayAll').addEventListener('click', function () {
  const form = document.getElementById('payForm');
  const remain = unformatNumber(
    form.querySelector('[name=remainAmount]').value
  );

  form.querySelector('[name=payAmount]').value =
    formatNumber(remain);
});

/* ===============================
 * 금액 초기화
 * =============================== */
document.getElementById('btnPayReset').addEventListener('click', function () {
  document
    .getElementById('payForm')
    .querySelector('[name=payAmount]').value = '0';
});

/* ===============================
 * 지급 금액 직접 입력
 * - 숫자만 허용
 * - 콤마 포맷
 * - 남은 금액 초과 방지
 * =============================== */
document
  .querySelector('#payForm [name=payAmount]')
  .addEventListener('input', function () {

    const form = document.getElementById('payForm');
    const remain = unformatNumber(
      form.querySelector('[name=remainAmount]').value
    );

    let value = unformatNumber(this.value);

    if (value > remain) {
      value = remain;
    }

    this.value = formatNumber(value);
  });

/* ===============================
 * 지급 처리
 * =============================== */
document.getElementById('btnPaySubmit').addEventListener('click', function () {

  const form = document.getElementById('payForm');
  const data = new FormData(form);

  const payAmount = unformatNumber(data.get('payAmount'));
  const remain = unformatNumber(data.get('remainAmount'));

  if (payAmount <= 0) {
    alert('지급 금액을 입력하세요.');
    return;
  }

  if (payAmount > remain) {
    alert('지급 금액이 남은 미지급금보다 큽니다.');
    return;
  }

  // 서버에는 숫자만 전달
  data.set('payAmount', payAmount);

  fetch('/receivable/hq/pay', {
    method: 'POST',
    body: data
  })
    .then(res => {
      if (!res.ok) throw new Error();
      return res.text();
    })
    .then(() => {
      alert('지급 처리되었습니다.');

      bootstrap.Modal
        .getInstance(document.getElementById('payModal'))
        .hide();

      if (typeof searchPayables === 'function') {
        searchPayables();
      }
    })
    .catch(() => {
      alert('지급 처리 중 오류가 발생했습니다.');
    });
});
