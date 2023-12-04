# Zipster
> 집스터 - AWS S3 등 Object Storage의 파일을 쉽게 다운로드할 수 있도록 지원하자
- S3 콘솔을 활용할 경우, 파일을 여러 건 다운받기가 굉장히 귀찮다. 귀찮으면 안귀찮게 만들어보자.

#### 목표
- [ ] `WebFlux` 와 `Coroutine`을 활용한 애플리케이션 구현해보기
- [ ] `LocalStack` 을 활용한 통합 테스트 환경 구축하기
- [ ] 단일 파일에 대한 다운로드 기능 구현하기
- [ ] 다수의 파일에 대한 zip 다운로드 기능 구현하기

#### 기간
넉넉히 `2023-12-01 ~ 2023-01-31`로 잡음

#### 기술 스택
- Kotlin + Spring Boot + WebFlux
- LocalStack(Docker)
- 가능한 경우, opeAPIGenerator(open api spec 3.0)

---
### 정책
#### 공통
- 애플리케이션은 `AWS S3`를 기반으로 동작하지만, 추후 `GCP GCS` 등 다른 CSP도 지원할 수 있어야 한다.
- 다운로드 시 활용할 클라이언트에 적용될 자격 증명(=credential)은 동적으로 결정될 수 있어야 한다.
- 어떤 사용자가 언제 어떤 파일을 다운로드 받았는지 로그 / 히스토리 등을 활용하여 추적할 수 있어야 한다.
- 다운로드 완료시 완료 알림을 줄 수 있어야 한다.

#### 단건 파일 다운로드
- `AWS` 이외의 CSP가 제공하는 오브젝트 스토리지에도 대응 가능해야 한다.

#### 다수 파일 zipping 및 다운로드
- 여러 CSP 또는 오브젝트 스토리지(=bucket)이 혼합된 요청도 처 가능해야 한다.
- 처리 과정 중 대상에 대한 필터링이 가능해야 한다.
    - 예시: 확장자가 `mp4`인 파일만 / 용량이 `100MB` 이하인 파일만 / 특정한 패턴의 이름만, 등...
- 처리 과정 중 예외 상황 발생시 이에 대한 처리 방식을 결정할 수 있어야 한다.
    - 예시: 예외 발생시 다운로드 중단 / 무시 / 재시도, 등...

### 추가 고려사항
- [ ] 7zip 등의 분할 압축?
- [ ] 다운로드 재시도?
- [ ] 요청에 대한 용량 제한 / 파일 개수 제한?
- [ ] 애플리케이션이 강제로 종료된 경우, 다운로드 작업에 대한 처리?

### API
#### 단건 파일에 대한 다운로드
후보들! 아래 URI들은 너무 길어지기 쉬운데, 더 좋은 방법은 없을까?
- `[GET] /service/{serviceName}/storage/{bucketName}/files/{fileName}`
- `[GET] /files?bucket=bucket-name&key=file.mp4`

#### 다수 파일에 대한 zipping 후 다운로드
- `[POST] /files/zip`