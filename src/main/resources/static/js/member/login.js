document.addEventListener("DOMContentLoaded", function () {
  const memberId = document.getElementById("memberId");
  const savedId = document.getElementById("savedId");
  const form = document.querySelector(".auth-login-form");

  const saved = getCookie("savedMemberId");
  if (saved) {
    memberId.value = saved;
    savedId.checked = true;
  }

  form.addEventListener("submit", function () {
    if (savedId.checked) {
      setCookie("savedMemberId", memberId.value, 30);
    } else {
      deleteCookie("savedMemberId");
    }
  });
});

function setCookie(cookieName, value, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + 
        ((exdays==null) ? "" : "; expires=" + exdate.toGMTString()) +
        "; path=/";
    document.cookie = cookieName + "=" + cookieValue;
}

function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "=; expires=" + expireDate.toGMTString() + "; path=/";
}


// 3. 쿠키 가져오기
function getCookie(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1) end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}
