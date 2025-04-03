 # 📝 CLI To-Do List Manager (Java)

A command-line based To-Do List Manager written in Java. Supports multiple user accounts with login/registration, secure task saving using Base64 encoding, categorized task views, and persistent data storage in user-specific directories.

## Purpose
This project is created as part of the **Java Programming Lab** coursework for the **2nd year of Computer Science Engineering**. It demonstrates the application of core Java concepts including file handling, object-oriented programming, collections, user input handling, and modular code structure in a practical and meaningful project.

It aims to:

- Build real-world logic using Java.
- Practice and apply file I/O, class structures, and object creation.
- Gain hands-on experience with encapsulation, static data, and modular coding.
- Understand how to create and manage user sessions and persistent data.

## ❓ Why No GUI or Database?

This project intentionally avoids using GUI frameworks or databases to maintain **simplicity and focus on core Java concepts**. The goal is to align with the **lab-level expectations** for Java coursework and ensure the project remains:

- **Easy to compile and test** in any basic Java setup.
- **Understandable for evaluation** by lab instructors.
- **Focused on file handling, logic building, and OOP principles**, which are key to learning Java fundamentals.
- Not overly complex, so that the emphasis remains on **code clarity, correctness, and basic feature implementation** rather than full-stack development.

> This approach keeps the project appropriate for grading and learning outcomes in a 2nd-year college setting.


## 🚀 Features

- ✅ **Login & Registration System**
    - Username & password validation.
    - User data stored in `users.txt`.
    - Each user gets a separate data folder.

- 📂 **Persistent Data Storage**
    - Each user has their own `data.txt` (for tasks) and `counter.txt` (task ID tracking).
    - Tasks are saved in Base64-encoded format for basic obfuscation.

- 📋 **Task Management**
    - Add, view, delete, and mark tasks as complete/incomplete.
    - Categorized into Urgent, Business, and Personal.
    - Tasks have auto-incremented IDs and optional due dates.

- 🔐 **Data Encryption**
    - All tasks are saved using Base64 encoding.

- 👥 **Multi-user Support**
    - Switch between users without restarting the program.
- 📳 **Modular Structure**
  - 🔎 How it Reflects Modularity
    - Separation of Concerns
    We’ve kept UI (menu) logic in App.java, business logic in Data.java, data model in Task.java, and login logic in User.java.

    - Logical Grouping
Each class has a single responsibility, which is one of the key principles of modular design (SRP – Single Responsibility Principle).

    - File Handling is Abstracted
    We use User.java and App.java to manage file creation and login logic without bloating other classes.


## 📦 How to Run

### 1. Compile the project:
```bash
javac *.java
```
### 2. Run the application:
```bash
java App
```
#### Optionally package into a JAR file:
```bash
jar cfe ToDoListApp.jar App *.class
java -jar ToDoListApp.jar
```

## 🛡️ Requirements 
- Java 17+
- Terminal or Command Prompt

## 🧠 Concepts Used
- Java OOP (Encapsulation, Static methods)
- File I/O (BufferedReader, BufferedWriter)
- Base64 Encoding/Decoding
- Collections API (ArrayList, HashMap)
- User input with Scanner
- Logger API for error reporting

## ✍️ Authors
- Aditya Negi
- Aaryan Singh Rathore
- Archit Pratap Singh
- Arjun Narayan

