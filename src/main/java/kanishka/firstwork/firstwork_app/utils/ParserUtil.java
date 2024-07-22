package kanishka.firstwork.firstwork_app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import kanishka.firstwork.firstwork_app.domain.Expression;
import kanishka.firstwork.firstwork_app.domain.RuleJsonBean;
import kanishka.firstwork.firstwork_app.enumeration.Constants;
import kanishka.firstwork.firstwork_app.enumeration.DataFields;
import kanishka.firstwork.firstwork_app.enumeration.Models;
import kanishka.firstwork.firstwork_app.enumeration.Operator;
import kanishka.firstwork.firstwork_app.exception.InvalidRuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParserUtil {

    static Logger logger = LoggerFactory.getLogger(ParserUtil.class);

    public static Map<String, String> getMapForFieldName(String fieldName){
        String []parts = fieldName.split("\\.");
        Map<String, String> map = new HashMap<>();
        map.put(Constants.MODEL_CONSTANT.value, parts[0]);
        map.put(Constants.FIELD_NAME_CONSTANT.value, parts[1]);
        return map;
    }

    public static Models getModelFromRuleJson(RuleJsonBean ruleJsonBean){

        Expression expression = ruleJsonBean.getExpression();
        if(!expression.isEmpty()){
            String fieldName = expression.getFieldName();
            Map<String, String> map = ParserUtil.getMapForFieldName(fieldName);

            String model = map.get(Constants.MODEL_CONSTANT.value);
            return Models.getMatchingModel(model).get();
        }

        return getModelFromRuleJson(ruleJsonBean.getRules().get(0));
    }

    public static RuleJsonBean getRuleJsonBeanFromString(String jsonStr) throws InvalidRuleException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonStr, RuleJsonBean.class);
        }catch (Exception e){
            logger.error("Invalid Json format" + jsonStr);
            throw new InvalidRuleException(e.getMessage() + " json format incorrect "+ jsonStr);
        }
    }

    public static RuleJsonBean populatePlaceHolders(RuleJsonBean ruleJsonBean, Map<String, String > placeholderMap){
        Expression expression = ruleJsonBean.getExpression();
        if(!expression.isEmpty()){
            String placeholder;
            for(Map.Entry<String, String> entry : placeholderMap.entrySet()){
                placeholder = "{ {" + entry.getKey() + "} }";
                expression.setValue(expression.getValue().toString().replace(placeholder, entry.getValue()));
            }
        }
        for(RuleJsonBean ruleJsonBean1 : ruleJsonBean.getRules()){
            populatePlaceHolders(ruleJsonBean1, placeholderMap);
        }
        return ruleJsonBean;
    }

    public static RuleJsonBean populateValues(RuleJsonBean ruleJsonBean) throws InvalidRuleException {

        if(Objects.nonNull(ruleJsonBean.getExpression()) && !ruleJsonBean.getExpression().isEmpty())
            ruleJsonBean.setExpression(populateExpressionValues(ruleJsonBean.getExpression()));
        List<RuleJsonBean> parsedRules = new ArrayList<>();
        for(RuleJsonBean rule : ruleJsonBean.getRules()){
            parsedRules.add(populateValues(rule));
        }
        ruleJsonBean.setRules(parsedRules);
        return ruleJsonBean;
    }

    private static Expression populateExpressionValues(Expression expression) throws InvalidRuleException {

        Expression expression1 = new Expression();

        String modelFieldName = expression.getFieldName();
        Map<String, String> map = ParserUtil.getMapForFieldName(modelFieldName);

        String fieldName = map.get(Constants.FIELD_NAME_CONSTANT.value);
        String model =map.get(Constants.MODEL_CONSTANT.value);

        DataFields dataFields = DataFields.getMatchingField(fieldName, Models.getMatchingModel(model).get());

        switch (dataFields.dataType){
            case INTEGER :
                expression1.setValue(Integer.parseInt(expression.getValue().toString()));
                break;
            case LONG:
                expression1.setValue(Long.parseLong(expression.getValue().toString()));
                break;
            case DATE:
                try {
                    expression1.setValue(parseStringToDate(expression.getValue().toString()));
                }catch (ParseException e){
                    logger.error("Invalid Date format");
                    throw new InvalidRuleException(e.getMessage());
                }
                break;
            case STRING:
                expression1.setValue(expression.getValue().toString());
        }
        expression1.setOperator(expression.getOperator());
        expression1.setFieldName(expression.getFieldName());
        return expression1;
    }

    public static Date parseStringToDate(String d) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.YYY_MM_DD.value, Locale.ENGLISH);
        return formatter.parse(d);
    }
}
