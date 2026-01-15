<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<table class="table table-hover">
  <thead>
    <tr>
      <th>거래처 코드</th>
      <th>거래처명</th>
      <th class="text-end">총 미지급금액</th>
      <th class="text-center">관리</th>
    </tr>
  </thead>

  <tbody>
    <c:forEach var="row" items="${list}">
      <tr>
        <td>${row.vendorCode}</td>
        <td>${row.vendorName}</td>
        <td class="text-end text-primary fw-bold">
          <fmt:formatNumber value="${row.totalUnpaidAmount}" pattern="#,###" />
        </td>

        <!-- 관리 / 상세 버튼 -->
        <td class="text-center">
          <button type="button"
                  class="btn btn-sm btn-outline-primary"
                  onclick="goPayableDetail(
                    '${row.vendorCode}',
                    '${row.baseMonth}'
                  )">
            상세
          </button>
        </td>
      </tr>
    </c:forEach>

    <!-- 조회 결과 없음 -->
    <c:if test="${empty list}">
      <tr>
        <td colspan="4" class="text-center text-muted py-4">
          조회 결과가 없습니다.
        </td>
      </tr>
    </c:if>
  </tbody>
</table>

<!-- 페이지네이션 -->
<jsp:include page="/WEB-INF/views/common/pagination.jsp" />
