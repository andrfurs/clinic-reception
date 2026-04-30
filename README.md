# Clinic reception

A backend application designed to automate the work of a medical institution. It enables coordination between
administrators, doctors and patients through a centralized schedule management system.

## Technology stack

* **Language:** Java
* **Framework:** Spring Boot
* **Data access:** Spring Data JPA, Hibernate
* **Database:** MySQL
* **Documentation:** Swagger
* **Tools:** Maven, Docker

## Key features

1. For administrators, CRUD operations (create, read, update, delete) for:

* Managing appointment time slots
* Managing doctors' schedules

2. For users:

* Viewing doctors' schedules
* Creating a doctor's appointment and deleting an appointment

## How to run the project

1. Clone the repository  
   `git clone https://github.com/andrfurs/clinic-reception.git`
2. Set up the database  
   Start Docker  
   `docker-compose up -d`
3. Run the application  
   `mvn spring-boot:run`
4. View the documentation  
   Follow the link <http://localhost:8080/swagger-ui/index.html#/>  
   <img src="images/swagger.png" width="600">

## Request examples

| Method | Endpoint                                                    | Description                | Result                                   |
|--------|-------------------------------------------------------------|----------------------------|------------------------------------------|
| GET    | http://localhost:8080/user/doctors                          | Get the list of doctors    | ![img_get.png](images/img_get.png)       |
| POST   | http://localhost:8080/admin/times                           | Create a new time slot     | ![img_post.png](images/img_post.png)     |
| PUT    | http://localhost:8080/admin/schedules/{scheduleId}/{timeId} | Add a time to the schedule | ![img_put.png](images/img_put.png)       |
| DELETE | http://localhost:8080/user/appointments/{id}                | Delete an appointment      | ![img_delete.png](images/img_delete.png) |

---
***

# Реєстратура поліклініки

Бекенд-застосунок, призначений для автоматизації роботи медичного закладу. Дозволяє координувати взаємодію між
адміністраторами, лікарями та пацієнтами через централізовану систему управління розкладом.

## Стек технологій

* **Мова:** Java
* **Фреймворк:** Spring Boot
* **Доступ до даних:** Spring Data JPA, Hibernate
* **База даних:** MySQL
* **Документація:** Swagger
* **Інструменти:** Maven, Docker

## Основні можливості

1. Для адміністраторів CRUD операції (створення, перегляд, оновлення, видалення) для:

* Керування часом прийому
* Керування розкладом лікарів

2. Для користувачів:

* Перегляд розкладу лікарів
* Створення запису на прийом до лікаря та видалення запису

## Як запустити проєкт

1. Клонуйте репозиторій  
   `git clone https://github.com/andrfurs/clinic-reception.git`
2. Налаштуйте БД  
   Запустіть Docker  
   `docker-compose up -d`
3. Запустіть застосунок  
   `mvn spring-boot:run`
4. Перегляд документації  
   Перейдіть за посиланням <http://localhost:8080/swagger-ui/index.html#/>  
   <img src="images/swagger.png" width="600">

## Приклади запитів

| Метод  | Ендпоінт                                                    | Опис                     | Результат                                |
|--------|-------------------------------------------------------------|--------------------------|------------------------------------------|
| GET    | http://localhost:8080/user/doctors                          | Отримати список лікарів  | ![img_get.png](images/img_get.png)       |
| POST   | http://localhost:8080/admin/times                           | Створити новий час       | ![img_post.png](images/img_post.png)     |
| PUT    | http://localhost:8080/admin/schedules/{scheduleId}/{timeId} | Додати час у розклад     | ![img_put.png](images/img_put.png)       |
| DELETE | http://localhost:8080/user/appointments/{id}                | Видалити запис на прийом | ![img_delete.png](images/img_delete.png) |
