/**
 * 
 */



document.addEventListener("DOMContentLoaded", () => {
  loadNotifications();
});

function loadNotifications() {
  fetch("/api/notifications")
    .then(res => res.json())
    .then(data => {
      renderNotifications(data);
      updateNotificationBadge(data);
    })
    .catch(err => console.error("알림 조회 실패", err));
}

/* 🔔 알림 리스트 렌더링 */
function renderNotifications(notifications) {
  const list = document.getElementById("notificationList");
  list.innerHTML = "";

  if (notifications.length === 0) {
    list.innerHTML = `
      <li class="dropdown-item text-center text-muted">
        알림이 없습니다
      </li>
    `;
    return;
  }

  notifications.slice(0, 5).forEach(n => {
    const li = document.createElement("li");

    li.innerHTML = `
      <a class="dropdown-item p-2 rounded mb-2"
         style="${n.notificationReadYn === 'N' ? 'background:#f8f9ff;' : ''}">
        <div class="fw-bold">${n.notificationTitle}</div>
        <small class="text-muted">${n.notificationContent}</small>
      </a>
    `;

    li.onclick = () => {
      fetch(`/api/notifications/${n.notificationId}/read`, { method: "PATCH" });
      location.href = n.notificationLink;
    };

    list.appendChild(li);
  });
}

/* 🔔 안 읽은 알림 개수 */
function updateNotificationBadge(notifications) {
  const badge = document.getElementById("notificationBadge");
  const unread = notifications.filter(n => n.notificationReadYn === "N").length;

  if (unread > 0) {
    badge.textContent = unread;
    badge.style.display = "inline-block";
  } else {
    badge.style.display = "none";
  }
}
