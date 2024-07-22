package kanishka.firstwork.firstwork_app.enumeration;

import org.springframework.boot.autoconfigure.jackson.JacksonProperties;

import java.util.EnumSet;
import java.util.Optional;
import java.util.stream.Stream;

import static kanishka.firstwork.firstwork_app.enumeration.Models.*;

public enum DataFields {
    USER_AGE("age", MODEL_USER, Constants.INTEGER),
    USER_NAME("name", MODEL_USER, Constants.STRING),
    USER_COUNTRY("country", MODEL_USER, Constants.STRING),
    COMPANY_NAME("name", MODEL_COMPANY, Constants.STRING),
    COMPANY_INDUSTRY("industry", MODEL_COMPANY, Constants.STRING),
    COMPANY_LOCATION("location", MODEL_COMPANY, Constants.STRING),
    CONTRACT_USER("user", MODEL_CONTRACT, Constants.LONG),
    CONTRACT_COMPANY("company", MODEL_CONTRACT, Constants.LONG),
    CONTRACT_START_DATE("startDate", MODEL_CONTRACT, Constants.DATE),
    CONTRACT_END_DATE("endDate", MODEL_CONTRACT, Constants.DATE),
    RULE_NAME("name", MODEL_RULE, Constants.STRING),
    RULE_JSON("ruleJson", MODEL_RULE, Constants.STRING);


    public final String value;
    public final Models model;
    public final Constants dataType;

    DataFields(String value, Models model, Constants datType){
        this.value = value;
        this.model = model;
        this.dataType = datType;
    }

    public static DataFields getMatchingField(String fieldName, Models model){
        Optional<DataFields> result = null;
        for(DataFields  dataFields : EnumSet.allOf(DataFields.class)){
            if(dataFields.value.equals(fieldName) && dataFields.model.equals(model))
                return dataFields;
        }
        return null;
    }

    public static Constants getDataType(String fieldName, String m){
        Models model = Models.getMatchingModel(m).get();
        DataFields dataFields = getMatchingField(fieldName, model);
        return dataFields.dataType;
    }
}
