//전역변수 선언
var currentContractData = null;
var deletedFileIds = [];
var isContractUpdated = false;

// 금액 formatter
function formatCurrency(amount) {
    if (amount === undefined || amount === null) return "0 원";
    return new Intl.NumberFormat('ko-KR').format(amount) + " 원";
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
        const response = await fetch(`/store/tab/store/search/store?${params.toString()}`, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        });

        if (!response.ok) throw new Error(`서버 오류: ${response.status}`);

        const data = await response.json();
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

function addFileField() {
    const container = document.getElementById('fileContainer');
    const newDiv = document.createElement('div');
    newDiv.className = 'input-group mb-2';
    newDiv.innerHTML = `
        <input type="file" class="form-control" name="contractFiles">
        <button type="button" class="btn btn-outline-danger" onclick="removeFileField(this)">
            <i class="bx bx-minus"></i>
        </button>
    `;
    container.appendChild(newDiv);
}

function removeFileField(button) {
    button.parentElement.remove();
}

(function() {
    const registerModalEl = document.getElementById('registerContractModal');
    
    if (registerModalEl) {
        registerModalEl.addEventListener('hidden.bs.modal', function () {
            const form = document.getElementById('registerContractForm');
            if (form) form.reset();

            const storeIdEl = document.getElementById('storeId');
            if (storeIdEl) storeIdEl.value = '';
            
            const resultList = document.getElementById('storeResultList');
            if (resultList) {
                resultList.style.display = 'none';
                resultList.innerHTML = '';
            }

            const fileContainer = document.getElementById('fileContainer');
            if (fileContainer) {
                fileContainer.innerHTML = `
                    <div class="input-group mb-2">
                        <input type="file" class="form-control" name="contractFiles">
                        <button type="button" class="btn btn-outline-primary" onclick="addFileField()">
                            <i class="bx bx-plus"></i>
                        </button>
                    </div>
                `;
            }
        });
    }
})();

async function submitContractRegistration() {
    const els = {
        storeId: document.getElementById('storeId'),
        royalti: document.getElementById('royalti'),
        deposit: document.getElementById('deposit'),
        startDate: document.getElementById('startDate'),
        endDate: document.getElementById('endDate'),
    };

    if (!els.storeId.value) { alert("가맹점명을 선택해주세요."); return; }
    if (!els.royalti.value) { alert("로얄티를 입력해주세요."); return; }
    if (!els.deposit.value) { alert("여신(보증금)을 입력해주세요."); return; }
    if (!els.startDate.value || !els.endDate.value) { alert("계약기간을 입력해주세요."); return; }

    const formData = new FormData();
    formData.append("storeId", els.storeId.value);
    formData.append("contractRoyalti", els.royalti.value);
    formData.append("contractDeposit", els.deposit.value);
    formData.append("contractStartDate", els.startDate.value);
    formData.append("contractEndDate", els.endDate.value);
	
	const startDate = new Date(els.startDate.value);
	startDate.setHours(0, 0, 0, 0);
	const today = new Date();
	today.setHours(0, 0, 0, 0);
	
	var status = 1;
	if (startDate > today) status = 0;
	
    formData.append("contractStatus", status);

    const fileInputs = document.getElementsByName("contractFiles");
    for (let input of fileInputs) {
        if (input.files.length > 0) {
            formData.append("files", input.files[0]); // fileInputs 태그에서 파일 객체 자체를 append 
        }
    }

    try {
        const response = await fetch('/store/tab/contract/add', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) throw new Error(`서버 오류: ${response.status}`);
        
        alert("계약이 성공적으로 등록되었습니다.");
        
        const modalEl = document.getElementById('registerContractModal');
        const modalInstance = bootstrap.Modal.getInstance(modalEl);
        if (modalInstance) modalInstance.hide();
        
        loadTab('contract');
    } catch (error) {
        console.error('Error:', error);
        alert("등록 중 오류가 발생했습니다.");
    }
}

