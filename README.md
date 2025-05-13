# coffee-cart Automated Tests
🧪 **Automated testing for the [coffee-cart.app](https://coffee-cart.app) web application**
![CodeRabbit Pull Request Reviews](https://img.shields.io/coderabbit/prs/github/UA-1378-TAQC/coffeecart.taqc?utm_source=oss&utm_medium=github&utm_campaign=UA-1378-TAQC%2Fcoffeecart.taqc&labelColor=171717&color=FF570A&link=https%3A%2F%2Fcoderabbit.ai&label=CodeRabbit+Reviews)


## 🛠 Technologies & Stack
- **Programming Language:** Java 21
- **Testing Framework:**  TestNG
- **Automation Library:** Selenium WebDriver
- **Dependency Management:** Maven
- **Other Tools:**
  - WebDriverManager – for automatic WebDriver management
  - Allure – for test reporting

## 📋 Project Structure
```
📂 src
├── 📂 main
│   ├── 📂 java
│   │   ├── 📂 com
│   │   │   ├── 📂 coffeecart
│   │   │   │   ├── 📂 ui
│   │   │   │   │   ├── 📂 pages        # POM classes
│   │   │   │   │   ├── 📂 components   # UI components
│   │   │   │   │   ├── 📂 elements     # Web elements
│   │   │   │   │   ├── 📂 modal        # Ui modal windows
├── 📂 test
│   ├── 📂 java
│   │   ├── 📂 com
│   │   │   ├── 📂 coffeecart
│   │   │   │   ├── 📂 ui             # UI tests
│   │   │   │   ├── 📂 utils          # Test utilities
│   ├── 📂 resources                 # Test resources
└── 📄 pom.xml                       # Maven dependencies
 ```

## 🔧 Installation & Setup
### 1️⃣ Clone the repository
```
git clone https://github.com/UA-1378-TAQC/coffeecart.taqc.git
cd coffeecart.taqc
```

create in `src/test/resources` file  `config.properties`

```properties
base.ui.url=https://coffee-cart.app/
implicitlyWait=10


user.email=${USER_EMAIL}
user.name=${USER_NAME}
```
### 2️⃣ Install dependencies
Run the following command to install all necessary dependencies:
```
mvn clean install
```
### 3️⃣ Run tests
Run all tests:
```
mvn test
```
Run a specific test
```
mvn -Dtest=TestClassName test
```
### 🏗 CI/CD Integration
Tests can be executed automatically via GitHub Actions on every commit.
Test results and logs can be found in the CI/CD pipeline output.
Allure reports are generated after test execution for better visibility.

### 📊 Generate Allure Report
To view test reports in Allure, run:
```
allure serve target/allure-results
```
