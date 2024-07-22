package kanishka.firstwork.firstwork_app.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kanishka.firstwork.firstwork_app.domain.RuleJsonBean;
import kanishka.firstwork.firstwork_app.dto.RuleDto;
import kanishka.firstwork.firstwork_app.enumeration.Constants;
import kanishka.firstwork.firstwork_app.enumeration.DataFields;
import kanishka.firstwork.firstwork_app.exception.InvalidRuleException;
import kanishka.firstwork.firstwork_app.service.RuleService;
import kanishka.firstwork.firstwork_app.utils.ParserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.json.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
@RequestMapping("/api/rule")
public class RuleController {

    private RuleService ruleService;

    public RuleController(RuleService ruleService){
        this.ruleService = ruleService;
    }


    @PostMapping
    public ResponseEntity<RuleDto> createRule(@RequestBody RuleDto request) throws Exception{
        return new ResponseEntity<>(ruleService.createRule(request), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RuleDto> updateRule(@RequestParam String ruleName, @RequestBody String ruleJson) throws InvalidRuleException{
        return new ResponseEntity<>(ruleService.updateRuleByName(new RuleDto(ruleName, ruleJson)), HttpStatus.OK);
    }

    @PostMapping("/createWithParams")
    public ResponseEntity<RuleDto> createRuleWithParams(@RequestParam String ruleName, @RequestParam String ruleJson)
            throws Exception{
        return new ResponseEntity<>(ruleService.createRule(new RuleDto(ruleName, ruleJson)), HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<RuleDto> createRule(@RequestParam String ruleName, @RequestBody String ruleJson)
            throws Exception{
        return new ResponseEntity<>(ruleService.createRule(new RuleDto(ruleName, ruleJson)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleDto> getRule(@PathVariable Long id) throws Exception{
        return new ResponseEntity<>(ruleService.getRuleById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Object>> executeRule(@RequestBody String request) throws InvalidRuleException, Exception {
        String jsonStr = request;
        RuleJsonBean ruleJsonBean = ParserUtil.getRuleJsonBeanFromString(jsonStr);
        return new ResponseEntity<>(ruleService.execute(ruleJsonBean), HttpStatus.OK);
    }

    @GetMapping("/executeRuleByRuleName")
    public ResponseEntity<List<Object>> executeRuleByRuleName(@RequestParam String ruleName)
            throws InvalidRuleException {

        return new ResponseEntity<>(ruleService.executeByRuleName(ruleName), HttpStatus.OK);
    }

    @GetMapping("/executeRuleWithPlaceHolders")
    public ResponseEntity<Object> executeRuleWithPlaceHolders(@RequestParam String ruleName,
                                                              @RequestBody Map<String, String> placeholderMap)
            throws InvalidRuleException{

        return new ResponseEntity<>(ruleService.executeRuleWithPlaceHolders(ruleName, placeholderMap), HttpStatus.OK);
    }

    @GetMapping("/executeRuleWithPlaceHoldersAndTuples")
    public ResponseEntity<Map<Object, List<Object>>> executeRuleWithPlaceHoldersAndTuples(@RequestParam String ruleName,
                                                                                               @RequestBody List<Map<String, String>> tuples)
            throws InvalidRuleException {

        return new ResponseEntity<>(ruleService.executeRuleWithPlaceHoldersAndTuples(ruleName, tuples), HttpStatus.OK);
    }
}
