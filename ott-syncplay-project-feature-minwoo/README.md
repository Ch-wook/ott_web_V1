#  OTT SyncPlay Project
**여러 OTT 플랫폼의 시청 기록을 한곳에서 통합 관리하는 스마트 대시보드 시스템**

##  프로젝트 소개
**OTT SyncPlay**는 사용자가 이용 중인 다양한 OTT(Netflix, TVING, Disney+, Wavve 등)의 시청 정보를 크롬 확장 프로그램을 통해 실시간으로 추출하고, 이를 전용 리액트 대시보드에서 통합 관리할 수 있는 서비스입니다.

단순히 기록을 나열하는 것을 넘어, **TMDB API를 활용한 실시간 포스터 연동**과 **로컬 로고 자산 관리 시스템**을 통해 사용자에게 상용 서비스 수준의 UI/UX를 제공하는 것을 목표로 합니다.

##  주요 기능

### 1. 시청 기록 통합 대시보드 (React)
* **최근 시청 기록**: 실시간으로 업데이트되는 시청 콘텐츠의 제목, 에피소드, 진행률 확인.
* **포스터 자동 매핑**: 콘텐츠 제목을 기반으로 **TMDB API**와 연동하여 고화질 포스터 출력.
* **OTT 가시성 개선**: 각 플랫폼별 공식 로고를 로컬(public) 자산으로 관리하여 가독성 및 디자인 통일성 확보.
* **찜 목록(Wishlist)**: 보고 싶은 콘텐츠를 별도로 저장하고 관리하는 기능.

### 2. 크롬 확장 프로그램 (Extension)
* **실시간 데이터 추출**: 각 OTT 재생 페이지(넷플릭스, 티빙 등)에서 제목, 진행률, URL 자동 감지.
* **백엔드 자동 전송**: 추출된 시청 데이터를 백엔드 API 서버로 실시간 전송 및 동기화.

### 3. 백엔드 서버 (Spring Boot)
* **RESTful API**: 시청 히스토리, 구독 정보, 찜 목록 관리를 위한 엔드포인트 제공.
* **동적 데이터 처리**: 확장 프로그램으로부터 오는 실시간 페이로드를 처리하고 저장하는 로직 구현.

## 🛠 기술 스택
* **Frontend**: React, Tailwind CSS, Lucide React (Icons), Vite
* **Backend**: Java Spring Boot, Gradle
* **Extension**: JavaScript, Chrome Extension API
* **External API**: TMDB API (The Movie Database)

## 📂 프로젝트 구조 (Project Structure)
... (생략) ...

---

## 🚀 최적화 및 보안 업데이트 내역 (v1.2)

최근 업데이트를 통해 성능을 획기적으로 개선하고 보안을 강화했습니다.

### 1. 성능 최적화 (Performance Optimization)

#### **검색 및 OTT 정보 로딩 개선**
*   **전역 캐시 시스템 도입**: `Map` 객체를 활용한 `providerCache`를 구현하여 중복된 API 호출을 방지하고 재검색 시 즉각적인 정보를 제공합니다.
*   **지연 로딩 (Lazy Loading)**: 검색 결과 및 보관함 리스트에서 OTT 정보를 화면에 보일 때 비동기로 로드하여 초기 렌더링 속도를 약 3~5배 향상시켰습니다.
*   **디바운싱 (Debouncing)**: 검색 입력창에 500ms 지연 로직을 적용하여 불필요한 네트워크 트래픽을 최소화했습니다.

#### **이미지 및 브라우저 최적화**
*   **이미지 해상도 최적화**: TMDB 포스터 해상도를 `w500`에서 `w342`로 조정하여 로딩 속도와 데이터 사용량을 최적화했습니다.
*   **이미지 지연 로딩**: `loading="lazy"` 속성을 적용하여 브라우저의 렌더링 성능을 개선했습니다.

### 2. 보안 및 안정성 (Security & Stability)

#### **경로 보안 강화 (ProtectedRoute)**
*   로그인 상태를 감지하여 비로그인 사용자가 `/home`, `/search` 등 주요 페이지에 직접 접근하는 것을 차단하는 보호 라우팅을 구현했습니다.

#### **데이터 정합성 개선 (N+1 문제 해결)**
*   보관함 페이지에서 시청 기록 로드 시 발생하던 N+1 API 호출 문제를 로직 개선 및 지연 로딩 도입으로 해결했습니다.

### 3. 주요 기술 구현 코드 (Code Snippets)

#### **OTT 정보 캐싱 로직**
```javascript
const providerCache = new Map();
// ... 캐시 확인 후 없을 때만 fetch 실행
const cached = providerCache.get(cacheKey);
if (cached) { setLocalProviders(cached); return; }
```

#### **보안 라우팅 (ProtectedRoute)**
```javascript
const ProtectedRoute = ({ children }) => {
  const user = localStorage.getItem('user');
  if (!user) return <Navigate to="/login" replace />;
  return children;
};
```