# my-blog-back-app

## Описание
Учебный проект 4 спринта из курса мидл java-разработчик яндекс практикум.

Приложение реализует REST API согласно заданию.

### Стек:
- Java 21
- Spring Boot (Web, Data JDBC)
- JUnit
- Mockito
- H2 database

## Требования:
- Java 21
- gradle

## Запуск

Склонируйте репозиторий и выполните:

```bash 
gradle bootRun
```
Приложение будет доступно по адресу:  
[http://localhost:8080/api](http://localhost:8080/api)

### Пример использоавния

Через фронтенд приложения-блога:
```
http://localhost/
```

Через rest api:
```
http://localhost:8080/api/posts?search=&pageNumber=1&pageSize=5
```