async function getContractDetail(contractId) {
    try {
        const url = `/store/tab/contract/detail?contractId=${contractId}`;
        const response = await fetch(url, { method: "GET" });

        if (!response.ok) throw new Error(`Error : ${response.status}`);

        const data = await response.json();
        
		// 전역 변수에 저장
        currentContractData = data; 

        document.getElementById('detailContractId').innerText = data.contractId;
        document.getElementById('detailStoreName').innerText = data.storeName;
		document.getElementById('detailMemName').innerText = data.memName;
		document.getElementById('detailStoreAddress').innerText = data.storeAddress;
        document.getElementById('detailStartDate').innerText = data.contractStartDate;
        document.getElementById('detailEndDate').innerText = data.contractEndDate;
        document.getElementById('detailRoyalty').innerText = formatCurrency(data.contractRoyalti);
        document.getElementById('detailDeposit').innerText = formatCurrency(data.contractDeposit);

        const statusArea = document.getElementById('detailStatusArea');
        let badgeHtml = '';
        switch (data.contractStatus) {
            case 0: badgeHtml = '<span class="badge bg-label-warning">PENDING</span>'; break;
            case 1: badgeHtml = '<span class="badge bg-label-info">ACTIVE</span>'; break;
            case 2: badgeHtml = '<span class="badge bg-label-danger">EXPIRED</span>'; break;
            default: badgeHtml = '<span class="badge bg-label-secondary">UNKNOWN</span>';
        }
        statusArea.innerHTML = badgeHtml;
		
		const viewContainer = document.getElementById('detailFileList_view');
	    viewContainer.innerHTML = ""; 

	    if (data.fileDTOs && data.fileDTOs.length > 0) {
	        data.fileDTOs.forEach(file => {
	            const li = document.createElement('li');
	            li.className = "list-group-item d-flex justify-content-between align-items-center"; 

	            let iconClass = "bx bxs-file-blank text-secondary";
	            if (file.fileOriginalName.includes('.pdf')) iconClass = "bx bxs-file-pdf text-danger";
	            else if (file.fileOriginalName.match(/\.(jpg|jpeg|png)$/i)) iconClass = "bx bxs-file-image text-primary";

	            li.innerHTML = `
	                <div class="d-flex align-items-center overflow-hidden">
	                    <i class="${iconClass} fs-4 me-2"></i>
	                    <span class="text-truncate">${file.fileOriginalName}</span>
	                </div>
	                <button class="btn btn-sm btn-outline-primary ms-2" onclick="downloadAttachment('${file.fileSavedName}', '${file.fileOriginalName}')">
	                    <i class="bx bx-download"></i> 다운로드
	                </button>
	            `;
	            viewContainer.appendChild(li);
	        });
	    } else {
	        viewContainer.innerHTML = `<li class="list-group-item text-muted small text-center bg-light">첨부된 파일이 없습니다.</li>`;
	    }

        const modalEl = document.getElementById('detailContractModal');
		const isAlreadyShown = modalEl.classList.contains('show');
		
		if (!isAlreadyShown) {
			isContractUpdated = false;
			
			modalEl.addEventListener('hidden.bs.modal', function () {
				if (isContractUpdated && typeof loadTab === 'function') loadTab('contract');
			}, { once: true });
		}
		
		let modal = bootstrap.Modal.getInstance(modalEl);
		if (!modal) {
	        modal = new bootstrap.Modal(modalEl);
		}
			
        modal.show();
    } catch (error) {
        console.error("계약 상세 조회 오류 : ", error);
        alert("계약 상세 정보를 불러오는데 실패했습니다.");
    }
}

function downloadAttachment(fileSavedName, fileOriginalName) {
    if (!fileSavedName || !fileOriginalName) {
        alert("파일 정보가 올바르지 않습니다.");
        return;
    }
    const url = `/fileDownload/contract?fileSavedName=${encodeURIComponent(fileSavedName)}&fileOriginalName=${encodeURIComponent(fileOriginalName)}`;
    location.href = url;
}

