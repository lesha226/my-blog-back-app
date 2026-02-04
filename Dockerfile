# Используем официальный образ Tomcat в качестве базового
FROM tomcat:10.1-jdk21-openjdk

# Копируем WAR-файл в каталог webapps
COPY target/api.war /usr/local/tomcat/webapps/

# Открываем порт 8080 для доступа к приложению
EXPOSE 8080

# Запускаем Tomcat при старте контейнера
CMD ["catalina.sh", "run"]
