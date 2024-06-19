# NutriAppJava

NutriAppJava is an Android application designed to manage nutritional data effectively. This project provides detailed instructions for setting up and deploying the application using Android Studio.

## Table of Contents
- [About The Project](#about-the-project)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [API Service](#api-service)
- [Endpoints](#endpoints)
- [License](#license)
- [Contact](#contact)

## About The Project

NutriAppJava is tailored for individuals looking to manage their nutritional data through an intuitive mobile application. It allows users to track and analyze their food intake, providing insights into their dietary habits.

## Prerequisites

Before starting with NutriAppJava, ensure you have the following installed:
- [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Android Studio](https://developer.android.com/studio)

## Installation

### Clone the Repository
```sh git clone https://github.com/Miblech/NutriAppJava.git```

```cd NutriAppJava```

### Open in Android Studio

- Launch Android Studio.
- Click on `File` > `Open...`.
- Navigate to the directory where you cloned the repository and select it.
- Click `OK` to open the project.

## Running the Application

### Build and Run

- Ensure an Android device is connected or an emulator is running.
- In Android Studio, click the `Run` button (the green arrow) or select `Run` > `Run 'app'`.
- The application should build and install on your connected device or emulator.

## Project Structure

- `app/src/main/java`: Contains the source code of the application.
- `app/src/main/res`: Houses resource files such as layouts, strings, and images.
- `gradle`: Contains Gradle build files.
- `app/build.gradle`: The build configuration file for the app module.

## API Service

The application communicates with the backend via Retrofit. The `ApiService` interface outlines the API endpoints used for various functionalities like user authentication, food management, and logging nutritional data.

## Endpoints

The application utilizes several API endpoints for different purposes, including retrieving, adding, updating, and deleting food items.

## License

NutriAppJava is distributed under the MIT License. See `LICENSE.txt` for more information.

## Contact

Your Name - @twitter_handle - nombre2727@gmail.com

Project Link: [https://github.com/Miblech/NutriAppJava](https://github.com/Miblech/NutriAppJava)
