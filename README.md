# SimpleTodoApp

This repository was created for team member education purposes.
It is a simple todo management application.

React is used as the front-end.
The development language is Typescript.
SpringBoot is used as the backend.
The development language is Java.

It requires the creation of a postgresql DB to operate.
Please create todo_db and todo_db_test.

## Setup

1. Create db

    ```shell
    createdb todo_db
    createdb todo_db_test
    ```

1. Set environment variables

    ```shell
   export DB_USER=(DB_USER)
   export DB_PASSWORD=(DB_PASSWORD)
    ```
1. Launch App

   client app
   ```shell
   cd client
   npm install
   npm run start
   ```
   
   server app
   ```shell
   cd server
   SERVER_PORT=8080 ./gradlew bootRun
   ```
