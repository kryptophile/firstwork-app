package kanishka.firstwork.firstwork_app.validator;

import kanishka.firstwork.firstwork_app.domain.Expression;
import kanishka.firstwork.firstwork_app.domain.RuleJsonBean;
import kanishka.firstwork.firstwork_app.enumeration.Constants;
import kanishka.firstwork.firstwork_app.enumeration.DataFields;
import kanishka.firstwork.firstwork_app.enumeration.Models;
import kanishka.firstwork.firstwork_app.enumeration.Operator;
import kanishka.firstwork.firstwork_app.utils.ParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class RuleJsonValidator {
    /**
     * 1. Any rule will have either sibling rule or child. Can't be both
     * 2. validate data fields
     *      - no fields apart from ones specified in models
     *      - fields should not mismatch with models Contract.country invalid
     * 3. Operator linking 2 expression should be OR or AND
     * 4. expression should have valid operator ie. < > == etc. </>(not OR or AND)
     * 5. Placeholder should have name equal to a field
     * 6. Values of expressions should be legal and match their corresponding data types
     */

    static Logger logger = LoggerFactory.getLogger(RuleJsonValidator.class);

    public static Boolean validate(RuleJsonBean ruleJsonBean){
        Boolean result = Boolean.TRUE;

        if(CollectionUtils.isEmpty(ruleJsonBean.getRules()) && ruleJsonBean.getExpression().isEmpty())
            return Boolean.FALSE;

        if(!CollectionUtils.isEmpty(ruleJsonBean.getRules()) && !(ruleJsonBean.getExpression().isEmpty()))
            return Boolean.FALSE;

        Optional<Operator> operatorOptional = Operator.getMatchingOperator(ruleJsonBean.getLinkToPrev());

        if(!ruleJsonBean.getLinkToPrev().isEmpty() && !operatorOptional.get().equals(Operator.OR) &&
                !operatorOptional.get().equals(Operator.AND))
            return Boolean.FALSE;

        if(!ruleJsonBean.getExpression().isEmpty())
            result = result && validateExpression(ruleJsonBean.getExpression());

        for(RuleJsonBean rule : ruleJsonBean.getRules()){
            result = result && validate(rule);
        }
        return result;
    }

    private static Boolean validateExpression(Expression expression){
        Optional<Operator> operatorOptional = Operator.getMatchingOperator(expression.getOperator());
        if(operatorOptional.isEmpty())
            return Boolean.FALSE;

        if(operatorOptional.get().equals(Operator.OR) || operatorOptional.get().equals(Operator.AND))
            return Boolean.FALSE;

        //validate field name and values
        String modelFieldName = expression.getFieldName();
        Map<String, String> map = ParserUtil.getMapForFieldName(modelFieldName);

        String fieldName = map.get(Constants.FIELD_NAME_CONSTANT.value);
        String model =map.get(Constants.MODEL_CONSTANT.value);
        if(model.isEmpty() || fieldName.isEmpty() || Models.getMatchingModel(model).isEmpty())
            return Boolean.FALSE;

        DataFields dataFields = DataFields.getMatchingField(fieldName, Models.getMatchingModel(model).get());
        if(Objects.isNull(dataFields))
            return Boolean.FALSE;

        //validate placeholder
        if(isPlaceHolder(expression.getValue().toString())){
            if(validatePlaceholder(expression.getValue().toString(), model))
                return Boolean.TRUE;
            return Boolean.FALSE;
        }

        //validate values if not { {placeholder} }
        try{
            switch (dataFields.dataType){
                case STRING:
                    expression.getValue().toString();
                    break;
                case INTEGER :
                    Integer.parseInt(expression.getValue().toString());
                    break;
                case LONG:
                    Long.parseLong(expression.getValue().toString());
                    break;
                case DATE:
                    SimpleDateFormat formatter = new SimpleDateFormat(Constants.YYY_MM_DD.value, Locale.ENGLISH);
                    formatter.parse(expression.getValue().toString());
            }
        }catch (Exception e){
            logger.error("Invalid Data field value" + dataFields);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public static Boolean isPlaceHolder(String value){
        if(value.length() > 4 && value.startsWith(Constants.PLACEHOLDER_START.value)
                && value.endsWith(Constants.PLACEHOLDER_END.value) )
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    public static Boolean validatePlaceholder(String value, String model){
        value = value.substring(value.indexOf(Constants.PLACEHOLDER_START.value) + 3,
                value.indexOf(Constants.PLACEHOLDER_END.value));
        try {
            DataFields.getMatchingField(value, Models.getMatchingModel(model).get());
        } catch (Exception e) {
            logger.error("Invalid placeholder");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
