# idt-grid-test-task
A test application to construct a grid table. It has 2 screens:
- Initial screen has 2 text fields to set up the number of columns and rows
- Second screen - grid with randomly generated text

## Demo
#1 Initial screen https://github.com/user-attachments/assets/b25219cc-01ea-40ab-8581-6e52faf1c95c

#2 Initial Screen - Error Handling https://github.com/user-attachments/assets/8bc74d40-cb0b-48ba-919e-7e1391b56d74

#3 Grid Screen https://github.com/user-attachments/assets/49905609-a7de-4ca6-8471-6d754666edf3

# Project

## Structure
The project contains 3 Gradle modules which present different architecture layers:
- App - Presentation Layer
- Domain - Domain Layer
- Data - Data layer
  
All Gradle modules are tuned by convention plugins places in build-plugin

### 1 - App - Presentation Layer
Presentation Layer implemented by using:
- Jetpack Compose
- ViewModel
- Jetpack Compose Navigation
Screens and view models has been covered by unit and instrumentational tests

### 2 - Domain - Domain Layer
- Contains use cases implemenation, domain models and repo interfaces.
- All necessary classes covered by unit tests

### 3 - Data - Data layer
- Contains implementation of repos and data sources
- All necessary classes covered by unit tests

## Frameworks
- Kotlin
- Kotlin Coroutines + Flow
- Jetpack Compose
- Compose Navigation
- Koin
- JUnit4
- MockK
