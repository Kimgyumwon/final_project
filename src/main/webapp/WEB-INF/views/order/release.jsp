<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>

<html
  lang="en"
  class="light-style layout-menu-fixed"
  dir="ltr"
  data-theme="theme-default"
  data-assets-path="../assets/"
  data-template="vertical-menu-template-free"
>
<head>
  <meta charset="utf-8" />
  <meta
    name="viewport"
    content="width=device-width, initial-scale=1.0, user-scalable=no"
  />

  <title>출고 처리</title>

  <!-- Favicon -->
  <link rel="icon" type="image/x-icon" href="../assets/img/favicon/favicon.ico" />

  <!-- Fonts -->
  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
  <link
    href="https://fonts.googleapis.com/css2?family=Public+Sans:wght@300;400;500;600;700&display=swap"
    rel="stylesheet"
  />

  <!-- Icons -->
  <link rel="stylesheet" href="/vendor/fonts/boxicons.css" />

  <!-- Core CSS -->
  <link rel="stylesheet" href="/vendor/css/core.css" />
  <link rel="stylesheet" href="/vendor/css/theme-default.css" />
  <link rel="stylesheet" href="/css/demo.css" />

  <!-- Vendors CSS -->
  <link rel="stylesheet" href="/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />
  <link rel="stylesheet" href="/vendor/libs/apex-charts/apex-charts.css" />

  <!-- Page CSS -->
  <link rel="stylesheet" href="/css/order/main.css"/>
  <link rel="stylesheet" href="/css/order/approve.css"/>

  <!-- Helpers -->
  <script src="/vendor/js/helpers.js"></script>
  <script src="/js/config.js"></script>
  <base href="${pageContext.request.contextPath}/">
</head>

<body data-user-type="${fn:startsWith(member.memberId, '1') ? 'HQ' : 'STORE'}">

