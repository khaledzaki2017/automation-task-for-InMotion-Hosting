# InMotion Hosting – Automation Testing Task

## Overview
This project contains an **end-to-end automation solution** for the main e-commerce flow on **InMotion Hosting**

---

## Automation Scope
The automated tests cover the core customer journey:

### Happy Path Scenarios
- Search for an available domain
- Verify domain availability and pricing
- Add domain to cart
- Select a hosting plan
- Verify cart contents and total price
- Validate cart persistence across navigation and refresh

### Negative / Validation Scenarios
- Search for an unavailable domain (e.g. `google.com`)
- Verify domain unavailability message
- Validate suggested alternative domains (when available)

---

## Technology Stack
- **Java**
- **Selenium WebDriver**
- **TestNG**
- **Maven**
- **Page Object Model (POM)**
- **Custom Explicit Wait Helper**

---

## Framework Design
- Page Object Model for clear separation of concerns
- Centralized **WaitHelper** for reliable synchronization
- No hard-coded sleeps
- Stable locators based on DOM structure
- Reusable and readable test steps
- Designed to be scalable for additional scenarios

---

## Execution Instructions
### Prerequisites
- Java 11 or higher
- Maven
- Chrome browser

### Run Tests
```bash
mvn clean test

```
### Watch Video of the Automation Task's Excution  
## https://www.youtube.com/watch?v=-VzmLg_3H0Y
[![InMotion Hosting E2E](https://img.youtube.com/vi/-VzmLg_3H0Y/0.jpg)]([www.youtube.com](https://www.youtube.com/watch?v=-VzmLg_3H0Y))


