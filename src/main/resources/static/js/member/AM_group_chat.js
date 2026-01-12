let currentDeptFilter = 0; 
let currentDeptName = "전체 사원";
let searchTimer = null; // 검색어 입력 딜레이를 위한 타이머 변수

$(document).ready(function() {
    renderTable(0);

    $(".dept-item").click(function() {
        $(".dept-item").removeClass("active");
        $(this).addClass("active");

        currentDeptFilter = $(this).data("dept");
        
        let deptName = $(this).find("span").first().text().trim();
        if(deptName.startsWith("└")) deptName = deptName.substring(1).trim();
        currentDeptName = deptName;

        $("#searchMember").val(''); 

        renderTable(currentDeptFilter, currentDeptName);
    });

    $("#searchMember").on("keyup", function() {
        clearTimeout(searchTimer);
        searchTimer = setTimeout(function() {
            renderTable(currentDeptFilter, currentDeptName);
        }, 300);
    });

    $("#checkRetired").change(function() {
        renderTable(currentDeptFilter, currentDeptName);
    });
});

function renderTable(deptCode, deptName = currentDeptName) {
    let searchKeyword = $("#searchMember").val();
    let isRetiredIncluded = $("#checkRetired").is(":checked"); 

    if (deptCode == 0) {
        $("#selectedDeptTitle").text("전체 사원");
    } else {
        $("#selectedDeptTitle").text(deptName);
    }

    $.ajax({
        url: "/member/checkCount", 
        type: "GET",
        data: { 
            deptCode: deptCode, 
            includeRetired: isRetiredIncluded, 
            keyword: searchKeyword
        },
        success: function(data) {
            drawTable(data); 
        },
        error: function(xhr, status, error) {
            console.error("데이터 로드 실패:", error);
        }
    });
}

function drawTable(memberList) {
    const tbody = $("#memberTableBody");
    tbody.empty();

    $("#selectedDeptCount").text(`(총 ${memberList.length}명)`);

    if (memberList.length === 0) {
        $("#noDataMessage").show();
    } else {
        $("#noDataMessage").hide();
    }

    memberList.forEach(member => {
        let profileImg = member.memProfileSavedName 
            ? `/fileDownload/profile?fileSavedName=${member.memProfileSavedName}` 
            : '/fileDownload/profile/default_img.jpg'; 

        let statusBadge = '';
        if(member.memIsActive == 'Y' || member.memIsActive == 1 || member.memIsActive === true) {
            statusBadge = '<span class="badge bg-label-success">재직</span>';
        } else {
            statusBadge = '<span class="badge bg-label-secondary">퇴사</span>';
        }

        const row = `
            <tr>
                <td>
                    <div class="user-wrapper">
                        <img src="${profileImg}" alt="Avatar" class="user-avatar">
                        <div class="user-info">
                            <h6>${member.memName}</h6>
                            <small>${member.memberId}</small>
                        </div>
                    </div>
                </td>
                <td>
                    <span class="text-body fw-medium">${member.memDeptName || '-'}</span>
                </td>
                <td>
                    <span class="position-badge">${member.memPositionName || '-'}</span>
                </td>
                <td class="text-center">${statusBadge}</td>
                <td>
                    <span class="text-muted" style="font-family:inherit;">${member.memPhone || ''}</span>
                </td>
                <td class="text-center">
                    <button onclick="openMemberDetail('${member.memberId}')" class="btn btn-icon btn-sm btn-label-secondary" title="상세 정보">
                        <i class="bx bx-show"></i>
                    </button>
                </td>
            </tr>
        `;
        tbody.append(row);
    });
}


function openMemberDetail(id) {
    const myModal = new bootstrap.Modal(document.getElementById('modalMemberDetail'));
    $("#modalId").val(id);
    myModal.show();
}

function saveMemberDetails() {
    alert("저장 기능은 구현 필요");
}

function openDeptEdit(event, deptId, deptName) {
    event.stopPropagation();
    $("#editDeptId").val(deptId);
    $("#editDeptName").val(deptName);
    new bootstrap.Modal(document.getElementById('modalDeptEdit')).show();
}

function saveAuth() {
    alert("권한 설정이 저장되었습니다.");
    const modal = bootstrap.Modal.getInstance(document.getElementById('modalAuth'));
    modal.hide();
}