<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
  <div class="layout-container">

    <!-- Menu -->
    <c:import url="/WEB-INF/views/template/aside.jsp"/>
    <!-- / Menu -->

    <!-- Layout page -->
    <div class="layout-page">
      <c:import url="/WEB-INF/views/template/header.jsp"/>

      <!-- Content wrapper -->
      <div class="content-wrapper">

        <!-- Content -->
        <div class="container-fluid flex-grow-1 container-p-y">

          <div class="layout-wrapper layout-content-navbar">
            <div class="layout-container">
              <div class="layout-page">
                <div class="content-wrapper">

                  <!-- ================= 제목 ================= -->
                  <div class="row mb-4">
                    <div class="col">
                      <h4 class="fw-bold">
                        <span class="text-muted fw-light">발주 관리 /</span> 출고 처리
                      </h4>
                    </div>
                  </div>

                  <!-- ================= 메인 카드 ================= -->
                  <div class="card p-3">
                    <div class="row g-3">

                      <!-- ================= 왼쪽 ================= -->
                      <div class="col-xl-7 col-lg-12">
                        <div class="card h-100 d-flex flex-column">

                          <div class="card-header d-flex justify-content-between">
                            <h5 class="mb-0">출고 대상 목록</h5>

                            <div class="d-flex gap-2">
                              <c:if test="${fn:startsWith(member.memberId, '1')}">
                                <button class="btn btn-primary btn-sm" id="updateReceiveStatusByStoreOrder">출고</button>
                                <button class="btn btn-danger btn-sm" id="updateCancelReceiveStatusByStoreOrder">출고취소</button>
                              </c:if>

                              <c:if test="${fn:startsWith(member.memberId, '2')}">
                                <button class="btn btn-success btn-sm" id="requestReleaseBtn" data-bs-toggle="modal"
        data-bs-target="#releaseModal">출고요청</button>
                              </c:if>
                            </div>
                          </div>

                          <!-- 탭 -->
                          <div class="card-body pb-0">
                            <ul class="nav nav-tabs">
                              <li class="nav-item">
                                <button class="nav-link active" data-bs-toggle="tab" data-bs-target="#storeOrderTab">
                                  출고
                                </button>
                              </li>
                            </ul>
                          </div>

                          <!-- 목록 -->
                          <div class="tab-content order-left-body">
                            <div class="tab-pane fade show active" id="storeOrderTab">
                              <table class="table table-bordered text-center align-middle mb-0">
                                <thead class="table-light">
                                  <tr>
                                    <th class="chk-td">
                                      <input type="checkbox" id="checkAll"/>
                                    </th>
                                    <th>발주번호</th>
                                    <th>금액</th>
                                    <th>가맹코드</th>
                                    <th>상태</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  <c:forEach var="o" items="${orderStoreList}">
                                    <tr class="order-row"
                                        data-order-no="${o.hqOrderId}"
                                        data-status="${o.hqOrderStatus}">
                                      <td class="chk-td">
                                        <input type="checkbox" class="order-check"/>
                                      </td>
                                      <td>${o.hqOrderId}</td>
                                      <td class="text-end">
                                        <fmt:formatNumber value="${o.hqOrderTotalAmount}"/>원
                                      </td>
                                      <td>${o.memberId}</td>
                                      <td>
                                        <c:choose>
                                          <c:when test="${o.hqOrderStatus == 330}">
                                            <span class="badge bg-label-info">출고대기</span>
                                          </c:when>
                                          <c:when test="${o.hqOrderStatus == 350}">
                                            <span class="badge bg-label-success">출고완료</span>
                                          </c:when>
                                        </c:choose>
                                      </td>
                                    </tr>
                                  </c:forEach>
                                </tbody>
                              </table>
                            </div>
                          </div>

                        </div>
                      </div>

                      <!-- ================= 오른쪽 ================= -->
                      <div class="col-xl-5 col-lg-12 order-right">

                        <!-- 출고 리스트 -->
                        <div class="card approval-list">
                          <div class="card-header">
                            <h5 class="mb-0">출고 리스트</h5>
                          </div>
                          <div class="order-scroll">
                            <table class="table table-sm table-bordered text-center mb-0">
                              <thead>
                                <tr>
                                  <th>발주번호</th>
                                  <th>출고일자</th>
                                  <th>총금액</th>
                                </tr>
                              </thead>
                              <tbody id="approvalListBody">
                                <tr class="empty-row">
                                  <td colspan="3" class="text-muted text-center">
                                    선택된 발주가 없습니다
                                  </td>
                                </tr>
                              </tbody>
                            </table>
                          </div>
                        </div>

                        <!-- 출고 상세 -->
                        <div class="card order-detail">
                          <div class="card-header">
                            <h5 class="mb-1">출고 상세</h5>
                            <small class="text-muted">
                              발주번호: <span id="selectedOrderId">-</span>
                            </small>
                          </div>
                          <div class="order-scroll">
                            <table class="table table-bordered text-center align-middle mb-0">
                              <thead class="table-light">
                                <tr>
                                  <th>상품명</th>
                                  <th>수량</th>
                                  <th>단가</th>
                                  <th>금액</th>
                                </tr>
                              </thead>
                              <tbody id="orderDetailBody">
                                <tr>
                                  <td colspan="4" class="text-muted py-4">
                                    발주를 선택하세요
                                  </td>
                                </tr>
                              </tbody>
                            </table>
                          </div>
                        </div>

                      </div>
                    </div>
                  </div>

                </div>
              </div>
            </div>
          </div>

        </div>
        <!-- / Content -->

        <!-- Footer -->
        <c:import url="/WEB-INF/views/template/footer.jsp"/>
        <!-- / Footer -->

        <div class="content-backdrop fade"></div>
      </div>
      <!-- / Content wrapper -->
    </div>
    <!-- / Layout page -->
  </div>
  
  <!-- 가맹 출고 모달 -->
  <div class="modal fade" id="releaseModal" tabindex="-1">
	  <div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
	    <div class="modal-content">
	
	      <div class="modal-header">
	        <h5 class="modal-title">가맹출고 요청</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>
	
	      <div class="modal-body">
	
	        <div class="d-flex justify-content-end mb-2">
	          <button class="btn btn-outline-primary btn-sm" id="btnAddItem">
	            물품등록
	          </button>
	        </div>
	
	        <table class="table table-bordered text-center align-middle">
	          <thead class="table-light">
	            <tr>
	              <th>물품명</th>
	              <th style="width:120px">수량</th>
	            </tr>
	          </thead>
	          <tbody id="selectedItemList">
	            <tr class="empty-row">
	              <td colspan="2" class="text-muted">
	                등록된 물품이 없습니다
	              </td>
	            </tr>
	          </tbody>
	        </table>
	
	      </div>
	
	      <div class="modal-footer">
	        <button class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	        <button class="btn btn-primary" onclick="submitReleaseRequest()">출고요청</button>
	      </div>
	
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="itemModal" tabindex="-1">
	  <div class="modal-dialog modal-md modal-dialog-centered modal-dialog-scrollable">
	    <div class="modal-content">
	
	      <div class="modal-header">
	        <h5 class="modal-title">물품 선택</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>
	
	      <div class="modal-body">
	        <ul class="list-group" id="inventoryList">
	          <!-- JS로 렌더링 -->
	        </ul>
	      </div>
	
	      <div class="modal-footer">
	        <button class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	      </div>
	
	    </div>
	  </div>
	</div>

  <!-- Overlay -->
  <div class="layout-overlay layout-menu-toggle"></div>
</div>
<!-- / Layout wrapper -->



<!-- Core JS -->
<script src="/vendor/libs/jquery/jquery.js"></script>
<script src="/vendor/libs/popper/popper.js"></script>
<script src="/vendor/js/bootstrap.js"></script>
<script src="/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>
<script src="/vendor/js/menu.js"></script>

<!-- Vendors JS -->
<script src="/vendor/libs/apex-charts/apexcharts.js"></script>

<!-- Main JS -->
<script src="/js/main.js"></script>

<!-- Page JS -->
<script src="/js/order/StoreStockRelease.js"></script>
<script src="/js/order/oederRelease.js"></script>

</body>
</html>
