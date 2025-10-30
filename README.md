# Cassandra

Особенности:

* Для простоты в бд хранятся не BLOB, а условные ссылки на фото;
* Список товаров выводит не всю информацию, а только основную
* Список комментариев выводит не все комментарии. Только комментарии к определённому товару

## Действия с карточками товара

### /put/card

Принимает:

* Название
* Описание
* Цены
* Фото

Добавляет товар и возвращает:

* ID товара

Пример:

Метод: POST

url: http://localhost:8080/put/card

Тело запроса в формате JSON:
```json
{
  "name": "Phone v1",
  "description": "BLA BLA BLA!!!",
  "prices": [100, 110, 250, 146, 820],
  "picture_urls": ["https//my.storage.com/shop/products/1", "https//my.storage.com/shop/products/2"]
}
```

Тело ответа:
```json
{
  "id": "1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9"
}
```

### /get/card

Принимает:

* ID товара

Выводит карточку товара:

* ID товара
* Название
* Описание
* Цены
* Фото
* Отзывы

Пример:

Метод: GET

url: http://localhost:8080/get/card?id=1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9

Тело запроса: Отсутствует

Тело ответа:
```json
{
  "cardId": "1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9",
  "productName": "Phone v1",
  "productDescription": "BLA BLA BLA!!!",
  "prices": [ 100, 110, 250, 146, 820 ],
  "pictureUrls": [ "https//my.storage.com/shop/products/1", "https//my.storage.com/shop/products/2" ],
  "reviews": [
    {
      "productId": "1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9",
      "reviewId": "99bff960-b5c7-11f0-a6c9-b9f5b57cd4a9",
      "comment": "good Phone!",
      "picture_url": [ "https//my.storage.com/shop/reviews/3", "https//my.storage.com/shop/reviews/4" ]
    },
    {
      "productId": "1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9",
      "reviewId": "873ca2b0-b5c8-11f0-a6c9-b9f5b57cd4a9",
      "comment": "bad Phone!",
      "picture_url": [ "https//my.storage.com/shop/reviews/1", "https//my.storage.com/shop/reviews/5" ]
    }
  ]
}
```


### /list/cards

Выводит основную информацию о товаре:

* ID товара
* Название
* Минимальная из цен

Пример:

Метод: GET

url: http://

Тело запроса: Отсутствует

Тело ответа:
```json
[
    {
        "cardId": "021d4f40-b5c7-11f0-a6c9-b9f5b57cd4a9",
        "productName": "Phone v2",
        "price": 200
    },
    {
        "cardId": "1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9",
        "productName": "Phone v1",
        "price": 100
    }
]
```


## Действия с отзывами к товару

### /put/review

Принимает:

* ID товара
* текст
* Приложенные фото

Добавляет отзыв и выводи:

* ID товара
* ID отзыва

Пример:

Метод: POST

url: http://localhost:8080/put/review

Тело запроса в формате JSON:
```json
{
    "product_id": "1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9",
    "comment": "good Phone!",
    "picture_url": ["https//my.storage.com/shop/reviews/3", "https//my.storage.com/shop/reviews/4"]
}
```

Тело ответа:
```json
{
    "product_id": "1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9",
    "comment_id": "99bff960-b5c7-11f0-a6c9-b9f5b57cd4a9"
}
```

### /get/review

Принимает:

* ID товара
* ID отзыва

Выводит конкретный отзыв:

* ID товара
* ID отзыва
* текст
* Приложенные фото

Пример:

Метод: GET

url: http://localhost:8080/get/review?product_id=1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9&comment_id=99bff960-b5c7-11f0-a6c9-b9f5b57cd4a9

Тело запроса: Отсутствует

Тело ответа:
```json
{
    "productId": "1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9",
    "reviewId": "99bff960-b5c7-11f0-a6c9-b9f5b57cd4a9",
    "comment": "good Phone!",
    "picture_url": [
        "https//my.storage.com/shop/reviews/3",
        "https//my.storage.com/shop/reviews/4"
    ]
}
```

### /list/reviews

Принимает:

* ID товара

Выводит все комментарии к этом заказу:

* ID товара
* ID отзыва
* текст
* Приложенные фото

Пример:

Метод: GET

url: http://localhost:8080/list/reviews?product_id=1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9

Тело запроса: Отсутствует

Тело ответа:
```json
[
    {
        "productId": "1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9",
        "reviewId": "99bff960-b5c7-11f0-a6c9-b9f5b57cd4a9",
        "comment": "good Phone!",
        "picture_url": [
            "https//my.storage.com/shop/reviews/3",
            "https//my.storage.com/shop/reviews/4"
        ]
    },
    {
        "productId": "1cd74f30-b5c6-11f0-a6c9-b9f5b57cd4a9",
        "reviewId": "873ca2b0-b5c8-11f0-a6c9-b9f5b57cd4a9",
        "comment": "bad Phone!",
        "picture_url": [
            "https//my.storage.com/shop/reviews/1",
            "https//my.storage.com/shop/reviews/5"
        ]
    }
]
```
