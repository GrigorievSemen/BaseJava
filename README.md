# BaseJava
Представляет собой веб приложение по созданию, редактированию резюме, с сохранением в базу данных (PostgreSQL).
Основная часть была разработана в ходе прохождения учебного курса по основам веб разработки.
Далее было существенно переработана логика по возможности добавления новых полей.
* Использовалось при разработке, сервлеты, Collection Framework, Stream API, web-контайнер Tomcat, HTML, JUnit, JSTL, JSP. 
* Ссылка на Heroku - https://gsmresumes.herokuapp.com/resume

Для работы необходимо:
* Установленный Tomcat
* Установленная БД Postgres
* Переименовать файл "resumes.properties.origin" (config/resumes.properties.origin) на "resumes.properties"
* Заполнить данными "resumes.properties", 
  db.url - путь к БД,
  db.user - имя пользователя БД,
  db.password - пароль к БД
* в файле Config (src/ru/mystudies/basejava/Config.java) - прописать абсолютный путь к файлу resumes.properties
* после подключения к БД, создать таблицы - файл init_db.sql (config/init_db.sql)
