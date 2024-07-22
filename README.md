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

Provide step-by-step instructions on how to install and get your project running.

```bash
# Example installation steps
git clone https://github.com/username/project.git
cd project
npm install
```

## Usage

Provide examples and instructions for using your project. Show how to use any main features with code examples.

```bash
# Example usage
command-line-tool --option value
```

## Contributing

Explain how people can contribute to your project. Include guidelines for:
- How to report bugs
- How to suggest enhancements
- How to contribute code via pull requests

## License

Specify under what license your code is distributed. For example:

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

## Contact

Provide your contact information if users want to reach out with questions or feedback.

---

Replace the placeholder sections (`Project Name`, `About`, `Features`, etc.) with your project-specific information. Customize the sections and content to best describe your project and provide clear, concise information for users and potential contributors.

### Additional Tips

- **Formatting**: Use Markdown syntax for headings, lists, code blocks, links, and formatting to improve readability.
- **Images and GIFs**: Consider adding screenshots, diagrams, or GIFs to visually demonstrate your project.
- **Badges**: Include badges for build status, version, license, etc., if applicable.
- **Update**: Keep your README.md file updated as your project evolves.

By following this template and customizing it to fit your project, you can create a README.md file that effectively communicates your project's purpose, usage instructions, and how others can contribute or get involved.