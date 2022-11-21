# Дипломный проект профессии «Тестировщик ПО»

## Документация

#### [План автоматизации тестирования](./docs/plan.md)
#### [Отчёт о проведённом тестировании](./docs/Report.md)
#### [Отчёт о проведённой автоматизации](./docs/Summary.md)

## Инструкция по запуску SUT и авто-тестов

### Предусловия

1. Для запуска проекта и тестов потребуются установленные 
Git, JDK 11, IntelliJ IDEA, Docker, Docker Compose.
2. Запустить Docker Desktop.
3. Клонировать репозиторий на локальную машину командой в Git:

   `git clone https://github.com/RushanZur/QA-Diplom`

4. Запустить IntelliJ IDEA и открыть проект.

### 1. Запуск контейнеров

Для старта контейнеров с БД (MySQL, PostgreSQL) и симулятором банковских сервисов
выполнить в терминале из корня проекта команду: `docker-compose up`.

### 2. Запуск SUT

В новой вкладке терминала из корня проекта выполнить одну из команд:

* С подключением к MySQL:

  `java -jar artifacts\aqa-shop.jar --Dspring.datasource.url=jdbc:mysql://localhost:3306/app`

* С подключением к PostgreSQL:

  `java -jar artifacts\aqa-shop.jar --Dspring.datasource.url=jdbc:postgresql://localhost:5432/app`

### 3. Запуск авто-тестов

#### Все тестовые классы

В Run anything(двойное нажатие Ctrl) выполнить одну из команд в зависимости от выбранной БД в п. 2.

* С подключением к MySQL:

  `./gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app`

* С подключением к PostgreSQL:

  `./gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app`

### 4. Формирование отчета AllureReport

В терминале выполнить команду: `./gradlew allureServe`.
Отчет сгенерируется на основе результатов последнего прогона тестов и автоматически откроется в браузере.
По окончании работы с отчетом необходимо завершить выполнение процесса allureServe, нажав Ctrl + F2 во вкладке «Run» терминала.
