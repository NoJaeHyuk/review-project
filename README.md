# 🗂 Review Application

## 🌟 프로젝트 소개

리뷰 작성 및 조회를 제공하는 간단한 애플리케이션입니다. 사용자는 특정 제품에 대해 리뷰를 작성하고, 기존 리뷰를 조회할 수 있습니다. 대규모 동시성을 고려하여 데이터 무결성과 성능을 유지하는 설계에 초점을 맞췄습니다.

---

## 💻 기술스펙

- JDK 17
- Spring Boot 3.x
- Spring Boot Data JPA
- MySQL
- Gradle
- Git

---


## 🗂 요구사항

### 2.1 기능 요구사항
- **리뷰 작성**: 사용자가 제품에 대해 리뷰를 작성합니다.
  - 작성 시 평점(1~5)과 내용을 포함합니다.
  - 하나의 상품에 하나의 리뷰만 작성 가능합니다.
- **리뷰 조회**: 특정 제품에 대한 리뷰 목록을 조회합니다.
  - 최신 리뷰부터 정렬됩니다.
- **제품 정보 업데이트**:
  - 리뷰 작성 시 `reviewCount`와 `score`가 자동 갱신됩니다.

### 2.2 비기능 요구사항
- 리뷰에 대한 동시성이슈가 있을 시 `reviewCount`와 `score`에 대한 정합성이 맞는지 확인합니다.
- 회원가입 및 로그인은 고려하지 않으며, `userId`를 DTO로 받는것으로 처리합니다.
- 테스트 코드를 작성합니다.

---

## 용어 사전

| 용어          | 정의                                  |
|---------------|---------------------------------------|
| 리뷰 (Review) | 사용자가 제품에 남기는 피드백         |
| 평점 (Score)  | 1~5 사이의 숫자로 표현되는 만족도 점수 |
| 리뷰 수 (reviewCount) | 특정 제품에 남겨진 리뷰의 총 개수     |

---

## 🌐 모델링

### 주요 엔티티

#### Product
| 필드         | 타입         | 설명                     |
|--------------|--------------|--------------------------|
| id           | BIGINT       | 제품의 고유 식별자       |
| reviewCount  | BIGINT       | 해당 제품의 리뷰 총 개수 |
| score        | FLOAT        | 리뷰의 평균 점수         |

#### Review
| 필드         | 타입         | 설명                     |
|--------------|--------------|--------------------------|
| id           | BIGINT       | 리뷰의 고유 식별자       |
| productId    | BIGINT       | 리뷰가 속한 제품의 ID    |
| userId       | BIGINT       | 리뷰를 작성한 유저       |
| content      | TEXT         | 리뷰 내용                |
| score        | INTEGER      | 리뷰 점수 (1~5)          |
| createdAt    | TIMESTAMP    | 리뷰 작성 시각           |
| updatedAt    | TIMESTAMP    | 리뷰 수정 시각           |

---

## 📂 ERD (Entity Relationship Diagram)

---

## 📚 API 명세

#### 리뷰 조회 API

`GET` 요청을 사용해서 제품별 리뷰를 조회할 수 있다.

#### Path parameters

> /products/{productId}/reviews?cursor={cursor}&size={size}

#### Request fields

| Path | Description |
|------|-------------|
| `productId` | 성품아이디        |
| `cursor`    | 커서 값 (직전 조회 API 의 응답으로 받은 cursor 값)        |
| `size`      | 조회 사이즈 (default = 10)       |

#### Response Body
```
{
    "totalCount": 15, // 해당 상품에 작성된 총리뷰 수
    "score": 4.6, // 평균 점수
    "cursor": 6,
    "reviews": [
        {
            "id": 15,
            "userId": 1, // 작성자 유저 아이디
            "score": 5,
            "content": "이걸 사용하고 제 인생이 달라졌습니다.",
            "imageUrl": "/image.png",
            "createdAt": "2024-11-25T00:00:00.000Z"
        },
        {
            "id": 14,
            "userId": 3, // 작성자 유저 아이디
            "score": 5,
            "content": "이걸 사용하고 제 인생이 달라졌습니다.",
            "imageUrl": null,
            "createdAt": "2024-11-24T00:00:00.000Z"
        }
    ]
}
```

#### 리뷰 등록 API

`POST` 요청을 사용해서 리뷰를 등록합니다.

#### Path parameters

> /products/{productId}/reviews

#### Request field

| Path | Description |
|------|-------------|
| `productId` | 성품아이디        |

- MultipartFile 타입의 단건 이미지

#### Response Body
```
NONE
```


---

