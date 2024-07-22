package kanishka.firstwork.firstwork_app.service;

import kanishka.firstwork.firstwork_app.domain.RuleJsonBean;
import kanishka.firstwork.firstwork_app.dto.RuleDto;
import kanishka.firstwork.firstwork_app.exception.InvalidRuleException;

import java.util.List;
import java.util.Map;

public interface RuleService {

    RuleDto createRule(RuleDto ruleDto) throws InvalidRuleException;

    List<Object> execute(RuleJsonBean ruleJsonBean) throws InvalidRuleException;

    List<Object> executeByRuleName(String ruleName) throws InvalidRuleException;

    List<Object> executeRuleWithPlaceHolders(String ruleName, Map<String, String> placeholderMap)
            throws InvalidRuleException;

    RuleDto getRuleById(Long id);

    Map<Object, List<Object>> executeRuleWithPlaceHoldersAndTuples(String ruleName, List<Map<String,
            String>> tuples) throws InvalidRuleException;

    RuleDto updateRuleByName(RuleDto ruleDto) throws InvalidRuleException;
}
