package kanishka.firstwork.firstwork_app.mapper;

import kanishka.firstwork.firstwork_app.dto.RuleDto;
import kanishka.firstwork.firstwork_app.model.Rule;

public class RuleMapper {
    public static Rule mapToRule(RuleDto ruleDto){
        return new Rule(
                ruleDto.getName(),
                ruleDto.getRuleJson()
        );
    }

    public static RuleDto mapToRuleDto(Rule rule){
        return new RuleDto(rule.getName(),
                rule.getRuleJson());
    }
}
