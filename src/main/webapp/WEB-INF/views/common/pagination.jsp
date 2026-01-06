<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:if test="${pager != null && pager.end >= pager.begin}">
  <nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">

      <!-- 이전 블럭 -->
	 <c:if test="${pager.page > 1}">
	  <li class="page-item">
	    <a class="page-link" href="javascript:Pager.go(${pager.page - 1})">
            &laquo;
	    </a>
	  </li>
	</c:if>
	<c:if test="${pager.page == 1}">
	  <li class="page-item disabled">
	    <a class="page-link">
            &laquo;
	    </a>
	  </li>
	</c:if>


      <!-- 페이지 번호 -->
      <c:forEach begin="${pager.begin}" end="${pager.end}" var="p">
        <li class="page-item ${p == pager.page ? 'active' : ''}">
          <a class="page-link" href="javascript:Pager.go(${p})">
            ${p}
          </a>
        </li>
      </c:forEach>

      <!-- 다음 블럭 -->
      <c:if test="${pager.end < (pager.begin + pager.perBlock - 1)}">
        <li class="page-item">
          <a class="page-link" href="javascript:Pager.go(${pager.end + 1})">
            &raquo;
          </a>
        </li>
      </c:if>

    </ul>
  </nav>
</c:if>
