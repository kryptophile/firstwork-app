package kanishka.firstwork.firstwork_app.enumeration;

public enum InitRules {
    USER_AGE_LESS_THAN_EQUALS_30_COUNTRY_INDIA("Age <= 30 and country == India", "{\n" +
            "  \"rules\": [\n" +
            "    {\n" +
            "      \"rules\": [],\n" +
            "      \"expression\": {\n" +
            "        \"fieldName\": \"User.age\",\n" +
            "        \"operator\": \"<=\",\n" +
            "        \"value\": \"30\"\n" +
            "      },\n" +
            "      \"linkToPrev\": \"&&\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"rules\": [],\n" +
            "      \"expression\": {\n" +
            "        \"fieldName\": \"User.country\",\n" +
            "        \"operator\": \"==\",\n" +
            "        \"value\": \"India\"\n" +
            "      },\n" +
            "      \"linkToPrev\": \"&&\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"expression\": {},\n" +
            "  \"linkToPrev\": \"&&\"\n" +
            "}"),
    CONTRACT_START_END_IN_RANGE_PLACEHOLDER("Contracts start and end in range", "{\n" +
            "  \"rules\": [\n" +
            "    {\n" +
            "      \"rules\": [],\n" +
            "      \"expression\": {\n" +
            "        \"fieldName\": \"Contract.startDate\",\n" +
            "        \"operator\": \">=\",\n" +
            "        \"value\": \"{ {startDate} }\"\n" +
            "      },\n" +
            "      \"linkToPrev\": \"&&\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"rules\": [],\n" +
            "      \"expression\": {\n" +
            "        \"fieldName\": \"Contract.endDate\",\n" +
            "        \"operator\": \"<=\",\n" +
            "        \"value\": \"{ {endDate} }\"\n" +
            "      },\n" +
            "      \"linkToPrev\": \"&&\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"expression\": {},\n" +
            "  \"linkToPrev\": \"&&\"\n" +
            "}"),
    CONTRACT_ACTIVE_THROUGHOUT_RANGE("Contract start before startDate and end after endDate", "{\n" +
            "  \"rules\": [\n" +
            "    {\n" +
            "      \"rules\": [],\n" +
            "      \"expression\": {\n" +
            "        \"fieldName\": \"Contract.startDate\",\n" +
            "        \"operator\": \"<\",\n" +
            "        \"value\": \"{ {startDate} }\"\n" +
            "      },\n" +
            "      \"linkToPrev\": \"&&\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"rules\": [],\n" +
            "      \"expression\": {\n" +
            "        \"fieldName\": \"Contract.endDate\",\n" +
            "        \"operator\": \">\",\n" +
            "        \"value\": \"{ {endDate} }\"\n" +
            "      },\n" +
            "      \"linkToPrev\": \"&&\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"expression\": {},\n" +
            "  \"linkToPrev\": \"&&\"\n" +
            "}"),
    USER_COMPLEX("(age > 30 and country == US) or ( (age < 30 and ( country == India or US) ) )", "{\n" +
            "  \"rules\": [\n" +
            "    {\n" +
            "      \"rules\": [\n" +
            "        {\n" +
            "          \"rules\": [],\n" +
            "          \"expression\": {\n" +
            "            \"fieldName\": \"User.age\",\n" +
            "            \"operator\": \">\",\n" +
            "            \"value\": \"30\"\n" +
            "          },\n" +
            "          \"linkToPrev\": \"&&\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"rules\": [],\n" +
            "          \"expression\": {\n" +
            "            \"fieldName\": \"User.country\",\n" +
            "            \"operator\": \"==\",\n" +
            "            \"value\": \"US\"\n" +
            "          },\n" +
            "          \"linkToPrev\": \"&&\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"expression\": {},\n" +
            "      \"linkToPrev\": \"&&\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"rules\": [\n" +
            "        {\n" +
            "          \"rules\" : [],\n" +
            "          \"expression\" : {\n" +
            "            \"fieldName\": \"User.age\",\n" +
            "            \"operator\": \"<\",\n" +
            "            \"value\": \"30\"\n" +
            "          },\n" +
            "          \"linkToPrev\" : \"&&\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"rules\": [\n" +
            "            {\n" +
            "              \"rules\": [],\n" +
            "              \"expression\": {\n" +
            "                \"fieldName\": \"User.country\",\n" +
            "                \"operator\": \"==\",\n" +
            "                \"value\": \"US\"\n" +
            "              },\n" +
            "              \"linkToPrev\": \"&&\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"rules\": [],\n" +
            "              \"expression\": {\n" +
            "                \"fieldName\": \"User.country\",\n" +
            "                \"operator\": \"==\",\n" +
            "                \"value\": \"India\"\n" +
            "              },\n" +
            "              \"linkToPrev\": \"||\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"expression\": {},\n" +
            "          \"linkToPrev\": \"&&\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"expression\": {},\n" +
            "      \"linkToPrev\": \"||\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"expression\": {},\n" +
            "  \"linkToPrev\": \"&&\"\n" +
            "}"),
    USER_PLACEHOLDER_AGE_GREATER_COUNTRY_EQUAL("age > placeholder_age && country == placeholder_country", "{\n" +
            "  \"rules\": [\n" +
            "    {\n" +
            "      \"rules\": [],\n" +
            "      \"expression\": {\n" +
            "        \"fieldName\": \"User.age\",\n" +
            "        \"operator\": \">\",\n" +
            "        \"value\": \"{ {age} }\"\n" +
            "      },\n" +
            "      \"linkToPrev\": \"&&\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"rules\": [],\n" +
            "      \"expression\": {\n" +
            "        \"fieldName\": \"User.country\",\n" +
            "        \"operator\": \"==\",\n" +
            "        \"value\": \"{ {country} }\"\n" +
            "      },\n" +
            "      \"linkToPrev\": \"&&\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"expression\": {},\n" +
            "  \"linkToPrev\": \"&&\"\n" +
            "}"),
    COMPANY_INDUSTRY_EQUALS_LOCATION_NOT_EQUALS("industry == placeholder_industry && location != placeholder_location", "{\n" +
            "  \"rules\": [\n" +
            "    {\n" +
            "      \"rules\": [],\n" +
            "      \"expression\": {\n" +
            "        \"fieldName\": \"Company.industry\",\n" +
            "        \"operator\": \"==\",\n" +
            "        \"value\": \"{ {industry} }\"\n" +
            "      },\n" +
            "      \"linkToPrev\": \"&&\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"rules\": [],\n" +
            "      \"expression\": {\n" +
            "        \"fieldName\": \"Company.location\",\n" +
            "        \"operator\": \"!=\",\n" +
            "        \"value\": \"{ {location} }\"\n" +
            "      },\n" +
            "      \"linkToPrev\": \"&&\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"expression\": {},\n" +
            "  \"linkToPrev\": \"&&\"\n" +
            "}");


    public final String name;
    public final String ruleJson;

    InitRules(String name, String ruleJson){
        this.name = name;
        this.ruleJson = ruleJson;
    }
}
