# goTel

goTel is a powerful backend application designed to serve real-world data for languages, cities, and countries via RESTful endpoints. It is ideal for developers building applications in Angular, JavaScript, or any modern front-end framework who need reliable, up-to-date data for forms and selection inputs—without resorting to static mock data.

## 🚀 Value Proposition

Many frontend applications rely on hardcoded or mock data for dropdowns and selection fields involving cities, countries, or languages. goTel solves this problem by providing a backend service powered by Java and Maven, delivering real data—fast and efficiently—using concurrent programming techniques. This ensures your applications have access to accurate and timely information, enhancing user experience and reducing maintenance.

## ✨ Features

- **RESTful Endpoints:** Quickly access lists of cities, countries, and languages.
- **Real Data:** No more mock data—get actual up-to-date information.
- **High Performance:** Built with Java and optimized with concurrent programming for fast response times.
- **Framework Agnostic:** Perfect for integration with Angular, JavaScript, React, Vue, and more.
- **Easy Deployment:** Use Maven for builds and Docker Compose for containerized running.

## 🏗️ Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- Docker (optional, for containerized deployment)

### Build & Run (Locally)

1. **Clone the repository**
   ```bash
   git clone https://github.com/leonard1thecoder/goTel.git
   cd goTel
   ```

2. **Build with Maven**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

### Run with Docker Compose

```bash
docker-compose up --build
```

## 🔗 API Endpoints

| Endpoint              | Description                          |
|-----------------------|--------------------------------------|
| `/api/countries`      | Returns list of countries            |
| `/api/cities`         | Returns list of cities               |
| `/api/languages`      | Returns list of languages            |
> More details and parameters coming soon!

## 💡 Use Cases

- Populate country, city, and language dropdowns in web forms.
- Use in registration, profile, or booking forms that require real location and language data.
- Integrate with any frontend application needing verified world data.

## 🤝 Contributing

Contributions are welcome! Please open issues or pull requests for improvements, bug fixes, or additional features.

## 📄 License

This project is licensed under the terms of your chosen license.

## 🙋‍♂️ Contact

For questions or support, open an issue or contact [leonard1thecoder](https://github.com/leonard1thecoder).

---
Powerful, real-world data for your frontend—powered by goTel.
