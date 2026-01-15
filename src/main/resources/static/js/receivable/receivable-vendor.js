/**
 * 본사 발주 미지급금 (Vendor)
 * 기준 JSP:
 *  - receivable-vendor.jsp
 *  - hq-payable-table.jsp
 *
 * 기준월 미입력 → 전체 조회
 * 기준월 입력 → 월 기준 집계 조회
 */

document.addEventListener("DOMContentLoaded", function () {
  initBaseMonthPicker();
});

/* =========================
 * 기준 월 선택기
 * receivable-vendor.jsp 기준
 * ========================= */
function initBaseMonthPicker() {
  flatpickr("#baseMonth", {
    locale: "ko",
    allowInput: true,
    plugins: [
      new monthSelectPlugin({
        shorthand: true,
        dateFormat: "Y-m",
        altFormat: "Y년 m월"
      })
    ]
  });
}

/* =========================
 * 조회 버튼 클릭
 * receivable-vendor.jsp 버튼 onclick
 * ========================= */
function searchPayables() {
  const pageInput = document.querySelector('input[name="pager.page"]');
  if (pageInput) pageInput.value = 1;
  loadPayables();
}

/* =========================
 * 목록 조회
 * hq-payable-table.jsp 렌더링
 * ========================= */
function loadPayables() {
  const form = document.getElementById("searchForm");
  const formData = new FormData(form);

  fetch("/receivable/vendor/search", {
    method: "POST",
    body: formData
  })
    .then(res => res.text())
    .then(html => {
      const tableArea = document.getElementById("payableTableArea");
      const emptyMessage = document.getElementById("emptyMessage");

      tableArea.innerHTML = html;

      tableArea.classList.remove("d-none");
      emptyMessage.classList.add("d-none");

      // hq-payable-table.jsp 내부 pagination.js 연동
      if (typeof bindPagination === "function") {
        bindPagination();
      }
    })
    .catch(err => {
      console.error("vendor payable list 조회 실패", err);
      alert("미지급금 목록 조회 중 오류가 발생했습니다.");
    });

  loadSummary();
}

/* =========================
 * 요약 조회
 * receivable-vendor.jsp 상단 요약 영역
 * ========================= */
function loadSummary() {
  const form = document.getElementById("searchForm");
  const formData = new FormData(form);

  fetch("/receivable/vendor/summary", {
    method: "POST",
    body: formData
  })
    .then(res => res.json())
    .then(data => {
      if (!data) return;

      document.getElementById("totalUnpaidAmount").innerText =
        numberFormat(data.totalUnpaidAmount);

      document.getElementById("payableCount").innerText =
        data.payableCount + "건";

      const baseMonth = document.getElementById("baseMonth").value;
      document.getElementById("summaryBaseMonth").innerText =
        baseMonth ? baseMonth : "-";

      document.getElementById("summaryArea").classList.remove("d-none");
    })
    .catch(err => {
      console.error("vendor payable summary 조회 실패", err);
    });
}

/* =========================
 * 페이지 이동
 * hq-payable-table.jsp → Pager.go(page)
 * ========================= */
function goPage(page) {
  const pageInput = document.querySelector('input[name="pager.page"]');
  if (pageInput) pageInput.value = page;
  loadPayables();
}

/* =========================
 * 숫자 포맷
 * hq-payable-table.jsp 금액 표시용
 * ========================= */
function numberFormat(value) {
  return Number(value || 0).toLocaleString();
}
