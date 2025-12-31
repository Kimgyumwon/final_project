
/**
 * 공용 페이지네이션 객체
 * - JSP에서는 Pager.go(page)만 호출
 * - 실제 조회 로직은 각 화면 JS에서 정의
 */
const Pager = {
  go(page) {
	
    const pageInput = document.querySelector('input[name="pager.page"]');
    if (!pageInput) return;

    pageInput.value = page;
	
	
	
    // 화면별 페이지 변경 핸들러 호출
    if (typeof window.onPageChange === 'function') {
      window.onPageChange(page);
    }
  }
};
