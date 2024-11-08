# CrawlProductTrigger

> AWS Lambda로 S3 이벤트를 트리거하여 크롤링된 제품 데이터를 서버에 전송하는 프로젝트

## 프로젝트 개요

CrawlProductTrigger는 S3에 업로드된 크롤링된 상품 데이터를 AWS Lambda에서 처리하고, 필요한 상품 정보를 파싱한 후 REST API를 통해 서버에 전송하는 긴단한 프로젝트입니다.

## 주요 기능
- **S3 이벤트 트리거**: S3에 파일이 업로드되면 Lambda가 자동으로 트리거되어 데이터를 처리합니다.
- **데이터 파싱 및 매핑**: S3에 업로드된 JSON 데이터를 파싱하여 CrawlProductInsertRequestDto 형태로 변환합니다.
- **서버 전송**: 전송할 데이터가 특정 개수 이상 쌓이면 한 번에 서버로 전송합니다.

## 프로젝트 구조

```plaintext
CrawlingTrigger             
├── domain                        
│   ├── build/                    
│   └── core-crawling             
│       ├── build/                
│       └── src
│           └── main
│               ├── java
│               │   └── com.ryuqq.setof.lamda
│               │       ├── CrawlingTriggerHandler          # Lambda 핸들러 클래스
│               │       ├── CrawlProductInsertRequestDto    # 크롤링 상품 DTO 클래스
│               │       ├── CrawlRawData                    # 크롤링 데이터 인터페이스
│               │       ├── JsonUtils                       # JSON 유틸리티 클래스
│               │       ├── KasinaCrawlRawData              # Kasina 사이트의 크롤링 데이터 클래스
│               │       ├── KasinaMapper                    # Kasina 사이트의 Mapper 클래스
│               │       ├── Mapper                          # Mapper 인터페이스
│               │       ├── MapperProvider                  # Mapper 제공 클래스
│               │       ├── MustItCrawlRawData              # MustIt 사이트의 크롤링 데이터 클래스
│               │       ├── MustItMapper                    # MustIt 사이트의 Mapper 클래스
│               │       └── SiteName                        # 사이트 이름 Enum 클래스
│               └── resources                               # 리소스 디렉토리
└── gradle/                    
└── build.gradle               

