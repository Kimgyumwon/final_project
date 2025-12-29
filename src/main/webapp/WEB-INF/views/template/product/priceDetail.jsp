<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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

    <title>Dashboard - Analytics | Sneat - Bootstrap 5 HTML Admin Template - Pro</title>

    <meta name="description" content="" />

    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="../assets/img/favicon/favicon.ico" />

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
      rel="stylesheet"
    />

    <!-- Icons. Uncomment required icon fonts -->
    <link rel="stylesheet" href="/vendor/fonts/boxicons.css" />

    <!-- Core CSS -->
    <link rel="stylesheet" href="/vendor/css/core.css" class="template-customizer-core-css" />
    <link rel="stylesheet" href="/vendor/css/theme-default.css" class="template-customizer-theme-css" />
    <link rel="stylesheet" href="/css/demo.css" />

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />

    <link rel="stylesheet" href="/vendor/libs/apex-charts/apex-charts.css" />

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="/vendor/js/helpers.js"></script>

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="/js/config.js"></script>
  </head>

  <body>
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-content-navbar">
      <div class="layout-container">
        <!-- Menu -->
		<c:import url="/WEB-INF/views/template/aside.jsp"></c:import>
        <!-- / Menu -->

        <!-- Layout container -->
        <div class="layout-page">
          <!-- Content wrapper -->
          <div class="content-wrapper">
            
            <!-- Content -->	
            <div class="container-xxl flex-grow-1 container-p-y">
              <div class="row">
				<div class="card">
				  <h5 class="card-header">원재료 목록</h5>
				  <div class="table-responsive text-nowrap">
				    <table class="table">
				      <thead>
				        <tr>
				          <th>물품코드</th>
				          <th>물품명</th>
				          <th>카테고리</th>
				          <th>단가</th>
				          <th>등록자</th>
				          <th>사용여부</th>
				        </tr>
				      </thead>
				      <tbody>
				        <%-- <c:forEach var="item" items="${itemList}">
				          <tr>
				            <td>${item.}</td>
				            <td>${item.}</td>
				            <td>${item.price}</td>
				            <td>${item.useYn}</td>
				            <td>
				              <button class="btn btn-sm btn-warning"
				                data-bs-toggle="modal"
				                data-bs-target="#editModal"
				                onclick="setEditData('${item.id}','${item.itemName}','${item.price}','${item.useYn}')">
				                수정
				              </button>
				            </td>
				          </tr>
				        </c:forEach> --%>
				        <tr>
				            <td>am0012</td>
				            <td>콜롬비아 원두 5kg</td>
				            <td>
			                	<span class="badge bg-label-warning">식품</span>
			                </td>
				            <td>5,000원</td>
				            <td>1122333</td>
				            <td>
			                	<span class="badge bg-label-danger">미사용</span>
			                </td>
				          </tr>
				          <tr>
				            <td>am0012</td>
				            <td>콜롬비아 원두 5kg</td>
				            <td>
			                	<span class="badge bg-label-warning">식품</span>
			                </td>
				            <td>10,000원</td>
				            <td>1122333</td>
				            <td>
			                	<span class="badge bg-label-success">사용</span>
			                </td>
				          </tr>
				          <tr>
				            <td>am0014</td>
				            <td>인도네이사 원두 5kg</td>
				            <td>
			                	<span class="badge bg-label-warning">식품</span>
			                </td>
				            <td>15,000원</td>
				            <td>1122333</td>
				            <td>
			                	<span class="badge bg-label-success">사용</span>
			                </td>
				          </tr>
				          <tr>
				            <td>am0018</td>
				            <td>커피머신</td>
				            <td>
			                	<span class="badge bg-label-dark">비식품</span>
			                </td>
				            <td>115,000원</td>
				            <td>1122333</td>
				            <td>
			                	<span class="badge bg-label-success">사용</span>
			                </td>
				          </tr>
				      </tbody>
				    </table>
				  </div>
				</div>
              </div>
            </div>
            <!-- / Content -->

            <!-- Footer -->
            <c:import url="/WEB-INF/views/template/footer.jsp"></c:import>
            <!-- / Footer -->

            <div class="content-backdrop fade"></div>
          </div>
          <!-- Content wrapper -->
        </div>
        <!-- / Layout page -->
      </div>

      <!-- Overlay -->
      <div class="layout-overlay layout-menu-toggle"></div>
    </div>
    <!-- / Layout wrapper -->

    <!-- Core JS -->
    <!-- build:js assets/vendor/js/core.js -->
    <script src="/vendor/libs/jquery/jquery.js"></script>
    <script src="/vendor/libs/popper/popper.js"></script>
    <script src="/vendor/js/bootstrap.js"></script>
    <script src="/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>

    <script src="/vendor/js/menu.js"></script>
    <!-- endbuild -->

    <!-- Vendors JS -->
    <script src="/vendor/libs/apex-charts/apexcharts.js"></script>

    <!-- Main JS -->
    <script src="/js/main.js"></script>

    <!-- Page JS -->
    <script src="/js/dashboards-analytics.js"></script>

    <!-- Place this tag in your head or just before your close body tag. -->
    <script async defer src="https://buttons.github.io/buttons.js"></script>
  </body>
</html>
