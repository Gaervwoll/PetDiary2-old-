[🇬🇧 English](README.md) | **🇷🇺 Русский**

## PetDiary (черновик README)

Небольшое Android‑приложение для ведения дневника питомца. Содержит экраны регистрации пользователя и первоначального ввода профиля питомца (имя, возраст, фото). В репозитории присутствуют две параллельные заготовки: классическая на XML и заготовка на Jetpack Compose.

### Статус
- В текущем виде проект не запускается «как есть» из‑за конфигурационных несоответствий (подробности ниже в разделе «Известные проблемы»).
- По умолчанию точкой входа в манифесте `app/src/main/AndroidManifest.xml` является `MainActivity` с демо‑экраном на Jetpack Compose.

## Возможности
- **Экран регистрации** (`RegistrationActivity`):
  - Поля: имя, email, пароль, подтверждение пароля
  - Валидации: корректность email, минимальная длина пароля, совпадение паролей
  - Заготовка перехода к экрану приветствия
- **Экран приветствия/профиля питомца** (`WelcomeActivity`):
  - Поля: имя питомца, возраст (годы и месяцы)
  - Добавление фото питомца из галереи (чтение из медиатеки)
  - Базовые проверки заполнения и уведомления `Toast`

## Технический стек
- Язык: **Kotlin**
- UI: **XML (Material Components, ConstraintLayout)** и заготовка на **Jetpack Compose**
- Минимальная версия Android: **minSdk 24**
- Целевая версия: **targetSdk 34**
- Зависимости (основные):
  - `androidx.appcompat:appcompat`
  - `com.google.android.material:material`
  - `androidx.constraintlayout:constraintlayout`
  - `com.github.bumptech.glide:glide`
  - Jetpack Compose BOM, Material3 (для демо‑экрана)

## Структура проекта (упрощённо)
- `app/src/main/` — стандартный source set Gradle
  - `AndroidManifest.xml` — объявлена `com.example.petdiary.MainActivity` как LAUNCHER
  - `java/com/example/petdiary/MainActivity.kt` — демо на Compose
  - `res/` — стандартные ресурсы, темы и т.д.
- `app/main/` — нестандартный для Gradle путь (в текущей конфигурации не является активным source set)
  - `AndroidManifest.xml` — отдельный манифест с `com.example.app.RegistrationActivity` как LAUNCHER и разрешением на чтение медиа
  - `java/com/example/app/RegistrationActivity.kt`
  - `java/com/example/app/WelcomeActivity.kt`
  - `res/layout/activity_registration.xml`, `res/layout/activity_welcome.xml`

## Сборка и запуск
1. Откройте проект в Android Studio (Arctic Fox+ / Hedgehog+).
2. Дождитесь завершения Gradle Sync.
3. Запустите сборку и установку на устройство/эмулятор (Android 7.0+).

Примечание: из‑за текущей структуры и конфликтов (см. ниже) сборка и/или запуск могут завершаться с ошибками.

## Известные проблемы
- **Два различных «слоя» приложения в одном модуле**:
  - Активный `namespace` модуля: `com.example.petdiary` (см. `app/build.gradle.kts`).
  - Классические активности используют пакет `com.example.app` и размещены в нестандартном пути `app/main/java`, а их ресурсы — в `app/main/res`.
  - В результате Gradle компилирует `app/src/main`, а файлы из `app/main` игнорируются; при этом существуют два разных `AndroidManifest.xml` в `app/src/main` и `app/main`.
- **Конфликт точек входа**: в обоих манифестах объявлены LAUNCHER‑активности (`MainActivity` и `RegistrationActivity`). Фактически учитывается только `app/src/main/AndroidManifest.xml`.
- **Разрешения на чтение фото**: в `app/main/AndroidManifest.xml` используется `READ_EXTERNAL_STORAGE`. На Android 13+ необходимо использовать `READ_MEDIA_IMAGES`, а выбор изображения лучше делать через современные API (`ActivityResultContracts`).

### Что потребуется, чтобы привести проект к рабочему состоянию (кратко)
- Привести все исходники и ресурсы к одному активному source set — обычно `app/src/main`.
- Выбрать единый пакет/namespace (напр. `com.example.petdiary`) и обновить его в коде и манифесте.
- Оставить один манифест и одну LAUNCHER‑активность.
- Проверить зависимости и версии плагинов/Gradle.

## Планы и идеи
- Экран логина и реальная регистрация (сервер/локальное хранилище)
- Сохранение профиля питомца (Room/Datastore)
- Современный выбор изображения и поддержка Android 13+ разрешений
- Навигация между экранами, экран ленты/дневника, напоминания и т.д.

## Лицензия
Не указана. Если потребуется, добавьте файл `LICENSE`. 