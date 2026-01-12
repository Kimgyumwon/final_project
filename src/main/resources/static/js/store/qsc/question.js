document.addEventListener('DOMContentLoaded', function() {
    const registerModal = document.getElementById('registerQuestionModal');
    registerModal.addEventListener('hidden.bs.modal', function () {
        document.getElementById('registerStoreForm').reset();
    });
});

function handleMaxScore(el) {
    el.value = el.value.replace(/[^0-9]/g, '');

    if (el.value !== '') {
        const num = parseInt(el.value);

        if (num > 10) {
            el.value = '10';
        }
        else if (num === 0) {
             el.value = '';
        }
    }
}

async function submitQuestionRegistration() {
    const listCategory = document.getElementById('listCategory').value;
    const listMaxScore = document.getElementById('listMaxScore').value;
    const listQuestion = document.getElementById('listQuestion').value;

    if (!listQuestion) {
        alert("질문을 입력해주세요.");
        document.getElementById('listQuestion').focus();
        return;
    }

    const formData = {
        listCategory: listCategory,
        listMaxScore: listMaxScore,
        listQuestion: listQuestion
    };

    try {
        const response = await fetch('/store/qsc/admin/question/add', {
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

        alert("QSC 질문이 성공적으로 등록되었습니다.");
        
        const modalEl = document.getElementById('registerQuestionModal');
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

function movePage(page) {
    if (page < 1) page = 1;
    document.getElementById("page").value = page;
    document.getElementById("questionSearchForm").submit();
}

function searchQuestion() {
    document.getElementById("page").value = 1;
    document.getElementById("questionSearchForm").submit();
}

function resetSearchForm() {
	const form = document.getElementById('questionSearchForm');
	
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
	var searchParams = $('#questionSearchForm').serialize();
	location.href='/store/qsc/question/downloadExcel?' + searchParams;
}