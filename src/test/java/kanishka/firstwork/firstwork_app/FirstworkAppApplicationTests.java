package kanishka.firstwork.firstwork_app;

import kanishka.firstwork.firstwork_app.enumeration.DataFields;
import kanishka.firstwork.firstwork_app.enumeration.InitRules;
import kanishka.firstwork.firstwork_app.exception.InvalidRuleException;
import kanishka.firstwork.firstwork_app.model.Rule;
import kanishka.firstwork.firstwork_app.model.User;
import kanishka.firstwork.firstwork_app.repository.TestRuleRepository;
import kanishka.firstwork.firstwork_app.repository.TestUserRepository;
import kanishka.firstwork.firstwork_app.service.RuleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FirstworkAppApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private TestUserRepository testUserRepository;

	@Autowired
	private TestRuleRepository testRuleRepository;

	@Autowired
	private RuleService ruleService;

	@BeforeAll
	public static void init(){
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setup(){
		baseUrl = baseUrl.concat(":").concat(port+"").concat("/");
	}

	@Test
	public void testAddUser(){
		User user1 = new User("User1", 25, "India");
		User user2 = new User("User2", 35, "India");
		User response1 = restTemplate.postForObject(baseUrl + "/api/user" , user1, User.class);
		User response2 = restTemplate.postForObject(baseUrl + "/api/user" , user2, User.class);

		List<User> users = new ArrayList<>();
		testUserRepository.findAll().forEach(user -> users.add(user));
		assertEquals("User1", response1.getName());
		assertEquals(2, users.size());
	}

	@Test
	public void testAddRule(){
		Rule rule = new Rule( "Rule1",
				InitRules.USER_AGE_LESS_THAN_EQUALS_30_COUNTRY_INDIA.ruleJson);
		Rule response = restTemplate.postForObject(baseUrl + "/api/rule" , rule, Rule.class);

		assertEquals(InitRules.USER_AGE_LESS_THAN_EQUALS_30_COUNTRY_INDIA.ruleJson, response.getRuleJson());
	}

	@Test
	public void testAddRuleRequestParam(){
		ResponseEntity<Rule> entity = new ResponseEntity(HttpStatus.CREATED);
		restTemplate.exchange(baseUrl + "/api/rule/createWithParams?ruleName={ruleName}&ruleJson={ruleJson}" ,
				HttpMethod.POST,
				entity,
				Rule.class,
				"Rule1",
				InitRules.USER_AGE_LESS_THAN_EQUALS_30_COUNTRY_INDIA.ruleJson
			);

		Specification<Rule> specification = (root, query, criteriaBuilder) -> criteriaBuilder
				.equal(root.get(DataFields.RULE_NAME.value), "Rule1");
		assertEquals(1, testRuleRepository.findAll(specification).size());
	}

	@Test
	public void testRuleByName() throws InvalidRuleException {
		String ruleJson = "{\n" +
				"  \"rules\": [\n" +
				"    {\n" +
				"      \"rules\": [],\n" +
				"      \"expression\": {\n" +
				"        \"fieldName\": \"User.age\",\n" +
				"        \"operator\": \"<\",\n" +
				"        \"value\": \"33\"\n" +
				"      },\n" +
				"      \"linkToPrev\": \"&&\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"rules\": [],\n" +
				"      \"expression\": {\n" +
				"        \"fieldName\": \"User.country\",\n" +
				"        \"operator\": \"!=\",\n" +
				"        \"value\": \"India\"\n" +
				"      },\n" +
				"      \"linkToPrev\": \"&&\"\n" +
				"    }\n" +
				"  ],\n" +
				"  \"expression\": {},\n" +
				"  \"linkToPrev\": \"&&\"\n" +
				"}";
		ResponseEntity<Rule> entity = new ResponseEntity(HttpStatus.CREATED);
		restTemplate.exchange(baseUrl + "/api/rule/createWithParams?ruleName={ruleName}&ruleJson={ruleJson}" ,
				HttpMethod.POST,
				entity,
				Rule.class,
				"Age < 33 && country != India",
				ruleJson
		);

		List<User> users = new ArrayList<>();
		users.add(new User("User1", 25, "India"));
		users.add(new User("User1", 26, "US"));
		users.add(new User("User1", 28, "India"));
		users.add(new User("User1", 30, "US"));
		users.add(new User("User1", 32, "Australia"));
		users.add(new User("User1", 33, "India"));
		users.add(new User("User1", 35, "India"));

		for(User user : users){
			restTemplate.postForObject(baseUrl + "/api/user" , user, User.class);
		}

		List<Object> result = ruleService.executeByRuleName("Age < 33 && country != India");

		//Other users also created in Loader class
		assertEquals(7, result.size());
	}

	@Test
	public void testTupleRule() throws InvalidRuleException {
		List<Map<String, String>> tuples = new ArrayList<>();

		/**
		 * Data loader created contracts and date ranges
		 * 2010 - 2014 --> User1, user2 * company1, company2, company3
		 * 2013 - 2018 --> User3, user4, user5 * company2, company3, company4
		 * 2017 - 2022 --> User1, user 6 * company1, company2
		 * 2023 - inf --> user 2, user3, user 7 * company 2, company3
		 *
		 * active contracts during date ranges
		 * 2010 ----- 2013 ---- 2014 ----- 2017 ----- 2018 -----2022 ------ 2023 ---- inf
		 *        6         15        9           13        4         0            6
		 *  +6         +9         -6        +4          -9       -4           +6
		 */

		//Contracts which started and ended in ranges
		Map<String, String> map1 = new HashMap<>();
		map1.put(DataFields.CONTRACT_START_DATE.value, "2009-06-11");
		map1.put(DataFields.CONTRACT_END_DATE.value, "2015-06-11");


		Map<String, String> map2 = new HashMap<>();
		map2.put(DataFields.CONTRACT_START_DATE.value, "2013-06-11");
		map2.put(DataFields.CONTRACT_END_DATE.value, "2023-06-11");

		Map<String, String> map3 = new HashMap<>();
		map3.put(DataFields.CONTRACT_START_DATE.value, "2012-06-11");
		map3.put(DataFields.CONTRACT_END_DATE.value, "2020-06-11");

		tuples.add(map1);
		tuples.add(map2);
		tuples.add(map3);

		Map<Object, List<Object>> result = ruleService.executeRuleWithPlaceHoldersAndTuples(
				InitRules.CONTRACT_START_END_IN_RANGE_PLACEHOLDER.name, tuples);

		assertEquals(6, result.get(map1.toString()).size());
		assertEquals(4, result.get(map2.toString()).size());
		assertEquals(9, result.get(map3.toString()).size());

	}
}
