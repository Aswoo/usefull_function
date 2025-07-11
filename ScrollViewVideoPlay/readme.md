# DroidKnights 2025 – 다양한 스크롤 뷰에서의 영상 재생 🎥

### 발표자
- **이가은**, 라텔앤드파트너즈
- 발표일: 2025년 6월 16일

---

## 🎯 발표 목적
- 스크롤 기반 UI에서 발생하는 영상 재생 문제(잔상, 버벅임, OOM, 백그라운드 재생 등)를 해결하는 최적화 전략 소개

---

## 📋 발표 목차
1. 렌더링 방법 선택
2. 디자인 패턴 분석
3. 스크롤 상황에 따른 재생/중단 로직

---

## 1. 렌더링 방법 선택하기

Android의 렌더링 프로세스는 다음과 같은 단계를 거칩니다:

```
Measure → Layout → Draw → Buffer → Display
```

### ✅ SurfaceView vs TextureView

| 항목 | SurfaceView | TextureView |
|------|-------------|-------------|
| 위치 | 뷰 계층 외부 | 뷰 계층 내부 |
| 성능 | 더 빠름 | 약간 느림 |
| 장점 | 오버레이, 레이턴시 적음 | 회전/투명도 처리 등 후처리 가능 |
| 용도 | 영상 재생에 최적 | 영상 필터, 회전 등 편집이 필요한 경우 |

---

## 2. 디자인 패턴 적용

### 🎯 목적: 메모리 절약, 확장성 확보, 유지 보수성 향상

#### 🔸 싱글톤 패턴 (Singleton Pattern)
- ExoPlayer, Cache, File 접근 등을 앱 전역에서 단일 인스턴스로 관리

#### 🔸 객체 풀 패턴 (Object Pool Pattern)
- ExoPlayer 인스턴스를 재사용해 객체 생성/소멸 오버헤드 감소

#### 🔸 프록시 패턴 (Proxy Pattern)
- 캐시에 영상이 있는지 확인하고, 없을 경우에만 네트워크 요청 수행

#### 🔸 캐시 어사이드 패턴 (Cache-Aside Pattern)
- 로컬 파일 확인 → 없으면 네트워크 → 다운로드 후 저장

#### 🔸 상태 패턴 (State Pattern)
- ExoPlayer 상태(Idle, Ready, Playing, Error 등)를 객체화하여 관리

#### 🔸 데코레이터 패턴 (Decorator Pattern)
- 기본 Player 기능에 자막/반복/음소거 등 기능을 동적으로 추가

---

## 3. 스크롤 상황에 따른 재생/중단 제어 로직

### ✅ LazyColumn
- `LazyListState`를 사용하여 visibleItems를 추적
- 보이는 영상 수에 따라 기준 달리함:
    - 1개: 첫 번째 아이템
    - 2개: 화면의 70% 이상 차지하는 아이템
    - 3개 이상: 화면 중심 아이템

### ✅ RecyclerView
- `findFirstVisibleItemPosition()`, `findLastVisibleItemPosition()` 활용
- 현재 화면 내에 있는 아이템만 재생 대상

### ✅ HorizontalPager
- `currentPage`만 재생하고, 나머지는 중단

### ✅ Composable 내부 스크롤
- `onGloballyPositioned` + `LayoutCoordinates` 이용
- 화면에 보이는지 여부로 재생 제어 판단

---

## ✨ 결론 및 제안

- 최적 조합은 다음과 같음:

```
[적절한 렌더링 방식] + [적절한 디자인 패턴] + [스크롤 상태에 따른 제어 로직]
```

- 단 하나의 정답은 없으며, 앱의 UI 구조, 메모리 상황, 네트워크 제약에 따라 구현 방식은 달라질 수 있음

---

## 📚 참고 자료

- Google I/O 2019 — *Drawn out: How Android renders*
- Android Developers
    - [Surface and SurfaceHolder](https://developer.android.com/reference/android/view/SurfaceHolder)
    - [Reduce graphic memory consumption](https://developer.android.com/topic/performance/graphics/reduce-overdraw)
- [Refactoring Guru - 디자인 패턴 설명](https://refactoring.guru/ko/design-patterns)

---

## ✅ 적용한 디자인 패턴 체크리스트

- [x] 싱글톤 패턴 (Singleton Pattern)
- [x] 객체 풀 패턴 (Object Pool Pattern)
- [x]  상태 패턴 (State Pattern)
- [x]  데코레이터 패턴 (Decorator Pattern)
- [ ] 캐시 어사이드 패턴 (Cache-Aside Pattern)
- [ ] 프록시 패턴(Proxy Pattern)
