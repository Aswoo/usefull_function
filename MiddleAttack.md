## 📌 안드로이드 개발에서 MITM 공격과 보안 대책 정리

🔹 MITM 공격이란?
MITM(Man-in-the-Middle) 공격은 클라이언트(앱)와 서버 간 통신을 가로채고 조작하는 공격 방식.

✅ 공격자가 데이터를 훔치거나 변조할 수 있음.
✅ OWASP Mobile Top 10에서 M3(취약한 통신), M9(리버스 엔지니어링)과 관련됨.

### 🔹 MITM 공격 과정

- 인터셉트(Interception) → ARP 스푸핑, 가짜 Wi-Fi, DNS 변조 등으로 통신 가로채기
- 복호화(Decryption) → 암호화되지 않은 데이터를 해독
- 변조(Manipulation) → 요청·응답 조작, 악성 데이터 삽입
- 재암호화 및 전달(Re-encryption & Forwarding) → 정상적인 흐름처럼 보이게 처리
  🔹 MITM 공격으로 발생할 수 있는 피해
  📌 데이터 탈취 → 계정 정보, 결제 정보 유출
  📌 세션 하이재킹 → 세션 토큰 탈취 후 사용자로 가장
  📌 피싱 및 악성코드 삽입 → 가짜 로그인 페이지, 악성코드 포함된 응답 삽입

### 🚀 안드로이드 앱에서 MITM 공격 방어 방법 (OWASP 기준)

✅ 1. HTTPS 및 TLS 강제 적용
📌 앱과 서버 간 모든 통신은 반드시 HTTPS 사용!
📌 TLS 1.2 이상(1.3 권장), 신뢰할 수 있는 인증 기관(CA) 인증서 사용
📌 HSTS(HTTP Strict Transport Security) 적용해 HTTP 다운그레이드 공격 방지

📌 안드로이드 적용 방법
AndroidManifest.xml에서 HTTP 차단 설정

```xml
<network-security-config>
    <domain-config cleartextTrafficPermitted="false">
        <domain includeSubdomains="true">yourdomain.com</domain>
    </domain-config>
</network-security-config>
```


✅ 2. 인증서 고정(Certificate Pinning) 적용
📌 앱이 특정 인증서만 신뢰하도록 설정 → 가짜 인증서 차단

📌 적용 방법 (OkHttp 사용)

```kotlin
val client = OkHttpClient.Builder()
    .certificatePinner(
        CertificatePinner.Builder()
            .add("yourdomain.com", "sha256/your-certificate-hash")
            .build()
    )
    .build()
```

✅ 3. 네트워크 보안 구성 강화
📌 Network Security Configuration 활용하여 TLS 강제 적용, 인증서 고정 설정

📌 안드로이드 적용 방법
res/xml/network_security_config.xml 생성

```xml
<network-security-config>
    <domain-config cleartextTrafficPermitted="false">
        <domain includeSubdomains="true">yourdomain.com</domain>
        <pin-set>
            <pin digest="SHA-256">your-certificate-hash</pin>
        </pin-set>
    </domain-config>
</network-security-config>
```

✅ 4. SSL/TLS 인증서 검증 강화

- 공격자가 가짜 인증서 사용 가능 → 신뢰할 수 있는 기관과 비교 필수
- 개발  중에도 SSL 검증 비활성화 ❌

✅ 5. 취약한 암호화 및 프로토콜 사용 금지

- 위험한 알고리즘 → MD5, RC4 사용 금지
- 안전한 암호화 → AES-GCM, ChaCha20-Poly1305 권장
- 취약한 프로토콜 차단 → TLS 1.0, SSL 3.0 비활성화
- 안드로이드 적용 방법

```kotlin
val sslSocketFactory = SSLContext.getInstance("TLSv1.3")
    .apply { init(null, null, null) }.socketFactory

val client = OkHttpClient.Builder()
    .sslSocketFactory(sslSocketFactory, 
        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        .trustManagers[0] as X509TrustManager
    )
    .build()
```

🔥 결론: MITM 공격 방어 핵심 정리
✔ HTTPS & TLS 적용
✔ 인증서 고정 (Certificate Pinning)
✔ 네트워크 보안 구성 강화
✔ SSL/TLS 인증서 철저히 검증
✔ 약한 암호화·프로토콜 사용 금지