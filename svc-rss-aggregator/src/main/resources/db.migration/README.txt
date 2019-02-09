Запуск миграции:
mvn -P migration liquibase:update -Dliquibase.url=jdbc:postgresql://<HOST>:<PORT>/<SID> -Dliquibase.username=<USERNAME> -Dliquibase.password=<PASSWORD>

Откат миграции:
mvn -P migration liquibase:rollback -Dliquibase.url=jdbc:postgresql://<HOST>:<PORT>/<SID> -Dliquibase.username=<USERNAME> -Dliquibase.password=<PASSWORD>

По умолчанию rollback на один сегмент.