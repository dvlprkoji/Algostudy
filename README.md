# 프로젝트 설명


알고리즘 스터디를 운영하며, 체계적인 관리의 필요성을 느껴 프로젝트를 진행했습니다

# 상세


## 1. 로그인

> 이메일 계정과 Google 계정, 카카오 계정으로 로그인할 수 있습니다

<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fd71ff210-1a2b-4bde-8bf1-80acb008bc2c%2FUntitled.png?table=block&id=45bd3354-4114-4097-a0f5-dbdacaf4108e&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&width=2000&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="50%" height="50%"/>

## 2. 스터디 등록

> 로그인 후 미션을 직접 설정하여 스터디를 생성할 수 있습니다

<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F7218095e-aedf-42fb-84ee-6987f28e9f31%2FUntitled.png?table=block&id=5ab787ef-d7d3-480f-9ab0-ee562a9f545e&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&width=2000&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="80%" height="80%"/>

## 3. 팀원 초대/수락

> 초대장을 보내 스터디에 초대할 수 있습니다
 

<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F3186daf7-712c-41f4-a62e-f1276d7a69c6%2FUntitled.gif?table=block&id=8e79879b-8298-4a6d-84c4-357d3bded974&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="80%" height="80%">

☝️ 팀원 검색 및 초대

<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F46e3def3-5e1c-48c3-af5f-af3834992239%2FUntitled.png?table=block&id=60c3f115-c806-4125-ab13-c55491a00740&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&width=2000&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="50%" height="50%"/>
     
☝️ 초대 메시지

<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc6a823e4-8da6-489d-9372-85de67341321%2FUntitled.png?table=block&id=c20cdeb3-6e29-4d8d-86c6-8c5701512b27&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&width=2000&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="50%" height="50%"/>

☝️ 초대 거절 및 수락 페이지

## 4. 대시보드

> 소속된 팀과 미션 달성률, 각오 및 디데이를 확인합니다


<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fa39b8ff9-4d98-4a71-b42c-6a560d02264a%2FUntitled.png?table=block&id=a111c66c-4854-4cbb-879e-c21eb510d5f3&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&width=2000&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="80%" height="80%"/>

☝️ 대시보드

## 5. 미션 체크 & 등수 확인

> 팀원들의 미션 수행 여부를 확인하고 나의 전체 등수를 확인합니다

<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F943270d4-4ce5-49e7-827e-893aeb0d1a99%2FAlgostudy.png?table=block&id=66c9c381-8efc-4b26-810a-342bdc3c137c&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&width=2000&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="80%" height="80%"/>


# Project Structure


> 사용한 기술 스택은 다음과 같습니다
> 
- Spring Boot (API Server)
- Spring Security (Security)
- Spring Batch (Batch)
- PostgreSQL (RDB)
- Spring Data JPA & QueryDSL (ORM)
- OAuth2.0 (Login)
- AWS EC2, S3 (Public Cloud)
- Nginx (Reverse Proxy Server)
- Javascript, Thymeleaf (Client)
- Redis (Cache)
- Apache Kafka (Message Broker) - 미적용
- Jenkins (CI/CD) - 미적용

> ERD & API는 다음과 같이 정리했습니다
> 
- ERDCloud : [https://www.erdcloud.com/d/ZmdWhdSJ3Lm8rC2Jw](https://www.erdcloud.com/d/ZmdWhdSJ3Lm8rC2Jw)
- API :
    
    

# Spring Boot


> Spring MVC 패턴으로 작성하였습니다

프로젝트 구조는 다음과 같습니다

```jsx
../com.example.algostudy/
├── batch                       - Spring Batch
│   ├── chunk                   - 청크 기반 CustomItemProcessor
│   ├── job                     - Job
│   ├── listener                - Listener
│   └── tasklet                 - Tasklet
├── common                      
│   └── CommonTools             - 공통 기능 메소드 정의
├── config                      
│   ├── AmazonS3Config          - Amazon S3 설정파일
│   ├── BatchConfig             - Spring Batch 설정파일
│   └── SecurityConfig          - Spring Security 설정파일
├── security         
│   ├── authentication          - 인증(Authentication) 설정파일
│   └── authorization           - 인가(Authorization) 설정파일
├── domain         
│   ├── entity                  - JPA Entity
│   └── dto                     - Data Transfer Object
├── controller                      
│   └── ...                     - Controller
├── listener                    - ApplicationListener 메소드 정의
├── mapper                      
│   └── ...                     - MapStruct Mapper 메소드 정의
├── repository                    
│   └── ...                     - Repository
└── service                    
    └── ...                     - Service
```

# Spring Security (Security)


> Security 설정을 통해 인가된 사용자만 허용 URL에 접근할 수 있도록 제한한다.
> 

다음과 같이 설정하였습니다

- Form Login (Auth)
- Remember Me (Auth)
- Session : maximumSessions(1)
- Session : SessionCreationPolicy.If_Required
- Authorization : URL-based (UrlResourcesMapFactoryBean)
<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fba996bb0-51f2-4379-86b7-853ffbacf87c%2Fsecurity.drawio.png?table=block&id=17882b4e-6e38-4b60-a189-e2ffe0948895&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&width=2000&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="80%" height="80%"/>


# Spring Batch (Batch)


> 매일 0시에 일별 미션을 체크하여 미션 캘린더에 저장하고 종료일자가 지난 미션을 삭제한다
> 

구조는 다음과 같습니다

<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F79352eac-e4d9-4e0a-84f2-f9bc374826a4%2Fbatch.drawio.png?table=block&id=d2fb054f-8c7c-4921-8237-8fe60bceaea9&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&width=2000&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="80%" height="80%"/>


# Spring Data JPA & QueryDSL (ORM)


> Spring Data JPA, QueryDSL을 통해 각각 객체 중심의 domain 설계, JPQL build를 하였습니다.
 
<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb9aeaabe-d068-4072-b081-38c7d896f8ad%2FUntitled.png?table=block&id=100b1bb1-d02c-411f-8db0-5726af7739f0&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&width=2000&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="80%" height="80%"/>


# OAuth 2.0 (Login)


> 이메일 로그인과 소셜 로그인을 제공하여 번거로운 회원가입 과정을 생략할 수 있습니다


구조는 다음과 같습니다

<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F72491da2-39b3-45ba-b990-c4613538ba44%2FUntitled.png?table=block&id=1f460fb6-56f0-4370-8c10-d95e2b442f98&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&width=2000&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="80%" height="80%"/>


# Redis (Cache)


> Sorted Set 자료구조를 사용하여 랭킹 보드를 구현하였습니다


구조는 다음과 같습니다

<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fae4e7834-8212-48fd-8bc6-9e53b0237e5b%2FUntitled.png?table=block&id=240bd9bc-3a35-4e44-935a-50a2a2111833&spaceId=c79bc389-a3bc-46be-b5a0-80287da2c343&width=2000&userId=c3dc26e3-1fe2-469d-80b9-a180fa347728&cache=v2" width="80%" height="80%"/>


# Github


[https://github.com/dvlprkoji/Algostudy](https://github.com/dvlprkoji/Algostudy)