function downloadContractPdf() {
    if (!currentContractData) {
        alert("계약 상세 정보를 찾을 수 없습니다.");
        return;
    }

    const data = currentContractData; // 전역 변수 참조

    document.getElementById('pdfContractId').innerText = data.contractId;
    document.getElementById('pdfStoreName').innerText = data.storeName;
    document.getElementById('pdfStoreNameTable').innerText = data.storeName;
    
    const storeAddr = data.storeAddress;
    const pdfStatusEl = document.getElementById('pdfStoreAddress');
    if(pdfStatusEl) pdfStatusEl.innerText = storeAddr;
    
    document.getElementById('pdfStartDate').innerText = data.contractStartDate;
    document.getElementById('pdfEndDate').innerText = data.contractEndDate;
    document.getElementById('pdfRoyalty').innerText = formatCurrency(data.contractRoyalti);
    document.getElementById('pdfDeposit').innerText = formatCurrency(data.contractDeposit);

    document.getElementById('pdfSignStoreName').innerText = data.storeName;
    document.getElementById('pdfSignOwner').innerText = data.memName;
    document.getElementById('pdfSignAddress').innerText = storeAddr;

	const [year, month, day] = data.contractStartDate.split('-');
    document.getElementById('pdfCreatedAt').innerText = `${year}년 ${month}월 ${day}일`;

    // html2pdf 변환 및 다운로드
    const element = document.getElementById('contractPdfTemplate');
    const opt = {
        margin:       0,
        filename:     `가맹계약서_${data.contractId}_${data.storeName}.pdf`,
        image:        { type: 'jpeg', quality: 1 },
        html2canvas:  { scale: 2, scrollY: 0 },
        jsPDF:        { unit: 'mm', format: 'a4', orientation: 'portrait' }
    };

    html2pdf().set(opt).from(element).save();
}

function enableEditMode() {
    if (!currentContractData) return;
	
	const pdfBtn = document.getElementById('btnPdfDownload');
	
	pdfBtn.style.transition = 'none';
	pdfBtn.classList.add('invisible');

    document.querySelectorAll('.mode-view').forEach(el => el.classList.add('d-none'));
    document.querySelectorAll('.mode-edit').forEach(el => el.classList.remove('d-none'));

    document.getElementById('btnEditMode').classList.add('d-none');
    document.getElementById('btnCloseModal').classList.add('d-none');
    
    document.getElementById('btnSaveContract').classList.remove('d-none');
    document.getElementById('btnCancelEdit').classList.remove('d-none');
	
	document.getElementById('titleArea').innerText='가맹 계약 정보 수정';
	
	deletedFileIds = [];
	
    const existingContainer = document.getElementById('existingFileContainer');
    existingContainer.innerHTML = "";

    if (currentContractData.fileDTOs && currentContractData.fileDTOs.length > 0) {
        currentContractData.fileDTOs.forEach(file => {
            const li = document.createElement('li');
            li.className = "list-group-item d-flex justify-content-between align-items-center";
            li.id = `file-item-${file.fileId}`;
			
			let iconClass = "bx bxs-file-blank text-secondary";
            if (file.fileOriginalName.includes('.pdf')) iconClass = "bx bxs-file-pdf text-danger";
            else if (file.fileOriginalName.match(/\.(jpg|jpeg|png)$/i)) iconClass = "bx bxs-file-image text-primary";

            li.innerHTML = `
                <div class="d-flex align-items-center overflow-hidden">
                    <i class="${iconClass} fs-4 me-2"></i> <span class="text-truncate text-muted">${file.fileOriginalName}</span>
                </div>
                <button class="btn btn-sm btn-outline-danger ms-2" type="button" onclick="markFileAsDeleted(${file.fileId})">
                    <i class="bx bx-trash"></i> 삭제
                </button>
            `;
            existingContainer.appendChild(li);
        });
    }
	
    const newContainer = document.getElementById('newFileContainer');
    newContainer.innerHTML = "";
	addEditFileField();

    document.getElementById('editStartDate').value = currentContractData.contractStartDate;
    document.getElementById('editEndDate').value = currentContractData.contractEndDate;
    document.getElementById('editRoyalty').value = currentContractData.contractRoyalti;
    document.getElementById('editDeposit').value = currentContractData.contractDeposit;
}

