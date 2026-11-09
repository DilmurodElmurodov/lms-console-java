# LMS Console — Learning Management System in Core Java

A dependency-free learning management system that runs entirely in the terminal.
Built with plain Java (no frameworks, no build tools required) to demonstrate
clean layered architecture, OOP design and in-memory data modelling.

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white)
![Dependencies](https://img.shields.io/badge/Dependencies-none-brightgreen)

## Features

- **Students & Teachers** — registration, listing and management with role-based menus
- **Subjects** — curriculum management linked to teachers
- **Attendance** — per-student, per-subject attendance tracking with statuses
- **Grades** — grade recording and lookup
- **Reports** — aggregated reporting across students, subjects and attendance
- **Data seeding** — demo data generated on startup so every menu is explorable immediately

## Architecture

```
src/lms
├── Main.java          # entry point
├── model/             # domain entities: User, Student, Teacher, Subject, Grade, Attendance, Role
├── repository/        # generic Repository<T> + typed in-memory repositories
├── service/           # business logic + LMSContext (composition root)
├── ui/                # console menus (main, student, teacher, subject, grade, attendance, report)
└── util/              # console I/O helpers, demo data seeder
```

The layers mirror a typical backend service: `ui → service → repository → model`.
`LMSContext` wires the object graph by hand — the same inversion-of-control idea
a DI container automates.

## Run it

Requires any JDK 17+. No build tool needed.

```bash
git clone https://github.com/DilmurodElmurodov/lms-console-java.git
cd lms-console-java

# compile
javac -d out $(find src -name "*.java")

# run
java -cp out lms.Main
```

Or simply open the folder in IntelliJ IDEA and run `Main`.

## Why no framework?

This project intentionally stays framework-free to show the fundamentals that
frameworks build on: layered design, single-responsibility classes, generic
repositories, enum-driven state, and a composition root. For my Spring Boot
work, see [FlowForge](https://github.com/DilmurodElmurodov/flowforge),
[FleetView](https://github.com/DilmurodElmurodov/fleetview),
[Caffelito Coffee](https://github.com/DilmurodElmurodov/caffelito-coffee) and
[FinVault](https://github.com/DilmurodElmurodov/finvault).

## License

MIT
