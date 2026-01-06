
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {

            // 기본 주소 (도로명 우선)
            let addr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            // 주소 세팅
            document.getElementById("vendorAddress").value = addr;

            // 상세주소로 포커스 이동
            document.getElementById("vendorAddressDetail").focus();
        }
    }).open();
}

function update(vendorId){
	const id = document.querySelector('#vendorId');
	id.value = vendorId;
}