function movePage(page) {
    if (page < 1) page = 1;

    const form = document.getElementById('searchForm');
    const formData = new FormData(form);
    const params = new URLSearchParams(formData);

    params.set('page', page);

    const currentUrlParams = new URLSearchParams(window.location.search);
    currentUrlParams.forEach((value, key) => {
        if (key.startsWith('sortConditions')) {
            params.append(key, value);
        }
    });

    location.href = form.action + '?' + params.toString();
}
    $(document).ready(function () {
        const init = $("#perPageInput").val() || "10";
        $("#perPageSelect").val(init);

        $("#perPageSelect").on("change", function () {
          $("#perPageInput").val($(this).val());
          movePage(1);
        });

        $("#btnExcel").on("click", function () {
          const form = document.getElementById('searchForm');
          const formData = new FormData(form);
          const params = new URLSearchParams(formData);

          window.location.href = "/member/admin_login_history_excel?" + params.toString();
        });
      });