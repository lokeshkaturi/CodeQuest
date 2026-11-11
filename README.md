# CodeQuest

Java 17 + Maven WAR + Jakarta Servlet 6 + JSP-ready app + MySQL, designed for Apache Tomcat 10.1.

## Features
- Student registration and login (new passwords stored with BCrypt)
- Student dashboard with XP and level
- Java / SQL / DSA multiple-choice missions
- Correct-answer submissions and XP awards
- Admin login and mission creation
- MySQL schema included in `database/codequest_db.sql`

## Requirements
- JDK 17
- Maven 3.9+
- MySQL 8+
- Apache Tomcat 10.1+
- Eclipse IDE for Enterprise Java and Web Developers (optional)

## 1. Create the database
Open MySQL Workbench, open `database/codequest_db.sql`, and execute the full script.
It creates `codequest_db`, four tables, a demo admin and two demo missions.

Demo admin: `admin` / `Admin@123`. Change it before real use. This seed password is plain text for demo compatibility; student registrations use BCrypt. For production, use hashed admin passwords too.

## 2. Configure database connection
Defaults are:
- URL: `jdbc:mysql://localhost:3306/codequest_db?useSSL=false&serverTimezone=UTC`
- User: `root`
- Password: empty

If your MySQL password is not empty, set environment variable `CODEQUEST_DB_PASSWORD` to your actual password. Optional variables: `CODEQUEST_DB_URL`, `CODEQUEST_DB_USER`.

Windows PowerShell example for current terminal:
```powershell
$env:CODEQUEST_DB_USER="root"
$env:CODEQUEST_DB_PASSWORD="your_mysql_password"
```

## 3. Build and run
From the project folder:
```bash
mvn clean package
```
Copy `target/CodeQuest.war` to Tomcat's `webapps` folder, start Tomcat, then open:
`http://localhost:8080/CodeQuest/`

Or in Eclipse:
1. File → Import → Existing Maven Projects → choose this project folder.
2. Right-click project → Maven → Update Project.
3. Add Apache Tomcat 10.1 in Servers view.
4. Add CodeQuest to Tomcat and start the server.
5. Open `http://localhost:8080/CodeQuest/`.

## 4. Pages
- `/register` — student registration
- `/login` — student login
- `/dashboard` — student XP / level
- `/missions` — mission list
- `/admin/login` — admin login
- `/admin/missions` — create missions after admin login
- `/logout` — sign out

## 5. Push to GitHub
Create an **empty** repository on GitHub named `CodeQuest` (do not add README, license, or .gitignore if this project already has them). Then run these commands in a terminal opened in the project folder:
```bash
git init
git add .
git commit -m "Initial CodeQuest project"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/CodeQuest.git
git push -u origin main
```
Replace `YOUR_USERNAME` with your GitHub username. If `origin` already exists:
```bash
git remote -v
git remote set-url origin https://github.com/YOUR_USERNAME/CodeQuest.git
git push -u origin main
```

## Notes
- This starter is intended for local learning/demo use. Before deployment, add CSRF protection, rate limiting, stronger session security, admin password hashing, and configuration through secure environment variables.
- Do not commit real passwords or database credentials.
