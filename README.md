# Capybara
병원 예약 어플리케이션<br><br>
AndroidStudio, SpringBoot, MySQL, Python(크롤링)<br>
Notion, AWS, Postman, Figma

## UI
<img src="https://github.com/hhJ830/Capybara/assets/99874673/ee54c0a7-5404-4454-9681-f976f4342675" width = 15% height = 15%>
<img src="https://github.com/hhJ830/Capybara/assets/99874673/2edf66bb-c0ed-48b4-b107-718005714f3c" width = 15% height = 15%>
<img src="https://github.com/hhJ830/Capybara/assets/99874673/6184e398-5bed-4fb4-aa2d-d5d4d65c563b" width =20% height=20%>
<img src="https://github.com/hhJ830/Capybara/assets/99874673/8700ecf7-b3e3-4cdb-a408-bd54fcfae5c2" width=20% height=20%>
<img src="https://github.com/hhJ830/Capybara/assets/99874673/6fc302da-6b42-4da5-b57f-a5dfbeaabb7f" width = 20% height = 20%>
<br>
<img src="https://github.com/hhJ830/Capybara/assets/99874673/2c87db94-7902-4a30-987f-4e3d4b15798b" width=18% height=18%">
<img src="https://github.com/hhJ830/Capybara/assets/99874673/8764ffed-2bb7-45b8-bbca-5e17d42e766d" width=18% height=18%>
<img src="https://github.com/hhJ830/Capybara/assets/99874673/76b80be0-c70e-4fe6-b6a4-6ec11308f912" width=18% height=18%>
<img src="https://github.com/hhJ830/Capybara/assets/99874673/d7b97ded-f7a5-4abd-9d76-a046675d489d" width=18% height=18%>
<img src="https://github.com/hhJ830/Capybara/assets/99874673/6ff321d1-61e5-435d-8875-1684aec4bb14" width=18% height=18%>

---
# DB

## 요구사항 명세서
![image](https://github.com/hhJ830/Capybara/assets/99874673/310b1903-0f88-43ca-9233-8149595d16b2)
![image](https://github.com/hhJ830/Capybara/assets/99874673/1e4aa382-8aa0-4cdf-a46d-37edc86f2dff)
<br>

## ERD
<img src="https://github.com/hhJ830/Capybara/assets/99874673/e8c69e21-09de-447b-aaed-3b3de9097be9" width=80% height=80%>
<br>

## 릴레이션 스키마
<img src="https://github.com/hhJ830/Capybara/assets/99874673/7ce2ec85-0bb2-4e96-996c-727d49a4090d" width=80% height=80%>
<br>

## MySQL
<img src="https://github.com/hhJ830/Capybara/assets/99874673/5e5ad1a0-80ef-47c9-8b22-0b4988471cf6" width=80% height=80%>

---

### 개발하면서 신경 쓴 기능

Token을 사용한 보안 기능(접근 권한 부여, 이중 로그인 방지 등)<br>
비관적 락을 사용한 동시성 제어(효율적인 락은 아닌 것 같음)<br>
Criteria를 사용한 동적 쿼리 검색<br><br>

---

### API 개발
1. 회원가입
2. 로그인
3. 회원정보수정
4. 회원정보조회
5. 중복아이디검사
6. 로그아웃

<br>

7. 진료과목으로 검색
8. 검색어로 검색
9. 병원상세정보
10. 좋아요 등록
11. 좋아요 삭제
12. 좋아요 top 3 조회

<br>

13. 진료기록조회
14. 진료기록검색
15. 진료기록상세

<br>

16. 예약가능시간조회
17. 예약하기
18. 예약정보조회
19. 예약정보검색
20. 예약정보삭제
