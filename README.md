
# Запуск

## Запуск проекта через Docker Compose

```bash
docker-compose up --build 
```

### Остановка контейнеров

```bash
docker-compose down
```

### Проверка

После запуска приложение будет доступно по адресу:

```
http://localhost:8080
```


# API Документация

Базовый URL: `http://localhost:8080`

---

## Управление подписками (`/subscriptions`)

### Получить топ подписок

**GET** `/subscriptions/top`

Возвращает список самых популярных подписок с количеством подписчиков.

#### Пример запроса:

```bash
curl -X GET http://localhost:8080/subscriptions/top
```

#### Пример ответа:

```json
[
  {
    "subscription": "YouTube Premium",
    "subscribersCount": 5
  },
  {
    "subscription": "Яндекс.Плюс",
    "subscribersCount": 3
  },
  {
    "subscription": "VK Музыка",
    "subscribersCount": 2
  }
]
```

---

## Управление пользователями (`/users`)

### Получить пользователя по ID

**GET** `/users/{id}`

Возвращает информацию о пользователе.

#### Пример запроса:

```bash
curl -X GET http://localhost:8080/users/1
```

#### Пример ответа:

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "subscriptionList": [
    {
      "id": 1,
      "code": "youtube",
      "name": "YouTube Premium"
    },
    {
      "id": 2,
      "code": "vk_music",
      "name": "VK Музыка"
    }
  ]
}
```

---

### Создать пользователя

**POST** `/users`

Создаёт нового пользователя.

#### Тело запроса:

```json
{
  "name": "Иван",
  "email": "ivan@example.com"
}
```

#### Пример запроса:

```bash
curl -X POST http://localhost:8080/users   -H "Content-Type: application/json"   -d '{"name": "Иван", "email": "ivan@example.com"}'
```

#### Пример ответа:

```json
{
  "id": 1,
  "name": "Иван",
  "email": "ivan@example.com"
}
```

---

### Обновить пользователя

**PUT** `/users/{id}`

Обновляет информацию о пользователе.

#### Тело запроса:

```json
{
  "name": "Иван Обновлённый",
  "email": "ivan_new@example.com"
}
```

#### Пример запроса:

```bash
curl -X PUT http://localhost:8080/users/1   -H "Content-Type: application/json"   -d '{"name": "Иван Обновлённый", "email": "ivan_new@example.com"}'
```

#### Пример ответа:

```json
{
  "id": 1,
  "name": "Иван Обновлённый",
  "email": "ivan_new@example.com",
  "subscriptionList": []
}
```

---

### Удалить пользователя

**DELETE** `/users/{id}`

Удаляет пользователя по ID.

#### Пример запроса:

```bash
curl -X DELETE http://localhost:8080/users/1
```

---

### Добавить подписку пользователю

**POST** `/users/{id}/subscriptions`

Добавляет подписку пользователю по ID.

#### Тело запроса:

```json
{
  "code": "netflix",
  "name": "Netflix"
}
```

#### Пример запроса:

```bash
curl -X POST http://localhost:8080/users/1/subscriptions   -H "Content-Type: application/json"   -d '{"code": "netflix", "name": "Netflix"}'
```

#### Пример ответа:

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "subscriptionList": [
    {
      "id": 1,
      "code": "youtube",
      "name": "YouTube Premium"
    },
    {
      "id": 2,
      "code": "vk_music",
      "name": "VK Музыка"
    },
    {
      "id": 4,
      "code": "netflix",
      "name": "Netflix"
    }
  ]
}
```

---

### Удалить подписку у пользователя

**DELETE** `/users/{id}/subscriptions?code={netflix}`

Удаляет подписку по коду у пользователя.

#### Пример запроса:

```bash
curl -X DELETE "http://localhost:8080/users/1/subscriptions?code=netflix"
```

#### Пример ответа:

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "subscriptionList": [
    {
      "id": 1,
      "code": "youtube",
      "name": "YouTube Premium"
    },
    {
      "id": 2,
      "code": "vk_music",
      "name": "VK Музыка"
    }
  ]
}
```
