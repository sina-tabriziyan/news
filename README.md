# Compose News App

A modern, modular Android News App built using **Jetpack Compose**, **Ktor**, **MVI architecture**, and **Koin** for dependency injection. The app fetches real-time news and displays it with offline support, favorites, and a responsive UI.

---

## 🏗️ Project Architecture

The app follows **Clean Architecture** with separation into distinct modules:


### 🧠 Architectural Pattern

We use the **Model-View-Intent (MVI)** pattern for state management, ensuring:

- Unidirectional data flow
- Predictable state changes
- Clear separation of concerns

### 🔌 Dependency Injection

- **Koin** is used to manage dependencies across all modules, supporting dynamic feature injection and testing.

### 🔗 Networking

- **Ktor Client** for efficient, asynchronous API calls with full coroutine support.
- Supports structured error handling and request logging.

### 🎨 UI

- Built entirely with **Jetpack Compose**.
- **Coil** used for fast, asynchronous image loading.
- Adaptive layouts and responsive components.

### 🧭 Navigation

- Navigation is handled with `androidx.navigation.compose` to support seamless transitions between screens.

---

## 🧩 Key Features

- Modular, scalable architecture
- Clean separation between data, domain, and presentation
- Top headlines displayed for selected companies (e.g., Apple, Google, Microsoft, Tesla)
- Offline support with **Room**
- Favorite articles persistence
- MVI-based UI state management
- Custom `Result` and `Error` handling system

---

## 🛠️ Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Ktor**
- **Room**
- **Koin**
- **Coil**
- **Navigation Compose**
- **DataStore** for lightweight persistence

---

## 📝 Additional Notes

- The project is structured to easily support additional features and new screens by simply adding modules (e.g., `feature:details`, `feature:favorites`).
- Designed with testability in mind. Each module is decoupled and can be tested independently.
- `Result<D, E>` and `sealed interface Error` are used for centralized error propagation across layers.
- You can easily switch out the data source (e.g., swap NewsAPI with another provider) by updating the `data` module.

---

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/compose-news-app.git
