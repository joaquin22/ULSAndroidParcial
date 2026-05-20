# Parcial - Juego de Colores (Android)

Aplicación Android desarrollada con **Kotlin + Jetpack Compose**.  
El objetivo del juego es seleccionar rápidamente el nombre correcto del color mostrado en pantalla antes de que termine el tiempo.

## Descripción

El flujo principal está compuesto por 3 pantallas:

- **Bienvenida** (`WelcomeScreen`)
- **Juego** (`GameScreen`)
- **Resultados** (`ResultScreen`)

La navegación se maneja con `navigation-compose` en `app/src/main/java/com/uls/parcial/ui/navigation/AppNavigation.kt`.

## Reglas del juego

- Duración por partida: **30 segundos**
- Puntaje por respuesta correcta: **+10**
- Puntaje por respuesta incorrecta: **-5**
- El puntaje mínimo se mantiene en **0**
- Al terminar el tiempo, se muestra resumen de resultados y récord

Lógica principal en `app/src/main/java/com/uls/parcial/viewmodel/GameViewModel.kt`.

## Características

- Interfaz moderna con Jetpack Compose
- Feedback visual de acierto/error
- Contador de tiempo con indicador circular
- Cálculo de precisión
- Historial de partidas de la sesión
- Persistencia de récord histórico con `SharedPreferences`

## Tecnologías usadas

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**

Dependencias y versiones: `gradle/libs.versions.toml` y `app/build.gradle.kts`.

## Estructura del proyecto

```text
app/src/main/java/com/uls/parcial/
├── MainActivity.kt
├── model/
│   └── Models.kt
├── viewmodel/
│   └── GameViewModel.kt
└── ui/
    ├── navigation/
    │   └── AppNavigation.kt
    ├── screens/
    │   ├── WelcomeScreen.kt
    │   ├── GameScreen.kt
    │   └── ResultScreen.kt
    └── components/
        ├── RuleItem.kt
        └── StatCard.kt
