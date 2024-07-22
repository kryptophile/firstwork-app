package kanishka.firstwork.firstwork_app.dataload;

import kanishka.firstwork.firstwork_app.dto.RuleDto;
import kanishka.firstwork.firstwork_app.enumeration.DataFields;
import kanishka.firstwork.firstwork_app.enumeration.InitRules;
import kanishka.firstwork.firstwork_app.exception.InvalidRuleException;
import kanishka.firstwork.firstwork_app.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Order(3)
@Component
public class RulesLoader implements ApplicationRunner {

    @Autowired
    RuleService ruleService;

    public RulesLoader(RuleService ruleService){
        this.ruleService = ruleService;
    }

    public void run(final ApplicationArguments args) throws InvalidRuleException {
        for (InitRules rule : EnumSet.allOf(InitRules.class)) {
            ruleService.createRule(new RuleDto(rule.name, rule.ruleJson));
        }
    }
}
