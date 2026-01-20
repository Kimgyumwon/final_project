function updateClock() {
    const now = new Date();
    const timeString = now.toLocaleTimeString('ko-KR', { hour12: false , minute: '2-digit', hour: '2-digit'});
    const clock = document.getElementById('headerClock');
    if(clock) clock.innerText = timeString;
}
setInterval(updateClock, 1000);
updateClock();

function date(){
	const now = new Date();
	    const month = String(now.getMonth() + 1).padStart(2, '0');
	    const day = String(now.getDate()).padStart(2, '0');
	    const week = now.toLocaleDateString('ko-KR', { weekday: 'short' });

	    const dayString = `${month}월 ${day}일 (${week}) `;

	    const view = document.getElementById('date');
	    if (view) view.innerText = dayString;
}
date();
	
	

$(document).ready(function(){
	
	fetch('/commute/availability', {
	        method: 'GET'
	    })
	    .then(res => res.json())
	    .then(data => {
	        if (data && data.canCommute === false) {
	            $('#inCommute').hide();
	            $('#outCommute').hide();
	        } else {
	            $('#inCommute').show();
	            $('#outCommute').show();
	        }
	    })
	    .catch(err => {
	        console.error('availability error:', err);
	        $('#inCommute').show();
	        $('#outCommute').show();
	    });

	$(document).on('click', '#inCommute', function(){
	        if(!confirm("현재 시간으로 출근하시겠습니까?")) return;
			
			fetch('/commute/checkIn', {
			                method: 'POST',
			                headers: { 'Content-Type': 'application/json' }
			            })
			            .then(response => response.text()) 
			            .then(data => {
			                if (data === 'success') {
			                    alert("출근 처리가 완료되었습니다. 오늘도 화이팅!");
			                    location.reload(); 
			                } else if (data === 'already') {
			                    alert("이미 출근 처리가 되어 있습니다.");
			                } else if (data === 'blocked') {
								alert("오늘은 공휴일/연차라 퇴근 처리가 불가능합니다.");
							} else {
			                    alert("출근 처리에 실패했습니다. (관리자 문의)");
			                }
			            })
			            .catch(error => {
			                console.error('Error:', error);
			                alert("서버 통신 중 오류가 발생했습니다.");
			            });
			        });
			    })
				
		$(document).ready(function(){
				$(document).on('click', '#outCommute', function(){
					if(!confirm("현재 시간으로 퇴근 하시겠습니까?")) return;
					
					fetch('/commute/checkOut', {
						method: 'POST',
						headers: { 'Content-Type': 'application/json' }
			            })
			            .then(response => response.text()) 
			            .then(data => {
			                if (data === 'success') {
			                    alert("퇴근 처리가 완료되었습니다. 오늘도 수고하셨습니다!");
			                    location.reload(); 
			                } else if (data === 'already') {
			                    alert("이미 퇴근 처리가 되어 있습니다.");
			                } else if (data === 'blocked') {
							    alert("오늘은 공휴일/연차라 퇴근 처리가 불가능합니다.");
							} else{
			                    alert("퇴근 처리에 실패했습니다. (관리자 문의)");
			                } 
			            })
			            .catch(error => {
			                console.error('Error:', error);
			                alert("서버 통신 중 오류가 발생했습니다.");
			            });
			        });
			    })			
				

function submitPasswordChange(){
	let nowPassword = $('#nowPassword').val();
	let changePassword = $('#newPassword').val();
	let confirmPassword = $('#confirmPassword').val();
	let $pwErrorMsg = $('#pwErrorMsg');
	
	if(!nowPassword){
		$pwErrorMsg.text("현재 비밀번호를 입력해 주세요").show();
		$('#nowPassword').focus();
		return;
	}
	
	if(!changePassword){
		$pwErrorMsg.text("새 비밀번호를 입력해 주세요").show();
		$('#newPassword').focus();
		return;
	}
	
	if(confirmPassword != changePassword){
		$pwErrorMsg.text("비밀번호가 일치하지 않습니다.").show();
		$('#confirmPassword').focus();
		return;
	}
	
	$.ajax({
        url: '/member/changePassword', 
        type: 'POST',
        data: { 
            nowPassword: nowPassword,
            changePassword: changePassword
        },
        success: function(response) {
            if(response === "success") {
                alert('비밀번호가 변경되었습니다.');
				
				const url = new URL(window.location.href);
				url.searchParams.delete("forcePw");
				window.location.href = url.toString();
            } else {
                $pwErrorMsg.text(response).show();
            }
        },
        error: function() {
            $pwErrorMsg.text('서버 통신 중 오류가 발생했습니다.').show();
        }
    });
}

$(function () {
  const params = new URLSearchParams(window.location.search);

  if (params.get("forcePw") === "1") {

    if (window.bootstrap) {
      const modalEl = document.getElementById("changePasswordModal");
      const modal = new bootstrap.Modal(modalEl);
      modal.show();
    } else {
      $('#changePasswordModal').modal('show');
    }

    const url = new URL(window.location.href);
    url.searchParams.delete("forcePw");
    history.replaceState({}, "", url.toString());
  }
});
