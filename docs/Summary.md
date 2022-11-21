# Отчёт о проведённой автоматизации

## 1. Что было запланировано и что было сделано

В результате работы над проектом были выполнены все запланированные задачи, а именно:

1. Автоматизированы позитивные и негативные тестовые сценарии покупки тура через UI
  ("Оплата по карте" и "Кредит по данным карты");
2. Протестирована работа сервиса на двух СУБД (MySQL и PostgreSQL);
3. Автоматизированы тесты API для приложения;
4. Сгенерирован подробный отчет о проведенном тестировании в Allure Report.

Был использован весь перечень выбранных инструментов.

## 2. Сработавшие риски

В процессе автоматизации тестирования сработала часть из ожидаемых рисков:

- Возникли некоторые трудности с подключением PostgreSQL и SUT к Docker;
- Проверка правильности работы автотестов вручную и исключение падений по причине наличия
  значительного числа дефектов в сервисе потребовали дополнительного времени;
- Поиск информации и настройка параметризации запуска заняли еще некоторое время вне плана.

## 3. Общий итог по времени

|  №  | Наименование работы                                             | Запланировано<br/> времени, ч | Потрачено<br/> времени, ч |
|:---:|:----------------------------------------------------------------|:-----------------------------:|:-------------------------:|
|  1  | Разработка плана тестирования                                   |              10               |            16             |
|  2  | Написание и отладка авто-тестов                                 |              40               |            55             |
|  3  | Подготовка отчетной документации, <br/> оформление баг-репортов |              12               |            12             |

Потраченное время на непосредственно автоматизацию (п. 2) превысило запланированное из-за сработавших рисков.

**Итого запланировано:** 76 часов

**Итого потрачено:** 84 часа