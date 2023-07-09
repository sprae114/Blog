# Blog
# 1. 프로젝트 설명

**📌프로젝트 진행 배경**
CRUD, Restful, JPA 등 그동안 배웠던 지식들을 활용하여 이용하는 댓글을 쓸수 있는 게시판 프로젝트를 만들고자 하였습니다.

**📌프로젝트 진행기간  :**  22.07.30 ~ 08.30 (4주)

**📌개발인원 : 1명 (개인프로젝트)**

**📌기술스택**


![기술스택](https://user-images.githubusercontent.com/52237184/184475536-fedf763f-9c4f-4cc1-b05b-7f9636370aae.JPG)



# 2. 프로젝트 설계

## 1)환경 세팅하기

**h2**

![h2](https://user-images.githubusercontent.com/52237184/184475482-1179c431-7206-4054-981a-1689aab04b02.jpg)

**접속**           [`http://localhost:8086`](http://localhost:8086/)


## 2)ERD

![image](https://user-images.githubusercontent.com/52237184/184476394-7bc6a35e-300d-45e1-81c9-e736b662f9ba.png)

## 3) 서버 구조

![image](https://github.com/sprae114/Blog/assets/52237184/28622415-4454-42da-a761-8f7206f1cf6c)


## 4)스토리 보드


![스토리보드](https://user-images.githubusercontent.com/52237184/184475627-9049d497-7ed2-4417-b9b4-7179cbee06c9.jpg)

## 5)기술명세서


![기술명세서](https://user-images.githubusercontent.com/52237184/184475507-5f53ad3e-03f7-4796-a2c5-f9b4fffe19b6.png)

## 6)테스트코드 작성

**Controller**

- BoardControllerTest
    - 글 등록 테스트- 입력값 정상
    - 글 등록 테스트- 제목 검증 실패
    - 글 수정 테스트 - 성공
    - 글 수정 테스트 - 실패 : 글 존재 X
    - 글 삭제 테스트 - 성공
    - 글 삭제 테스트 - 실패 : 글 존재 X

- LoginControllerTest
    - 회원 가입 처리 - 성공
    - 회원 가입 처리 - 실패 : 기존 아이디가 있는 경우
    - 로그인 처리 - 성공
    - 로그인 처리 - 실패 : 비번이 일치하지 않는 경우
    - 로그인 처리 - 실패 : 아이디가 없는경우
    - 로그아웃
    
- IndexControllerTest
    - 로그인 페이지 조회
    - 글 메인 페이지 조회
    - 글 등록 페이지 조회
    - 회원가입 페이지 조회
    - 글 상세 페이지 조회
    - 글 수정 페이지 조회
    


**Service**

- LoginServiceTest
    - 회원가입 - 정상
    - 로그인 - 정상
    - 로그인 - 아이디 오류
    - 로그인 - 비번 오류
    
- BoardServiceTest
    - 글 저장 - 성공
    - 글 저장 - 실패 : 제목 글자수 미만
    - 글 수정 - 성공
    - 글 수정 - 이름 없는 경우
    - Board 전체 찾기
    - Board 찾기 - 존재하지 O
    - Board 찾기 - 존재하지 X
    - 글 삭제 - 성공
    - 글 삭제 - 이름 없는 경우
    
- ReplyServiceTest
    - 댓글 삭제 - 정상
    - 댓글 삭제 - 실패 : 해당 댓글 없을때


**검증**

- MemberValidationTest
    - 회원가입 - 중복된 아이디
    - 회원가입 - 아이디 검증 : 틀린 문자
    - 회원가입 - 아이디 검증 : 길이
    - 회원가입 - 이름 검증 : 틀린 문자
    - 회원가입 - 이름 검증 : 길이
    - 회원가입 - 비번 검증 : 길이
- BoardValidationTest
    - 글 등록 내용 검증
    - 글 등록 제목 검증
    - 글 수정 내용 검증
    - 글 수정 제목 검증
    

# 3.주요기능

## 1)회원가입

- 아이디는 타 사용자의 아이디와 중복될 수 없습니다.
- 아이디는 영어와 숫자로 이뤄진 문자로만, 이름은 한글로만 사용할 수 있습니다.


![회원가입](https://user-images.githubusercontent.com/52237184/184475662-0a3b2823-c9bf-4cb3-bc9e-380dd94079cd.gif)
    

## 2)로그인

- session을 통한 로그인 기능을 구현하였습니다.
- 회원 정보일치여부를 확인합니다.
    
 
 ![로그인](https://user-images.githubusercontent.com/52237184/184475658-e3762f51-0d13-4661-bc88-82e4449ea50f.gif)
    

## 3)검색

- 원하는 게시물의 제목을 찾을 수 있습니다.
- 다음페이지를 넘겨도 검색 결과가 유지됩니다.
    
 
 ![검색](https://user-images.githubusercontent.com/52237184/184475651-68eeff12-0c0d-44b3-9793-f3101496b4f0.gif)   

## 4) 페이징

- 페이징 처리를 통해 5개 이상이 넘어는 게시물은 다음 페이지에 볼수 있도록 하였습니다.
- 현재 페이지는 클릭할 수 없습니다.
    

![페이징](https://user-images.githubusercontent.com/52237184/184475659-e75e775f-b454-42cd-8613-5156e8627c36.gif)
    

## 5) 게시글 CRUD

- 로그인 사용자만 게시글을 사용할 수 있습니다.
- 게시물 검증을 통해 게시물을 등록하지 못하게 했습니다.
- 게시물을 CRUD할 수 있습니다.
    

![게시글 등록 검증](https://user-images.githubusercontent.com/52237184/184475653-296f203c-f6c4-460f-975d-22440caa7422.gif)



![게시글 수정 및 삭제](https://user-images.githubusercontent.com/52237184/184475655-a0304d42-b71a-4872-ada1-39333ef8c15a.gif)  
    
    
    

## 6) 댓글 등록 및 삭제

- 댓글의 등록, 삭제 기능을 추가하였습니다.
- 게시물이 삭제되면 관련된 댓글도 사라집니다.
    

![댓글](https://user-images.githubusercontent.com/52237184/184475656-b5426fb8-35ac-48cd-b01c-1a55a514ac28.gif)    

## 7) 미로그인 상태

- 미 로그인 상태에서는 글 등록/수정/삭제를 할수 없습니다


![게시글 로그인 상태만 등록 수정 및 삭제 댓글 가능](https://user-images.githubusercontent.com/52237184/184475654-c4dc7734-d80a-4c02-a882-4ba9c488c372.gif)
