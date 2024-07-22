## About Project

Compliance engine to run rules.
Project has been configured to accept rules in JSON format.


---

# Compliance Engine - Kanishka

Brief description of your project.

## Table of Contents

- [Compliance engine](#project-name)
    - [Table of Contents](#table-of-contents)
    - [About](#about)
    - [Features](#features)
    - [Getting Started](#getting-started)
        - [Prerequisites](#prerequisites)
        - [Installation](#installation)
    - [Usage](#usage)
    - [Contributing](#contributing)
    - [License](#license)
    - [Contact](#contact)

## About

Project has been designed to run rules in a generic manner. Rules are binary (field operator value) but multiple rules can be combined in different logical operations.

## How rules are structured

Each rule is a node in a tree and is processed by traversing the tree.
Rule node can have an `expression`, `rules[]` which are child rules and `linkToPrev` which tells how to siblings in tree interact logically.<br />

Expression contains 2 parameters combined by an operator. Below json can be read as
`User.age > 30 && User.country == 'India'` where age and country are placeholders.


Two rules are combined as `rules[0] <-- rules[1]` where `<--` is represented by `linkToPrev` in the json.
Also a rule can have either sibling or child.

eg. `(A and B) OR ( (B and C) AND (D < E) OR (E == A) )`
Here you can see `( (B and C) AND (D < E) OR (E == A))` is a sibling of `(A and B)` </br>
Also, `( (B and C) OR (D < E) OR (E == A))` doesn't have an expression at its level, but it has 2 children which combine to give a result.

Similar operation has been done recursively to compute all logical operations.
```
                                    (<empty>)
                                      OR
                   child rule1    <-----------------         child rule2
                    (A and B)                                (<empty>)
                                                                AND                 OR
                                               child rule2.1  <--- child rule2.2  <---  child rule2.3
                                                 (B and C)           (D < E)              (E == A)
                                                

```

```json
{
  "rules": [
    {
      "rules": [],
      "expression": {
        "fieldName": "User.age",
        "operator": ">",
        "value": "30"
      },
      "linkToPrev": ""
    },
    {
      "rules": [],
      "expression": {
        "fieldName": "User.country",
        "operator": "==",
        "value": "India"
      },
      "linkToPrev": "&&"
    }
  ],
  "expression": {},
  "linkToPrev": ""
}
```

## Features

List the features of your project:

- Create, update rules
- Create, update models (User, Company, Contract)
- Run rules on individual models at any level of tree complexity
- Other APIs which fetch custom data from models eg. `Contracts where Company.Industry == IT`

## Getting Started

### Prerequisites

Project is configured to run on mysql database as storage. It can be configured in `application.properties` file.

- MySQL Db `mysql  Ver 8.0.38`
- IntelliJ as IDE
- JDK 19.0.1
- Maven

### Installation

Follow steps below

1. Go to IntelliJ. `File > Project from version control` </br>
![Screenshot 2024-07-22 221528](https://github.com/user-attachments/assets/f445e8fd-d10f-4e68-a6ed-e8f5b049d75b)
</br>
2. Choose folder to build project on. Paste git url in `URL` field

   
![Screenshot 2024-07-22 221142](https://github.com/user-attachments/assets/5564447d-fad1-425e-889c-59af99a3e897)
</br>

3. Do a Maven build to let your IntelliJ structure all dependencies. </br>
![Screenshot 2024-07-22 221636](https://github.com/user-attachments/assets/b8b5453f-23bc-42c5-a28e-cec283f9ff7f) </br>

4. Configure `application.properties` file. Set your database username, password. Set schema eg. I have set `firstwork` </br>

![Screenshot 2024-07-22 223627](https://github.com/user-attachments/assets/c0c23d31-6aba-4b5e-a475-95232955bbe6)

   
5. Go to `FirstworkAppApplication` and right click and Run.
   </br>
6. Run the endpoints via Postman and explore. </br>
