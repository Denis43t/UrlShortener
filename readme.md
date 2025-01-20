______________________________________________________________________________
DATASOURCE_URL=jdbc:postgresql://db:5432/shortener_db
DB_USERNAME=GoITgroup3User
DB_PASSWORD=GoITgroup3Password
JWT_SECRET_KEY=GoITgroup3JWTsecretKey
______________________________________________________________________________
Порти:
default: 8080
prod: 9999
______________________________________________________________________________
Для того, щоб створити та запустити додаток разом з БД в докер-контейнерах:

1. Оберіть в меню Maven профіль prod.

2. Збілдіть проект через mvn clean package.

3. Виконайте тут в терміналі ось це однією командою:

$env:DB_USERNAME="GoITgroup3User"
$env:DB_PASSWORD="GoITgroup3Password"
$env:JWT_SECRET_KEY="GoITgroup3JWTsecretKey"
docker-compose up --build   
______________________________________________________________________________