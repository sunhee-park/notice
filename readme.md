# notice (공지사항 관리)
## 개발환경
- Java:  v15.0.1 
- gradle v7.3.3
- Database: H2 database

## 개발 framework
- spring-boot-starter-web
- sprng-boot-starter-data-jpa

## Rest API
- 등록: POST /api/v1/posts
- 수정: PUT /api/v1/posts/{id}
- 삭제: DELETE /api/v1/posts/{id}
- 조회: GET /api/v1/posts/{id}

## 개발전략
###  엔티티 설계 관련
- Posts(공지시항 게시물) Entity와 Files(첨부파일) Entity를 1:N 관계 설정(oneTomany)
- Files 테이블의 posts_id 칼럼은 Posts 테이블의 id 칼럼울 foreign key로 가짐
- 게시물이 삭제될 때 첨부파일도 함께 삭제

###  파일 업로드 API 설계 관련
- 게시물 등록/수정 시에 Content-Type을 multipart/form-data 로 첨부파일 및 게시물을 전송
- 게시물 수정 시에 form parameter(attaches)로 기존 첨부파일의 id를 전송받아 첨부파일을 등록/삭제 처리
- 게시물 조회 시에 기존 첨부파일의 id 와 fileName을 전송

### 첨부 파일 저장 관련
- 첨부파일의 경로(filePath)와 이름(origFileName)은 테이블(FILES)에 저장하고 실제 파일은 로컬 디렉토리에 저장
- 동일한 이름의 첨부파일 등록을 위해 uuid로 파일이름을 생성하여 저장
- 파일 저장 디렉토리를 application.properties 파일의 storage.location 필드에서 설정 가능

### 조회수 증가 관련
- 게시물 조회 시에 PostsService 의 int increaseViewCount(Long id)를 호출하여 조회수를 증가