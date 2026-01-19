document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var titleEl = document.getElementById('calendarTitle');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'ko',               
        height: 'auto',
        headerToolbar: false,

        datesSet: function(info) {
            if(titleEl) {
                var date = calendar.getDate();
                var year = date.getFullYear();
                var month = String(date.getMonth() + 1).padStart(2, '0');
                titleEl.innerText = year + '. ' + month;
            }
			
			refreshMonthList(calendar);
        },
        
        eventSources: [
             {
                 url: '/member/calendar',
                 method: 'GET',
                 failure: function() { console.log('데이터 로딩 실패'); }
             }
         ],
         eventContent: function(arg) {
             let italicEl = document.createElement('div');
             italicEl.innerHTML = arg.event.title;
             italicEl.style.fontSize = '0.85rem';
             italicEl.style.whiteSpace = 'normal';
             return { domNodes: [italicEl] };
         }
    });

    calendar.render();

    var btnPrev = document.getElementById('btnPrev');
    var btnNext = document.getElementById('btnNext');
    var btnToday = document.getElementById('btnToday');

    if(btnPrev) btnPrev.addEventListener('click', function() { calendar.prev(); });
    if(btnNext) btnNext.addEventListener('click', function() { calendar.next(); });
    if(btnToday) btnToday.addEventListener('click', function() { calendar.today(); });


    var btnShowList = document.getElementById('btnShowList');
    var btnShowCalendar = document.getElementById('btnShowCalendar');
    
    var listWrapper = document.getElementById('listWrapper');
    var calendarWrapper = document.getElementById('calendarWrapper');

    btnShowList.addEventListener('click', function() {
        listWrapper.style.display = 'block';
        calendarWrapper.style.display = 'none';

        btnShowList.classList.add('btn-primary', 'active');       
        btnShowList.classList.remove('btn-outline-primary');
        
        btnShowCalendar.classList.remove('btn-primary', 'active');
        btnShowCalendar.classList.add('btn-outline-primary');
    });

    btnShowCalendar.addEventListener('click', function() {
        listWrapper.style.display = 'none';
        calendarWrapper.style.display = 'block';

        btnShowCalendar.classList.add('btn-primary', 'active');  
        btnShowCalendar.classList.remove('btn-outline-primary');

        btnShowList.classList.remove('btn-primary', 'active');  
        btnShowList.classList.add('btn-outline-primary');

        calendar.render(); 
    });
});

function loadMonthList(startDate, endDate, page = 1) {

  $.ajax({
    url: "/commute/list",
    type: "GET",
    data: {
      startDate: startDate,
      endDate: endDate,
      page: page,
      perPage: 50
    },
    success: function(res) {
      drawAttendanceList(res.list || []);
    },
    error: function(xhr, status, error) {
      console.error("리스트 로드 실패:", error);
      drawAttendanceList([]);
    }
  });

}

function drawAttendanceList(list) {
  const $tbody = $("#attendanceListBody");
  $tbody.empty();

  if (!list || list.length === 0) {
    $tbody.append(`
      <tr>
        <td colspan="4" class="text-center py-4 text-muted">표시할 내역이 없습니다.</td>
      </tr>
    `);
    return;
  }

  list.forEach(row => {
    const date = row.memCommuteWorkDate || "-";
    const type = row.memCommuteState || "-";

    let timeOrDays = "-";
    if (type.includes("연차") || type.includes("휴가") || type.includes("반차")) {
      timeOrDays = row.memCommuteNote || "-"; 
    } else {
      const inTime = row.memCommuteInTime ? row.memCommuteInTime.substring(11, 16) : "-";
      const outTime = row.memCommuteOutTime ? row.memCommuteOutTime.substring(11, 16) : "-";
      timeOrDays = `${inTime} ~ ${outTime}`;
    }

    const note = row.memCommuteNote || "";

    $tbody.append(`
      <tr>
        <td class="text-center">${date}</td>
        <td class="text-center">${type}</td>
        <td class="text-center">${timeOrDays}</td>
        <td class="ps-4">${note}</td>
      </tr>
    `);
  });
}


function toYMD(d){
  const y = d.getFullYear();
  const m = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  return `${y}-${m}-${day}`;
}

function refreshMonthList(calendar) {
  const current = calendar.getDate();
  const y = current.getFullYear();
  const m = current.getMonth();

  const first = new Date(y, m, 1);
  const last  = new Date(y, m + 1, 0);

  const startDate = toYMD(first);
  const endDate   = toYMD(last);

  loadMonthList(startDate, endDate, 1);
}