function cancelEditMode() {
    document.querySelectorAll('.mode-view').forEach(el => el.classList.remove('d-none'));
    document.querySelectorAll('.mode-edit').forEach(el => el.classList.add('d-none'));

    document.getElementById('btnEditMode').classList.remove('d-none');
    document.getElementById('btnCloseModal').classList.remove('d-none');
	document.getElementById('btnPdfDownload').classList.remove('invisible');
    
    document.getElementById('btnSaveContract').classList.add('d-none');
    document.getElementById('btnCancelEdit').classList.add('d-none');
	
	document.getElementById('titleArea').innerText='가맹 계약 정보';
}

function addEditFileField() {
    const container = document.getElementById('newFileContainer');
    const div = document.createElement('div');
    div.className = "input-group mb-2";
    div.innerHTML = `
        <input type="file" class="form-control" name="editContractFiles">
        <button type="button" class="btn btn-outline-danger" onclick="this.parentElement.remove()">
            <i class="bx bx-minus"></i>
        </button>
    `;
    container.appendChild(div);
}

function markFileAsDeleted(fileId) {
    if(confirm("이 파일을 삭제하시겠습니까? (저장 버튼을 눌러야 최종 반영됩니다)")) {
        deletedFileIds.push(fileId);
        
        const fileItem = document.getElementById(`file-item-${fileId}`);
        if(fileItem) {
            fileItem.classList.add('d-none');
        }
    }
}

async function updateContract() {
    const contractId = currentContractData.contractId;
    const startDate = document.getElementById('editStartDate').value;
    const endDate = document.getElementById('editEndDate').value;
    const royalty = document.getElementById('editRoyalty').value;
    const deposit = document.getElementById('editDeposit').value;

	if (!royalty) { alert("로얄티를 입력해주세요."); return; }
	if (!deposit) { alert("여신(보증금)을 입력해주세요."); return; }
	if (!startDate || !endDate) { alert("계약기간을 입력해주세요."); return; }
	
	const startDateForStatus = new Date(startDate);
	startDateForStatus.setHours(0, 0, 0, 0);
	const today = new Date();
	today.setHours(0, 0, 0, 0);

	var status = 1;
	if (startDateForStatus > today) status = 0;
	
	const formData = new FormData();
	
	formData.append("contractId", contractId);
	formData.append("contractStartDate", startDate);
	formData.append("contractEndDate", endDate);
	formData.append("contractRoyalti", parseInt(royalty));
	formData.append("contractDeposit", parseInt(deposit));
	formData.append("contractStatus", status);
	
	if (deletedFileIds.length > 0) {
		deletedFileIds.forEach(id => {
			formData.append("deleteFileIds", id);
		});
	}
	
	const fileInputs = document.getElementsByName("editContractFiles");
	for (let input of fileInputs) {
		if (input.files.length > 0) {
			formData.append("newFiles", input.files[0]);
		}
	}

    try {
        const response = await fetch('/store/tab/contract/update', { 
            method: 'POST',
            body: formData,
        });

        if (!response.ok) throw new Error(`서버 오류: ${response.status}`);

        alert("계약 정보가 수정되었습니다.");

		isContractUpdated = true;
        await getContractDetail(contractId);
        cancelEditMode(); 
        
    } catch (error) {
        console.error("수정 실패:", error);
        alert("정보 수정 중 오류가 발생했습니다.");
    }
}