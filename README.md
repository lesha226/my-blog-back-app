# my-blog-back-app

## Описание
Учебный проект 3 спринта из курса мидл java-разработчик яндекс практикум.

Бэкэнд приложение для сервлет-контейнера Tomcat. 
Реализует REST API согласно заданию.

### Стек:
- Java 21
- Spring 6.2.1
- JUnit 5.11.2
- Mockito 5.14.2
- H2 database 2.2.224
- Tomcat 10

## Сборка и установка

Требования: 
- сервлет-контейнер Tomcat 10
- maven
- Java 21

Команда для сборки: 
```bash 
mvn package
```

Команда копирования приложения в Tomcat:
```shell 
cp ./target/api.war $TOMCAT_HOME/webapps/api.war
```


### Пример использоавния

Через фронтенд приложения-блога:
```
http://localhost/
```

Через rest api:
```
http://localhost:8080/api/posts?search=&pageNumber=1&pageSize=5
```

