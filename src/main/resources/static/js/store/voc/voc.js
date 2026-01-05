$(function() {
    // 1. JSP에서 값 가져오기
    let startParam = '${pager.startDate}'.trim(); 
    let endParam = '${pager.endDate}'.trim();

    // 2. 초기 날짜 설정 로직
    // 기본값은 '오늘'로 설정 (달력을 열었을 때 보여질 기준 날짜)
    let startMoment = moment();
    let endMoment = moment();
    let hasValidData = false; // 데이터가 실제로 있는지 확인하는 플래그

    // 값이 있고 형식이 올바른지 체크
    if (startParam && endParam) {
        let s = moment(startParam, 'YYYY-MM-DD');
        let e = moment(endParam, 'YYYY-MM-DD');
        
        // 두 날짜가 모두 유효한 날짜(Invalid Date가 아님)라면 적용
        if (s.isValid() && e.isValid()) {
            startMoment = s;
            endMoment = e;
            hasValidData = true;
        }
    }

    // 3. DateRangePicker 초기화
    $('#daterange').daterangepicker({
        startDate: startMoment, // 데이터가 없으면 '오늘', 있으면 '그 날짜'부터 시작
        endDate: endMoment,
        autoUpdateInput: false, // [핵심] 날짜 선택 전까지 input을 자동으로 채우지 않음
        locale: {
            format: "YYYY-MM-DD",
            separator: " ~ ",
            applyLabel: "확인",
            cancelLabel: "취소",
            fromLabel: "부터",
            toLabel: "까지",
            customRangeLabel: "직접 선택",
            daysOfWeek: ["일", "월", "화", "수", "목", "금", "토"],
            monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
            firstDay: 0
        }
    });

    // 4. 화면 초기값 설정 (가장 중요한 부분)
    if (hasValidData) {
        // 데이터가 있을 때만 input에 글자를 채워줌
        $('#daterange').val(startMoment.format('YYYY-MM-DD') + ' ~ ' + endMoment.format('YYYY-MM-DD'));
    } else {
        // 데이터가 없으면 빈 값으로 둠 -> HTML의 placeholder="기간을 선택하세요"가 보임
        $('#daterange').val(''); 
    }

    // 5. [확인] 버튼 눌렀을 때 이벤트
    $('#daterange').on('apply.daterangepicker', function(ev, picker) {
        const sDate = picker.startDate.format('YYYY-MM-DD');
        const eDate = picker.endDate.format('YYYY-MM-DD');

        // 보이는 input 채우기
        $(this).val(sDate + ' ~ ' + eDate);
        
        // 숨겨진 form input 채우기 (서버 전송용)
        $('#startDate').val(sDate);
        $('#endDate').val(eDate);
    });

    // 6. [취소] 버튼 눌렀을 때 이벤트
    $('#daterange').on('cancel.daterangepicker', function(ev, picker) {
        // 값을 싹 비움 -> 다시 placeholder가 보임
        $(this).val('');
        $('#startDate').val('');
        $('#endDate').val('');
    });
});

async function fetchJson(url, options = {}) {
    const response = await fetch(url, options);
    if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
    return response.json();
}

async function searchStore() {
    const keyword = document.getElementById("storeNameInput").value.trim();
    const resultListElement = document.getElementById("storeResultList");

    if (!keyword) {
        alert("검색어를 입력해주세요.");
        return;
    }

    try {
        const params = new URLSearchParams({ keyword: keyword });
		const data = await 	fetchJson(`/store/search?${params.toString()}`, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        });
				
        let listHtml = "";

        if (data.length === 0) {
            listHtml = `<li class="list-group-item text-muted">검색 결과가 없습니다.</li>`;
        } else {
            data.forEach(store => {
                listHtml += `
                    <li class="list-group-item list-group-item-action d-flex align-items-center cursor-pointer" 
                        onclick="selectStore('${store.storeId}', '${store.storeName}', '${store.memName}')">
                        <div class="avatar avatar-sm me-2">
                            <span class="avatar-initial rounded-circle bg-label-primary">
                                ${store.storeName.charAt(0)}
                            </span>
                        </div>
                        <div class="d-flex flex-column">
                            <span class="fw-bold">${store.storeName}</span>
                            <small class="text-muted">${store.memName}</small>
                        </div>
                    </li>
                `;
            });
        }

        resultListElement.innerHTML = listHtml;
        resultListElement.style.display = 'block';

    } catch (error) {
        console.error("Fetch Error:", error);
        alert("가맹점 검색 중 오류가 발생했습니다.");
    }
}

