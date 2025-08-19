**🇬🇧 English** | [🇷🇺 Русский](README.ru.md)

## PetDiary (draft README)

A small Android app for keeping a pet diary. It includes a user registration screen and an initial pet profile setup (name, age, photo). The repo contains two parallel scaffolds: a classic XML-based UI and a Jetpack Compose demo.

### Status
- In its current shape the project may not run out of the box due to configuration mismatches (see "Known issues").
- By default, the entry point declared in `app/src/main/AndroidManifest.xml` is `MainActivity` with a Jetpack Compose demo screen.

## Features
- **Registration screen** (`RegistrationActivity`):
  - Fields: name, email, password, confirm password
  - Validations: email format, minimum password length, password match
  - Placeholder navigation to the welcome screen
- **Welcome / Pet profile screen** (`WelcomeActivity`):
  - Fields: pet name, age (years and months)
  - Pick a pet photo from gallery (media read access)
  - Basic input checks with `Toast` messages

## Tech stack
- Language: **Kotlin**
- UI: **XML (Material Components, ConstraintLayout)** plus a **Jetpack Compose** demo
- Min Android: **minSdk 24**
- Target Android: **targetSdk 34**
- Key dependencies:
  - `androidx.appcompat:appcompat`
  - `com.google.android.material:material`
  - `androidx.constraintlayout:constraintlayout`
  - `com.github.bumptech.glide:glide`
  - Jetpack Compose BOM, Material3 (for the demo screen)

## Project structure (simplified)
- `app/src/main/` — the standard Gradle source set
  - `AndroidManifest.xml` — declares `com.example.petdiary.MainActivity` as LAUNCHER
  - `java/com/example/petdiary/MainActivity.kt` — Compose demo
  - `res/` — standard resources, themes, etc.
- `app/main/` — a non-standard path for Gradle (not an active source set with current config)
  - `AndroidManifest.xml` — separate manifest declaring `com.example.app.RegistrationActivity` as LAUNCHER and storage permission
  - `java/com/example/app/RegistrationActivity.kt`
  - `java/com/example/app/WelcomeActivity.kt`
  - `res/layout/activity_registration.xml`, `res/layout/activity_welcome.xml`

## Build and run
1. Open the project in Android Studio (Arctic Fox+ / Hedgehog+).
2. Let Gradle Sync complete.
3. Build and run on a device/emulator (Android 7.0+).

Note: due to the current structure and conflicts (see below), build and/or run may fail.

## Known issues
- **Two different "layers" in a single module**:
  - Active module `namespace`: `com.example.petdiary` (see `app/build.gradle.kts`).
  - Legacy activities use package `com.example.app` and live under the non-standard `app/main/java` with resources under `app/main/res`.
  - As a result Gradle compiles `app/src/main` while `app/main` is ignored; there are two separate manifests in `app/src/main` and `app/main`.
- **Entry point conflict**: both manifests declare LAUNCHER activities (`MainActivity` and `RegistrationActivity`). Only `app/src/main/AndroidManifest.xml` actually takes effect.
- **Photo read permissions**: `READ_EXTERNAL_STORAGE` is used in `app/main/AndroidManifest.xml`. On Android 13+ you should use `READ_MEDIA_IMAGES` and modern selection APIs (`ActivityResultContracts`).

### What’s needed to make it work (short)
- Consolidate all sources and resources into a single active source set, typically `app/src/main`.
- Use a single package/namespace (e.g., `com.example.petdiary`) and update code and manifest accordingly.
- Keep one manifest and one LAUNCHER activity.
- Verify dependency and Gradle/plugin versions.

## Roadmap / ideas
- Login screen and real registration (backend/local storage)
- Persist pet profile (Room/Datastore)
- Modern image picking and Android 13+ permission support
- Navigation between screens, diary feed, reminders, etc.

## License
Not specified. Add a `LICENSE` file if needed. 