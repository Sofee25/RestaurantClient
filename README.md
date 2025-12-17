# Restaurant Client Android App

![Android](https://img.shields.io/badge/Android-34-green?logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue?logo=kotlin)
![Material Design 3](https://img.shields.io/badge/Material%20Design-3-purple)

**A modern Android restaurant ordering system with glassmorphism UI and real-time updates**

---

## 📱 Description

Restaurant Client is a comprehensive mobile application designed to streamline restaurant order management. Built with modern Android development practices, it provides an efficient and intuitive platform for both administrators and customers to handle orders, products, and user accounts with a beautiful glassmorphism UI.

### Backend Server
**ARROW (Asynchronous Rust Restaurant Order Workflow)** - A high-performance backend server built with Rust, leveraging the language's safety and concurrency features to deliver robust and efficient restaurant operations management.

---

## ✨ Features

### 👤 Customer Features
- **🔐 Secure Authentication** - Role-based access control with encrypted credential storage
- **🍽️ Product Browsing** - Browse restaurant menu with categories and detailed product information
- **🛒 Shopping Cart** - Real-time cart management with glassmorphism UI
  - Add/remove items
  - Adjust quantities
  - Live price calculations
  - Glass blur effects for modern look
- **💳 Checkout Process** - Streamlined order placement with order summary
- **📦 Order History** - View past orders with detailed information
- **👥 User Profile** - Manage account information and preferences

### 🔧 Administrator Features
- **📊 Admin Dashboard** - Comprehensive overview with statistics and quick actions
- **👥 User Management** - Create, update, and delete user accounts with role assignment
- **🍔 Product Management** - Full CRUD operations for menu items
- **📋 Order Management** - View, update, and track all customer orders
- **🎨 Modern Admin UI** - Professional blue theme with glassmorphism effects

### 🎨 UI/UX Features
- **Glassmorphism Design** - Modern blur effects throughout the app
- **Material Design 3** - Latest Material Design guidelines
- **Responsive Layouts** - Optimized for various screen sizes
- **Glass Components** - Buttons, cards, FABs, and dialogs with blur effects
- **Smooth Animations** - Polished transitions and interactions

---

## ��️ Technologies Used

### Frontend
- **Language:** Kotlin 1.9.22
- **UI Framework:** Android XML Layouts with ViewBinding
- **Architecture:** MVVM (Model-View-ViewModel)
- **Minimum SDK:** API 26 (Android 8.0 Oreo)
- **Target SDK:** API 34 (Android 14)

### Core Libraries
- **Hilt:** 2.51.1 (Dependency Injection)
- **Retrofit:** 2.9.0 (REST API)
- **OkHttp:** 4.11.0 (HTTP Client)
- **Kotlin Coroutines:** 1.7.3 (Async)
- **Lifecycle & ViewModel:** 2.7.0
- **Material Design:** 1.13.0
- **BlurView:** 3.2.0 (Glassmorphism)
- **Security Crypto:** 1.1.0-alpha06

### Backend
- **Framework:** Rust (ARROW Server)
- **Database:** MySQL

### Development Tools
- **Android Studio:** Ladybug | 2024.2.1
- **Gradle:** 8.13
- **JDK:** 21 (21.0.8)
- **Git:** 2.48.1

---

## �� Installation

### Prerequisites
- Android Studio Ladybug 2024.2.1 or later
- JDK 21 (21.0.8 or later)
- Git 2.48.1 or later
- Minimum Android SDK: API 26

### Steps

1. Clone the repository:
```bash
git clone https://github.com/your-username/RestaurantClient.git
cd RestaurantClient
```

2. Open in Android Studio

3. Configure Backend API - Create .env/.env file:
```properties
API_BASE_URL=http://your-backend-server.com/api/
```

4. Sync Gradle and Run

---

## 📖 Usage

### Customer Role
- Register/Login
- Browse products
- Add to cart
- Checkout
- View order history

### Administrator Role
- Access admin dashboard
- Manage users
- Manage products
- Manage orders

---

## 🏗️ Architecture

MVVM (Model-View-ViewModel) with:
- View Layer (Activities, XML)
- ViewModel Layer (Business Logic)
- Repository Layer (Data)
- Data Layer (API, Local Storage)

---

## 📚 Documentation

See docs/ folder:
- UI_MODERNIZATION_PLAN.md
- GLASS_COMPONENTS.md
- THEME_SELECTION_GUIDE.md
- THEME_QUICK_REFERENCE.md
- THEME_SELECTOR_GUIDE.md

---

## 🔐 Security

- Encrypted SharedPreferences
- HTTPS communication
- Secure session management
- Input validation

---

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Make changes
4. Commit and push
5. Open Pull Request

---

## 📝 License

MIT License - see LICENSE file

---

## 🙏 Acknowledgments

- Material Design 3
- BlurView Library
- Hilt
- Retrofit
- ARROW Server Team

---

Made with ❤️ using Kotlin and Material Design 3
