<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<html
  lang="ko"
  class="light-style layout-menu-fixed"
  dir="ltr"
  data-theme="theme-default"
>
<head>
  <meta charset="utf-8" />
  <meta name="viewport"
        content="width=device-width, initial-scale=1.0, user-scalable=no" />

  <title>알림 센터</title>

  <!-- Fonts -->
  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
  <link
    href="https://fonts.googleapis.com/css2?family=Public+Sans:wght@400;500;600;700&display=swap"
    rel="stylesheet"
  />

  <!-- Icons -->
  <link rel="stylesheet" href="/vendor/fonts/boxicons.css" />

  <!-- Core CSS -->
  <link rel="stylesheet" href="/vendor/css/core.css" />
  <link rel="stylesheet" href="/vendor/css/theme-default.css" />
  <link rel="stylesheet" href="/css/demo.css" />

  <script src="/vendor/js/helpers.js"></script>
  <script src="/js/config.js"></script>
</head>

<body>

<div class="layout-wrapper layout-content-navbar">
  <div class="layout-container">

    <!-- 사이드바 -->
    <c:import url="/WEB-INF/views/template/aside.jsp"/>

    <div class="layout-page">
      <!-- 헤더 -->
      <c:import url="/WEB-INF/views/template/header.jsp"/>

      <div class="content-wrapper">
        <div class="container-xxl container-p-y">

          <!-- 페이지 타이틀 -->
		   <h4 class="fw-bold mb-4 d-flex align-items-center">
			 <i class="bx bx-bell me-2"></i>
			 알림 센터
		   </h4>

          <!-- 가운데 정렬 + 폭 제한 -->
          <div class="row justify-content-center">
            <div class="col-lg-9 col-xl-8">

              <!-- 알림 카드 1 (안읽음) -->
              <div class="card mb-2 border-start border-4 border-primary">
                <div class="card-body py-3 px-3">

                  <div class="d-flex justify-content-between align-items-start">

                    <!-- 왼쪽 -->
                    <div class="d-flex align-items-start">
                      <div class="me-3">
                        <span class="badge bg-label-primary p-2">
                          <i class="bx bx-file"></i>
                        </span>
                      </div>

                      <div>
                        <div class="fw-bold">
                          결재 요청이 도착했습니다
                        </div>
                        <div class="text-muted small">
                          결재 문서 번호 A-1023에 대한 승인 요청입니다.
                        </div>
                        <div class="text-muted small mt-1">
                          👤 김대리 · 5분 전 · 결재 알림
                        </div>
                      </div>
                    </div>

                    <!-- 버튼 -->
                    <a href="#" class="btn btn-sm btn-outline-primary">
                      이동
                    </a>

                  </div>
                </div>
              </div>

              <!-- 알림 카드 2 -->
              <div class="card mb-2">
                <div class="card-body py-3 px-3">

                  <div class="d-flex justify-content-between align-items-start">

                    <div class="d-flex align-items-start">
                      <div class="me-3">
                        <span class="badge bg-label-success p-2">
                          <i class="bx bx-package"></i>
                        </span>
                      </div>

                      <div>
                        <div class="fw-bold">
                          발주가 승인되었습니다
                        </div>
                        <div class="text-muted small">
                          발주 번호 B-5581이 승인 처리되었습니다.
                        </div>
                        <div class="text-muted small mt-1">
                          👤 시스템 · 어제 · 발주 알림
                        </div>
                      </div>
                    </div>

                    <a href="#" class="btn btn-sm btn-outline-secondary">
                      이동
                    </a>

                  </div>
                </div>
              </div>

              <!-- 알림 카드 3 -->
              <div class="card mb-2">
                <div class="card-body py-3 px-3">

                  <div class="d-flex justify-content-between align-items-start">

                    <div class="d-flex align-items-start">
                      <div class="me-3">
                        <span class="badge bg-label-warning p-2">
                          <i class="bx bx-video"></i>
                        </span>
                      </div>

                      <div>
                        <div class="fw-bold">
                          교육 수료 기한이 임박했습니다
                        </div>
                        <div class="text-muted small">
                          필수 교육 과정 수료 기한이 3일 남았습니다.
                        </div>
                        <div class="text-muted small mt-1">
                          👤 인사팀 · 2026-01-01 · 교육 알림
                        </div>
                      </div>
                    </div>

                    <a href="#" class="btn btn-sm btn-outline-secondary">
                      이동
                    </a>

                  </div>
                </div>
              </div>

            </div>
          </div>

        </div>

        <!-- 푸터 -->
        <c:import url="/WEB-INF/views/template/footer.jsp"/>

        <div class="content-backdrop fade"></div>
      </div>
    </div>
  </div>

  <div class="layout-overlay layout-menu-toggle"></div>
</div>

<!-- JS -->
<script src="/vendor/libs/jquery/jquery.js"></script>
<script src="/vendor/libs/popper/popper.js"></script>
<script src="/vendor/js/bootstrap.js"></script>
<script src="/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>
<script src="/vendor/js/menu.js"></script>
<script src="/js/main.js"></script>

</body>
</html>
