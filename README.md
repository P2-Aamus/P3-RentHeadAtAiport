# P3-RentHeadAtAiport

This system is developed for a semester project.

The system is a self-sufficient renting system for headphones being stored in kiosks
located in multiple airports.

## Installation

### MySQL

This system runs on a local database, called main, mySQL server version 9.4.0 Innovation. 
Furthermore, should the application "MySQL" workbench also be downloaded.

https://dev.mysql.com/downloads/mysql/

https://dev.mysql.com/downloads/workbench/

Since it is a local database, should the database and the tables be downloaded.
The database can be imported with the database compressed in a zip-file: main.sql.zip

### JDK

This project also uses a development kit called BellSoft Liberica 24. 
Download the latest version, with the Full JDK package.

https://bell-sw.com/pages/downloads/#jdk-25-lts

### Maven
This project also uses the archetype Maven, which is a project structure that is implemented in 
IntelliJ.

When selecting the project, choose Maven Archetype on the left side under the generators.

Catalog: internal

Archetype: org.apache.maven.archetypes:maven-archetype-quickstart

Version: 1.4

## Usage

### How to run the project

Run the code inside the UI manager in the org.example.GUI folder.

### Admin page

Run the code in all the classes besides the AbstractDataTable and the Database class in the Admin.GUI folder.

### QR code

Use the attached QR-code, with a fabricated boarding pass and scan the QR-code when the camera is 
displayed.

### MySQL usage
When installed MySQL, is the config.properties file in the resources folder also necessary to configure.

db.url=jdbc:mysql://localhost:3306/main
db.user=root
db.password= *This is your personal SQL password*


### How to change the kiosk location

#### Different scenarios:

##### Pick up a pair of headphones:

Inside the UIManager, on line 23, should the kiosk object have the argument "EKBI".

##### Drop off a pair of headphones:

Inside the UIManager, on line 23, should the kiosk object have the argument "EKCH".

## License

MIT License

Copyright (c) [2025] [Gruppe 3]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE-


