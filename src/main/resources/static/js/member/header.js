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
		$(document).on('click', '#inCommute', function(){
		        if(!confirm("현재 시간으로 출근하시겠습니까?")) return;
		})
		
		
	})