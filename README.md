# 프로젝트 ERD 다이어그램

![ERD 다이어그램](images/Untitled.png)
![API 문서](images/api_docs.png) 

# ⚾ KBO 선수 검색 웹 애플리케이션

KBO 리그의 선수 정보를 검색하고 필터링할 수 있는 웹 애플리케이션입니다.  
선수 이름, 팀, 포지션, 규정 이닝(또는 경기 수) 등의 다양한 조건으로 타자/투수 데이터를 조회할 수 있습니다. 
API 연동 및 정렬 기능도 포함되어 있습니다.
선수의 데이터를 추가, 수정, 삭제할 수 있습니다.

---

## 📸 데모

<img src="https://your-screenshot-url.com/demo.png" width="100%" alt="KBO 선수 검색 데모 이미지" />

---

## 🔧 기술 스택

| 구분 | 기술 |
|------|------|
| 프론트엔드 | HTML5, CSS, JavaScript (Vanilla JS) |
| 백엔드 | Spring Boot 3.4.3, JPA, QueryDSL, Spring Security, JWT |
| DB | MySQL, H2 (개발용) |
| 크롤링 | Selenium (Java) |
| API 문서화 | SpringDoc OpenAPI |
| 배포 | AWS EC2, RDS, Docker |

---

## 🧩 주요 기능

- ✅ 선수 이름 검색
- ✅ 팀별 필터
- ✅ 타자 / 투수 선택
- ✅ 세부 포지션 필터 (예: 중견수, 마무리투수 등)
- ✅ 규정 이닝(투수) 또는 경기 수 필터
- ✅ 선수 데이터 정렬 (이름, 타율, ERA 등)
- ✅ 타자/투수 자동 구분 후 컬럼 동적 생성
- ✅ 선수 데이터 수정, 삭제
- ✅ 선수 추가

---

## 🖥️ 실행 방법

java -jar build/libs/kbo-api.jar

## 📁 프로젝트 구조

Baseball/record/KBO
├── chrome              # Selenium 크롤러 (타자, 투수, 팀 정보 수집)
├── config              # 프로젝트 설정 (JWT, Swagger, Security 등)
├── controller          # REST API 컨트롤러
├── domain              # 도메인 엔티티
├── dto                 # 요청/응답 DTO
├── repository          # JPA 레포지토리
├── service             # 비즈니스 로직 서비스
└── KboApplication.java # 메인 클래스

resources
├── static              # 정적 리소스 (HTML)
└── application.properties # 설정 파일

🔌 API 예시

- 선수 이름으로 검색
  'GET /kbo/find/PlayerName?name=노시환'
  **Response**
  ```json
  {
    "playerType": "BATTER",
    "average": 0.254,
    "hit": 51,
    "homeRun": 11,
    "rbi": 36,
    "ops": 0.799,
    "batterPosition": "THIRD",
    "name": "노시환",
    "birthDate": "2000-12-03",
    "game": 53,
    "teamName": "한화",
    "playerType": "BATTER"
}

- 팀 이름으로 성적 조회
  'GET /team/record'
  **Response**
  ```json
   {
        "teamName": "LG",
        "date": "2025-05-28",
        "teamRank": 1,
        "game": 53,
        "win": 34,
        "lose": 18,
        "draw": 1,
        "winningRate": 0.654,
        "gamesBehind": 0.0
    },
    {
        "teamName": "한화",
        "date": "2025-05-28",
        "teamRank": 2,
        "game": 53,
        "win": 31,
        "lose": 22,
        "draw": 0,
        "winningRate": 0.585,
        "gamesBehind": 3.5
    },...
