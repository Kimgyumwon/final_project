function movePage(page) {
  if (page < 1) page = 1;

  const form = document.getElementById("searchForm");
  document.getElementById("pageInput").value = page;

  const params = new URLSearchParams(new FormData(form));
  params.set("page", page);

  location.href = form.action + "?" + params.toString();
}

function toggleActive(id, active) {
  $.post("/member/admin_holiday_toggle", { companyHolidayId: id, active: active })
    .done(res => {
      if (res === "success") location.reload();
      else alert("처리 실패");
    })
    .fail(() => alert("서버 오류"));
}

function openAddModal() {
  $("#addError").hide().text("");
  $("#addDate").val("");
  $("#addName").val("");
  const modalEl = document.getElementById("holidayAddModal");
  new bootstrap.Modal(modalEl).show();
}

function submitAddHoliday() {
  const date = $("#addDate").val();
  const name = $("#addName").val().trim();
  const $err = $("#addError");

  if (!date) return $err.text("날짜를 선택하세요.").show();
  if (!name) return $err.text("휴무명을 입력하세요.").show();

  $.ajax({
    url: '/member/admin_holiday_add',
    type: 'POST',
    data: { comHolidayDate: date, comHolidayName: name },
    success: function(res){
      if(res === 'success'){
        alert('추가되었습니다.');
        location.reload();
      } else {
        alert(res); 
      }
    },
    error: function(xhr){
      alert(xhr.responseText || '추가 실패');
    }
  });



function submitEditHoliday() {
  const id = $("#editId").val();
  const date = $("#editDate").val();
  const name = $("#editName").val().trim();

  $.post("/member/admin_holiday_update", {
    companyHolidayId: id,
    comHolidayDate: date,
    comHolidayName: name
  })
    .done(res => {
      if (res === "success") location.reload();
      else alert("수정 실패");
    })
    .fail(() => alert("서버 오류"));
}

$(document).ready(function () {
  const init = $("#perPageInput").val() || "10";
  $("#perPageSelect").val(init);

  $("#perPageSelect").on("change", function () {
    $("#perPageInput").val($(this).val());
    movePage(1);
  });

  $("#btnExcel").on("click", function () {
    const form = document.getElementById("searchForm");
    const params = new URLSearchParams(new FormData(form));
    location.href = "/member/admin_holiday_excel?" + params.toString();
  });
});

function openEditModal(id, date, name, type) {

  $('#editId').val(id);
  $('#editDate').val(date);
  $('#editName').val(name);

  const modalEl = document.getElementById('holidayEditModal');

  if (window.bootstrap) {
    new bootstrap.Modal(modalEl).show();
  } else {
    $('#holidayEditModal').modal('show');
  }
}
}