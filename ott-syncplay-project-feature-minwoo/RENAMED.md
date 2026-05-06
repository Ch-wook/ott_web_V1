# RENAMED: 프로젝트 최적화 및 DB 연동 업데이트 내역 (v1.3)

## 1. 데이터베이스(MySQL) 연동 및 서버 아키텍처 개선
- **기존 메모리 저장소 방식에서 JPA/MySQL 연동 방식으로 전환**
- **Docker 기반 MySQL 컨테이너 설정 추가**: 포트 `3308`을 통해 로컬 DB와 연결
- **신규 엔티티 및 API 추가**:
    - `User.java`, `UserController.java`: 사용자 정보 관리 및 회원가입/로그인 기능
    - `WatchHistory.java`, `WatchHistoryController.java`: 시청 기록 DB 영속성 관리
- **설정 파일 업데이트**: `application.properties`에 MySQL 접속 정보 및 TMDB API 설정 반영

## 2. 프론트엔드 성능 최적화 (v1.2 포함)
- **N+1 문제 해결**: 보관함 페이지 로딩 시 OTT 정보를 지연 로딩으로 처리하여 API 호출 병목 해결
- **이미지 최적화**: TMDB 포스터 해상도 조정(w500 -> w342) 및 브라우저 `loading="lazy"` 속성 적용
- **캐싱 및 디바운싱**: `providerCache`를 통한 데이터 재사용 및 검색창 입력 최적화

## 3. 보안 강화
- **ProtectedRoute 적용**: 로그인하지 않은 사용자의 특정 페이지 접근 제한 및 로그인 리다이렉트

## 4. 프로젝트 구조 변경
- 메인 폴더명 변경: `ott-syncplay-project-feature-minwoo` -> `ott-syncplay-project`
- GitHub 서브모듈(화살표 링크) 문제 해결 및 일반 폴더 전환
