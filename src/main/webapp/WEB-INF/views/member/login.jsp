<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="ko" class="light-style customizer-hide" dir="ltr">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
    <title>CAFE CORE - Login</title>

    <link href="https://fonts.googleapis.com/css2?family=Public+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="/assets/vendor/fonts/boxicons.css" />

   <style type="text/css">
    /* 1. 기본 폰트 및 베이스 설정 */
    * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Public Sans', sans-serif; }
    body { background-color: #f5f5f9; display: flex; min-height: 100vh; overflow: hidden; }
    
    .auth-wrapper { display: flex; width: 100%; }
    
    /* 2. 좌측 로그인 컨테이너 - 더 깔끔한 엣지 처리 */
    .auth-container {
        width: 460px;
        background: #fff;
        display: flex;
        flex-direction: column;
        justify-content: center;
        padding: 60px 50px;
        box-shadow: 20px 0 50px rgba(0,0,0,0.03);
        z-index: 2;
    }
    
    /* 3. 우측 비주얼 영역 - 선택하신 로고와 어울리는 딥한 컬러와 배경감 */
    .brand-visual {
        flex: 1;
        background: linear-gradient(135deg, #4b4efc 0%, #2b2e83 100%);
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        color: #fff;
        position: relative;
    }
    
    /* 세련된 도트 패턴 추가 */
    .brand-visual::after {
        content: "";
        position: absolute;
        inset: 0;
        background-image: radial-gradient(#ffffff 1px, transparent 1px);
        background-size: 30px 30px;
        opacity: 0.05;
    }

    /* 4. 로고 박스 - 선택하신 이미지 비율 최적화 */
    .logo-box { display: flex; align-items: center; gap: 14px; margin-bottom: 50px; }
    .logo-box img { 
        width: 48px; 
        height: 48px; 
        border-radius: 12px; 
        object-fit: cover;
        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
    }
    .logo-text { font-size: 28px; font-weight: 800; color: #444e5e; letter-spacing: -1px; }

    /* 5. 폼 입력창 - 모던한 보더 및 포커스 효과 */
    .auth-login-form { display: flex; flex-direction: column; gap: 20px; }
    .input-group { display: flex; flex-direction: column; gap: 8px; }
    .input-group label { font-size: 13px; font-weight: 700; color: #566a7f; text-transform: uppercase; letter-spacing: 0.5px; }
    
    input[type="text"], input[type="password"] {
        width: 100%;
        padding: 14px 18px;
        border-radius: 10px;
        border: 1px solid #d9dee3;
        font-size: 15px;
        transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
        background: #fcfcfd;
    }
    input:focus {
        border-color: #696cff;
        background: #fff;
        outline: none;
        box-shadow: 0 0 0 4px rgba(105, 108, 255, 0.15);
        transform: translateY(-1px);
    }

    /* 6. 체크박스 디자인 */
    .remember-wrap { display: flex; align-items: center; gap: 10px; margin-top: 5px; cursor: pointer; width: fit-content; }
    .remember-wrap input { width: 18px; height: 18px; accent-color: #696cff; cursor: pointer; }
    .remember-wrap span { font-size: 14px; color: #697a8d; font-weight: 500; }

    /* 7. 메인 버튼 - 호버 액션 강화 */
    button[type="submit"] {
        margin-top: 15px;
        padding: 16px;
        border: none;
        border-radius: 10px;
        background: #696cff;
        color: #fff;
        font-size: 16px;
        font-weight: 700;
        cursor: pointer;
        transition: 0.3s;
        box-shadow: 0 4px 15px rgba(105, 108, 255, 0.3);
    }
    button[type="submit"]:hover { 
        background: #5f61e6; 
        transform: translateY(-2px);
        box-shadow: 0 6px 20px rgba(105, 108, 255, 0.4);
    }
    button[type="submit"]:active { transform: translateY(0); }

    /* 8. 텍스트 요소들 */
    .text-danger {
        background: #fff2f2;
        color: #ff3e1d;
        padding: 14px;
        border-radius: 10px;
        font-size: 14px;
        border-left: 5px solid #ff3e1d;
        margin-bottom: 15px;
        font-weight: 500;
    }

    .brand-title { font-size: 4.5rem; font-weight: 900; letter-spacing: -3px; z-index: 1; text-shadow: 0 10px 30px rgba(0,0,0,0.2); }
    .brand-sub { font-size: 1.3rem; opacity: 0.9; z-index: 1; font-weight: 300; letter-spacing: 2px; }
</style>
</head>

<body>
    <div class="auth-wrapper">
        <div class="auth-container">
            <div class="logo-box">
                <img src="/img/cafe-logo.jpg" alt="Logo" />
                <span class="logo-text">CAFE <span style="color: #696cff;">CORE</span></span>
            </div>

            <div style="margin-bottom: 30px;">
                <h3 style="color: #566a7f; margin-bottom: 8px;">본사 시스템 로그인</h3>
                <p style="color: #a1acb8; font-size: 14px;">가맹점 통합 관리를 위한 관리자 전용 채널입니다.</p>
            </div>

            <form action="/login" class="auth-login-form" method="post">
                <c:if test="${param.error == 'true'}">
                    <div class="text-danger">
                        <c:choose>
                            <c:when test="${not empty param.message}">
                                ${fn:escapeXml(param.message)}
                            </c:when>
                            <c:otherwise>
                                아이디 또는 비밀번호를 다시 확인해 주세요.
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>

                <div class="input-group">
                    <label for="memberId">아이디</label>
                    <input type="text" name="memberId" id="memberId" placeholder="아이디를 입력하세요" required />
                </div>

                <div class="input-group">
                    <label for="memPassword">비밀번호</label>
                    <input type="password" name="memPassword" id="memPassword" placeholder="············" required />
                </div>

                <label class="remember-wrap" for="savedId">
                    <input type="checkbox" id="savedId" />
                    <span>아이디 저장</span>
                </label>

                <button type="submit">시스템 접속하기</button>
            </form>

            <div style="margin-top: auto; color: #c4cdd5; font-size: 12px; text-align: center;">
                © 2026 CAFE CORE. All Rights Reserved.
            </div>
        </div>

        <div class="brand-visual">
            <h1 class="brand-title">CAFE CORE</h1>
            <p class="brand-sub">Integrated Franchise Control Center</p>
        </div>
    </div>

    <script src="/assets/vendor/libs/jquery/jquery.js"></script>
    <script src="/js/member/login.js"></script>
</body>
</html>