function selectStore(id, name, member) {
    document.getElementById("storeId").value = id;
    document.getElementById("storeNameInput").value = `${name} (${member})`;
    document.getElementById("storeResultList").style.display = 'none';
}

document.addEventListener('click', function(e) {
    const target = e.target;
    const isInputGroup = target.closest('.input-group');
    const isResultList = target.closest('#storeResultList');

    if (!isInputGroup && !isResultList) {
        const listEl = document.getElementById('storeResultList');
        if (listEl) listEl.style.display = 'none';
    }
});

async function submitVocRegistration() {
    const storeId = document.getElementById('storeId').value;
    const vocType = document.getElementById('vocType').value;
    const vocContact = document.getElementById('vocContact').value;
	const vocTitle = document.getElementById('vocTitle').value;
	const vocContents = document.getElementById('vocContents').value;
    
    if (!storeId) {
        alert("가맹점명을 선택해주세요.");
        return;
    }
    if (!vocType) {
        alert("불만유형을 선택해주세요.");
        return;
    }
    if (!vocContact) {
        alert("고객연락처를 입력해주세요.");
        return;
    }
    if (!vocTitle) {
        alert("제목을 입력해주세요.");
        return;
    }
    if (!vocContents) {
        alert("내용을 입력해주세요.");
        return;
    }

    const formData = {
        storeId: storeId,
        vocType: vocType, 
        vocContact: vocContact,
        vocTitle: vocTitle,
        vocContents: vocContents
    };

    try {
        const response = await fetch('/store/voc/add', { 
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', 
				},
            body: JSON.stringify(formData)
        });

        if (!response.ok) {
            throw new Error(`서버 오류: ${response.status}`);
        }

        const result = await response.json();

        alert("VOC가 성공적으로 등록되었습니다.");
        
        const modalEl = document.getElementById('registerVocModal');
        const modalInstance = bootstrap.Modal.getInstance(modalEl);
        if (modalInstance) {
            modalInstance.hide();
        }
        
        location.reload();

    } catch (error) {
        console.error('Error:', error);
        alert("등록 중 오류가 발생했습니다.");
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const vocModalEl = document.getElementById('registerVocModal');

    if (vocModalEl) {
        vocModalEl.addEventListener('hidden.bs.modal', function () {
            document.getElementById('storeId').value = '';
            document.getElementById('storeNameInput').value = '';
            document.getElementById('vocType').selectedIndex = 0;
            document.getElementById('vocContact').value = '';
            document.getElementById('vocTitle').value = '';
            document.getElementById('vocContents').value = '';

            const resultList = document.getElementById('storeResultList');
            if (resultList) {
                resultList.style.display = 'none';
                resultList.innerHTML = '';
            }
        });
    }
});

function movePage(page) {
    if (page < 1) page = 1;
    document.getElementById("page").value = page;
    document.getElementById("vocSearchForm").submit();
}

function searchVoc() {
    document.getElementById("page").value = 1;
    document.getElementById("vocSearchForm").submit();
}

function resetSearchForm() {
	const form = document.getElementById('storeSearchForm');
	
	const inputs = form.querySelectorAll('input[type="text"], input[type="time"]');
	    inputs.forEach(input => {
	        input.value = '';
	    });

	    const selects = form.querySelectorAll('select');
	    selects.forEach(select => {
	        select.value = ''; 
	    });

	    if(document.getElementById('page')) {
	        document.getElementById('page').value = 1;
	    }
}

function downloadExcel() {
	var searchParams = $('#storeSearchForm').serialize();
	location.href='/store/downloadExcel?' + searchParams;
}