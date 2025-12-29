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
				          <th>즐겨찾기</th>
				          <th>물품코드</th>
				          <th>물품명</th>
				          <th>카테고리</th>
				          <th>단가</th>
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
				        	<td class="text-center">
							    <button class="btn btn-sm btn-light"
							      onclick="toggleFavorite(this, 'am0012')"
							      title="즐겨찾기">
							      <i class="bx bx-star"></i>
							    </button>
						  	</td>
				            <td>am0012</td>
				            <td>콜롬비아 원두 5kg</td>
				            <td>식품</td>
				            <td>20,000원</td>
				            <td>사용</td>
				            <td>
				              <button class="btn btn-sm btn-warning"
				                data-bs-toggle="modal"
				                data-bs-target="#editModal"
				                onclick="setEditData('${item.id}','${item.itemName}','${item.price}','${item.useYn}')">
				                수정
				              </button>
				            </td>
				          </tr>
				          <tr>
				          	<td class="text-center">
							    <button class="btn btn-sm btn-light"
							      onclick="toggleFavorite(this, 'am0013')"
							      title="즐겨찾기">
							      <i class="bx bx-star"></i>
							    </button>
						  	</td>
				            <td>am0013</td>
				            <td>브라질 원두 5kg</td>
				            <td>식품</td>
				            <td>10,000원</td>
				            <td>사용</td>
				            <td>
				              <button class="btn btn-sm btn-warning"
				                data-bs-toggle="modal"
				                data-bs-target="#editModal"
				                onclick="setEditData('${item.id}','${item.itemName}','${item.price}','${item.useYn}')">
				                수정
				              </button>
				            </td>
				          </tr>
				          <tr>
				          	<td class="text-center">
							    <button class="btn btn-sm btn-light"
							      onclick="toggleFavorite(this, 'am0014')"
							      title="즐겨찾기">
							      <i class="bx bx-star"></i>
							    </button>
						  	</td>
				            <td>am0014</td>
				            <td>인도네이사 원두 5kg</td>
				            <td>식품</td>
				            <td>15,000원</td>
				            <td>사용</td>
				            <td>
				              <button class="btn btn-sm btn-warning"
				                data-bs-toggle="modal"
				                data-bs-target="#editModal"
				                onclick="setEditData('${item.id}','${item.itemName}','${item.price}','${item.useYn}')">
				                수정
				              </button>
				            </td>
				          </tr>
				          <tr>
				          	<td class="text-center">
							    <button class="btn btn-sm btn-light"
							      onclick="toggleFavorite(this, 'ml0009')"
							      title="즐겨찾기">
							      <i class="bx bx-star"></i>
							    </button>
						  	</td>
				            <td>ml0009</td>
				            <td>조지아 커피 머신</td>
				            <td>자재</td>
				            <td>150,000원</td>
				            <td>사용</td>
				            <td>
				              <button class="btn btn-sm btn-warning"
				                data-bs-toggle="modal"
				                data-bs-target="#editModal"
				                onclick="setEditData('${item.id}','${item.itemName}','${item.price}','${item.useYn}')">
				                수정
				              </button>
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


	<div class="modal fade" id="editModal" tabindex="-1">
	  <div class="modal-dialog">
	    <form class="modal-content" action="/item/update" method="post">
	      <div class="modal-header">
	        <h5 class="modal-title">원재료 수정</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>
	
	      <div class="modal-body">
	        <input type="hidden" name="id" id="editId">
	
	        <div class="mb-3">
	          <label class="form-label">원재료명</label>
	          <input type="text" class="form-control" name="itemName" id="editName">
	        </div>
	
	        <div class="mb-3">
	          <label class="form-label">단가</label>
	          <input type="number" class="form-control" name="price" id="editPrice">
	        </div>
	
	        <div class="mb-3">
	          <label class="form-label">사용 여부</label>
	          <select class="form-select" name="useYn" id="editUseYn">
	            <option value="Y">사용</option>
	            <option value="N">미사용</option>
	          </select>
	        </div>
	      </div>
	
	      <div class="modal-footer">
	        <button type="submit" class="btn btn-primary">수정</button>
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	      </div>
	    </form>
	  </div>
	</div>
	


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
