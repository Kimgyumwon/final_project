<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
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
      content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"
    />

    <title>거래처 목록</title>

    <meta name="description" content="" />

    <link rel="icon" type="image/x-icon" href="../assets/img/favicon/favicon.ico" />

    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
      rel="stylesheet"
    />
    <link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard.css" />

    <link rel="stylesheet" href="/vendor/fonts/boxicons.css" />

    <link rel="stylesheet" href="/vendor/css/core.css" class="template-customizer-core-css" />
    <link rel="stylesheet" href="/vendor/css/theme-default.css" class="template-customizer-theme-css" />
    <link rel="stylesheet" href="/css/demo.css" />

    <link rel="stylesheet" href="/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />
    <link rel="stylesheet" href="/vendor/libs/apex-charts/apex-charts.css" />

    <script src="/vendor/js/helpers.js"></script>
    <script src="/js/config.js"></script>

    <style>
      /* 타이틀 영역 */
      .page-title-area h4 {
          color: #1e293b;
          font-weight: 700;
          letter-spacing: -0.02em;
          font-size: 1.5rem;
      }

      /* 카드(Card) 디자인 - 핵심 변화: 플랫함 대신 부드러운 깊이감 부여 */
      .card {
          border: none !important; /* 기존 테두리 제거 */
          box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.08), 0 2px 4px -1px rgba(0, 0, 0, 0.04) !important; /* 모던한 소프트 쉐도우 */
          border-radius: 12px !important; /* 더 둥근 모서리 */
          background-color: #ffffff;
          margin-bottom: 24px;
          transition: transform 0.2s ease-in-out;
      }
      .card:hover {
        transform: translateY(-2px); /* 호버 시 살짝 떠오르는 효과 */
      }
      .card-body { padding: 24px; } /* 내부 여백 확보 */

      /* 검색창 및 입력 필드 디자인 */
      .form-label {
          color: #64748b;
          font-weight: 600;
          font-size: 0.875rem;
          margin-bottom: 8px;
      }
      .form-control {
          border: 1px solid #e2e8f0 !important;
          border-radius: 8px !important;
          padding: 10px 14px;
          font-size: 0.95rem;
          background-color: #f8fafc;
          transition: all 0.2s;
      }
      .form-control:focus {
          background-color: #fff;
          border-color: #3b82f6 !important; /* 선명한 블루 포커스 */
          box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15) !important; /* 포커스 링 효과 */
      }
      .input-group .btn { border-top-right-radius: 8px !important; border-bottom-right-radius: 8px !important; }

      /* 메인 버튼 (검색, 추가) */
      .btn-primary {
          background: linear-gradient(135deg, #3b82f6, #2563eb) !important; /* 그라데이션 적용 */
          border: none !important;
          box-shadow: 0 4px 10px rgba(37, 99, 235, 0.2);
          font-weight: 600;
          padding: 10px 20px;
          border-radius: 8px !important;
          transition: all 0.2s;
      }
      .btn-primary:hover {
          box-shadow: 0 6px 15px rgba(37, 99, 235, 0.3);
          transform: translateY(-1px);
      }

      /* 테이블 디자인 - 핵심 변화: 세련된 헤더와 여유로운 간격 */
      .table-responsive { border-radius: 12px; overflow: hidden; }
      .table { margin-bottom: 0; }
      .table thead th {
          background-color: #f1f5f9 !important; /* 아주 연한 블루그레이 헤더 */
          color: #475569 !important;
          font-weight: 700;
          text-transform: uppercase;
          letter-spacing: 0.05em;
          font-size: 0.75rem;
          padding: 16px; /* 헤더 패딩 증가 */
          border-bottom: none !important;
      }
      .table tbody td {
          padding: 16px; /* 셀 패딩 증가로 시원한 느낌 */
          vertical-align: middle;
          color: #334155;
          border-bottom: 1px solid #f1f5f9 !important;
          font-size: 0.925rem;
      }
      .table tbody tr:hover { background-color: #f8fafc !important; /* 아주 은은한 호버 효과 */ }

      /* 데이터 강조 스타일 */
      .table td strong { color: #0f172a; font-weight: 600; } /* 거래처명 강조 */
      .text-secondary.fw-bold { /* 거래처 코드 뱃지 스타일 */
          background-color: #eef2f6;
          color: #475569 !important;
          padding: 4px 8px;
          border-radius: 6px;
          font-size: 0.8rem;
          font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, monospace;
      }

      /* 수정 버튼 (세련된 고스트 버튼 스타일) */
      .btn-update {
        color: #d97706; /* 앰버 색상 */
        background-color: #fffbeb;
        border: 1px solid #fcd34d;
        font-weight: 600;
        font-size: 0.85rem;
        padding: 6px 14px;
        border-radius: 6px;
        transition: all 0.2s ease-in-out;
      }
      .btn-update:hover {
        background-color: #d97706;
        color: white;
        border-color: #d97706;
        box-shadow: 0 4px 6px rgba(217, 119, 6, 0.15);
      }

      /* 모달 디자인 */
      .modal-content {
          border-radius: 16px !important; /* 더 둥근 모달 */
          border: none;
          box-shadow: 0 20px 40px rgba(0,0,0,0.1);
      }
      .modal-header {
          border-bottom: 1px solid #f1f5f9;
          padding: 20px 24px;
          background-color: #fff;
          border-top-left-radius: 16px;
          border-top-right-radius: 16px;
      }
      .modal-title { font-weight: 700; color: #1e293b; font-size: 1.25rem; }
      .modal-body { padding: 24px; }
      .modal-footer {
          border-top: 1px solid #f1f5f9;
          padding: 16px 24px;
      }
      .btn-outline-secondary {
          border-radius: 8px; color: #64748b; border-color: #cbd5e1; font-weight: 600;
      }
      .btn-outline-secondary:hover { background-color: #f1f5f9; color: #334155; border-color: #94a3b8; }

    </style>
  </head>

  <body>
    <div class="layout-wrapper layout-content-navbar">
      <div class="layout-container">
        <c:import url="/WEB-INF/views/template/aside.jsp"></c:import>
        <div class="layout-page">
        <c:import url="/WEB-INF/views/template/header.jsp"></c:import>
          <div class="content-wrapper d-flex flex-column">

            <div class="container-xxl flex-grow-1 container-p-y">

			  <div class="d-flex justify-content-between align-items-center page-title-area mb-4">
				  <h4 class="mb-0">거래처 목록</h4>
				  <button class="btn btn-primary"
				          data-bs-toggle="modal"
				          data-bs-target="#vendorAddModal">
				    <i class="bx bx-plus me-1"></i> 거래처 추가
				  </button>
				</div>

              <div class="card search-card">
				  <div class="card-body">
				        <form method="get" action="/vendor/list">
						  <div class="row g-3 align-items-end">

						    <div class="col-md-4">
						      <label class="form-label">거래처명</label>
						      <input type="text"
						             class="form-control"
						             name="vendorName"
						             value="${vendorDTO.vendorName}"
                                     placeholder="거래처명을 입력하세요">
						    </div>

						    <div class="col-md-3">
						      <label class="form-label">사업자 번호</label>
						      <input type="text"
						             class="form-control"
						             name="vendorBusinessNumber"
						             value="${vendorDTO.vendorBusinessNumber}"
                                     placeholder="'-' 없이 숫자만 입력">
						    </div>

						    <div class="col-md-3">
						      <label class="form-label">거래처 코드</label>
						      <input type="text"
						             class="form-control"
						             name="vendorCode"
						             value="${vendorDTO.vendorCode}"
                                     placeholder="코드 입력">
						    </div>

						    <div class="col-md-2 d-grid">
						      <button type="submit" class="btn btn-primary">
						        <i class="bx bx-search me-1"></i> 검색
						      </button>
						    </div>

						  </div>
						</form>
				  </div>
				</div>

                <div class="card">
				  <div class="table-responsive text-nowrap">
				    <table class="table">
				      <thead>
				        <tr>
				          <th>거래처코드</th>
				          <th>거래처명</th>
				          <th>사업자번호</th>
				          <th>사업자주소</th>
				          <th>대표자명</th>
				          <th>담당자명</th>
				          <th>전화번호</th>
				          <th>이메일</th>
				          <th>관리</th>
				        </tr>
				      </thead>
				      <tbody>
				        <c:forEach var="v" items="${vendorList}">
				          <tr>
				            <td><span class="text-secondary fw-bold">${v.vendorCode}</span></td>
				            <td><strong>${v.vendorName}</strong></td>
				            <td>${v.vendorBusinessNumber}</td>
				            <td style="max-width: 200px; overflow: hidden; text-overflow: ellipsis;">${v.vendorAddress}</td>
				            <td class="text">${v.vendorCeoName}</td>
				            <td>${v.vendorManagerName}</td>
				            <td>${v.vendorManagerTel}</td>
				            <td>${v.vendorManagerEmail}</td>
				            <td>
				              <button class="btn-update"
				                data-bs-toggle="modal"
				                data-bs-target="#editModal"
				                onclick="update(${v.vendorId})">
				                수정
				              </button>
				            </td>
				          </tr>
				        </c:forEach>
				      </tbody>
				    </table>
				  </div>
				</div>
			</div>
            <c:import url="/WEB-INF/views/template/footer.jsp"></c:import>
            <div class="content-backdrop fade"></div>
          </div>
          </div>
        </div>

      <div class="layout-overlay layout-menu-toggle"></div>
    </div>
    <div class="modal fade" id="editModal" tabindex="-1">
	  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
	    <form class="modal-content" action="/vendor/update" method="post">
	      <div class="modal-header">
	        <h5 class="modal-title">거래처 정보 수정</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>

	      <div class="modal-body">
	        <input type="hidden" name="vendorId" id="vendorId">

	        <div class="mb-4">
	          <label class="form-label">담당자 번호</label>
	          <input type="text" class="form-control" name="vendorManagerTel" id="vendorManagerTel" placeholder="010-0000-0000">
	        </div>

	        <div class="mb-4">
	          <label class="form-label">담당자 이름</label>
	          <input type="text" class="form-control" name="vendorManagerName" id="vendorManagerName" placeholder="이름 입력">
	        </div>

	        <div class="mb-3">
	          <label class="form-label">담당자 이메일</label>
	          <input type="email" class="form-control" name="vendorManagerEmail" id="vendorManagerEmail" placeholder="example@email.com">
	        </div>
	      </div>

	      <div class="modal-footer">
	        <button type="button" class="btn btn-outline-secondary px-4" data-bs-dismiss="modal">취소</button>
	        <button type="submit" class="btn btn-primary px-4">저장 완료</button>
	      </div>
	    </form>
	  </div>
	</div>

	<div class="modal fade" id="vendorAddModal" tabindex="-1" aria-hidden="true">
	  <div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
	    <div class="modal-content">

	      <div class="modal-header">
	        <h5 class="modal-title">신규 거래처 등록</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>

	      <div class="modal-body">
	        <form action="add" method="post" id="vendorAddForm">
	          <div class="row">
	            <div class="col-md-12 mb-4">
	              <label class="form-label">사업자 번호 <span class="text-danger">*</span></label>
	              <div class="d-flex gap-2 align-items-center">
	                <input type="text" class="form-control text-center" name="vendorBusinessNumber1" maxlength="3" placeholder="123" required style="max-width: 100px;">
	                <span class="text-muted fw-bold">-</span>
	                <input type="text" class="form-control text-center" name="vendorBusinessNumber2" maxlength="2" placeholder="45" required style="max-width: 80px;">
	                <span class="text-muted fw-bold">-</span>
	                <input type="text" class="form-control text-center" name="vendorBusinessNumber3" maxlength="5" placeholder="67890" required style="max-width: 120px;">
	              </div>
	            </div>

	            <div class="col-md-12 mb-4">
	              <label class="form-label">사업자 이름 <span class="text-danger">*</span></label>
	              <input type="text" class="form-control" name="vendorName" placeholder="거래처명 입력" required>
	            </div>

	            <div class="col-md-12 mb-4">
	              <label class="form-label">사업자 주소 <span class="text-danger">*</span></label>
	              <div class="input-group mb-2">
	                <input type="text" class="form-control" id="vendorAddress" name="vendorAddress" placeholder="주소 검색을 클릭하세요" readonly required>
	                <button type="button" class="btn btn-primary" onclick="execDaumPostcode()">주소 검색</button>
	              </div>
	              <input type="text" class="form-control" id="vendorAddressDetail" name="vendorAddressDetail" placeholder="상세주소 입력">
	            </div>

	            <div class="col-md-6 mb-4"><label class="form-label">대표자명 <span class="text-danger">*</span></label><input type="text" class="form-control" name="vendorCeoName" required placeholder="대표자 이름"></div>
	            <div class="col-md-6 mb-4"><label class="form-label">업태명</label><input type="text" class="form-control" name="vendorBusinessType" placeholder="예: 도소매"></div>
	            <div class="col-md-4 mb-3"><label class="form-label">담당자명</label><input type="text" class="form-control" name="vendorManagerName" placeholder="담당자 이름"></div>
	            <div class="col-md-4 mb-3"><label class="form-label">담당자 번호</label><input type="text" class="form-control" name="vendorManagerTel" placeholder="010-0000-0000"></div>
	            <div class="col-md-4 mb-3"><label class="form-label">담당자 이메일</label><input type="email" class="form-control" name="vendorManagerEmail" placeholder="example@email.com"></div>
	          </div>
	        </form>
	      </div>

	      <div class="modal-footer">
	        <button type="button" class="btn btn-outline-secondary px-4" data-bs-dismiss="modal">취소</button>
	        <button type="submit" class="btn btn-primary px-4" form="vendorAddForm">등록 완료</button>
	      </div>
	    </div>
	  </div>
	</div>

    <script src="/vendor/libs/jquery/jquery.js"></script>
    <script src="/vendor/libs/popper/popper.js"></script>
    <script src="/vendor/js/bootstrap.js"></script>
    <script src="/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>
    <script src="/vendor/js/menu.js"></script>
    <script src="/vendor/libs/apex-charts/apexcharts.js"></script>
    <script src="/js/main.js"></script>
    <script src="/js/dashboards-analytics.js"></script>
    <script async defer src="https://buttons.github.io/buttons.js"></script>
    <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="/js/vendor/main.js"></script>
    <script src="/js/vendor/search.js"></script>
  </body>
</html>