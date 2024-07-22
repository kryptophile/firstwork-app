package kanishka.firstwork.firstwork_app.service.impl;

import kanishka.firstwork.firstwork_app.domain.Expression;
import kanishka.firstwork.firstwork_app.domain.RuleJsonBean;
import kanishka.firstwork.firstwork_app.dto.RuleDto;
import kanishka.firstwork.firstwork_app.enumeration.Constants;
import kanishka.firstwork.firstwork_app.enumeration.DataFields;
import kanishka.firstwork.firstwork_app.enumeration.Models;
import kanishka.firstwork.firstwork_app.enumeration.Operator;
import kanishka.firstwork.firstwork_app.exception.InvalidOperatorException;
import kanishka.firstwork.firstwork_app.exception.InvalidRuleException;
import kanishka.firstwork.firstwork_app.mapper.CompanyMapper;
import kanishka.firstwork.firstwork_app.mapper.ContractMapper;
import kanishka.firstwork.firstwork_app.mapper.RuleMapper;
import kanishka.firstwork.firstwork_app.mapper.UserMapper;
import kanishka.firstwork.firstwork_app.model.Company;
import kanishka.firstwork.firstwork_app.model.Contract;
import kanishka.firstwork.firstwork_app.model.Rule;
import kanishka.firstwork.firstwork_app.model.User;
import kanishka.firstwork.firstwork_app.repository.CompanyRepository;
import kanishka.firstwork.firstwork_app.repository.ContractRepository;
import kanishka.firstwork.firstwork_app.repository.RuleRepository;
import kanishka.firstwork.firstwork_app.repository.UserRepository;
import kanishka.firstwork.firstwork_app.service.RuleService;
import kanishka.firstwork.firstwork_app.utils.ParserUtil;
import kanishka.firstwork.firstwork_app.utils.SpecificationUtils;
import kanishka.firstwork.firstwork_app.validator.RuleJsonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RuleServiceImpl implements RuleService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RuleRepository ruleRepository;

    static Logger logger = LoggerFactory.getLogger(RuleServiceImpl.class);

    public RuleServiceImpl(RuleRepository ruleRepository, UserRepository userRepository, CompanyRepository companyRepository,
                           ContractRepository contractRepository){
        this.ruleRepository = ruleRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public RuleDto createRule(RuleDto ruleDto) throws InvalidRuleException {
        try {
            ParserUtil.getRuleJsonBeanFromString(ruleDto.getRuleJson());
        }catch (InvalidRuleException e){
            logger.error("Invalid json format" + ruleDto.getRuleJson());
            throw e;
        }
        if(!RuleJsonValidator.validate(ParserUtil.getRuleJsonBeanFromString(ruleDto.getRuleJson()))) {
            logger.error("Invalid rule format");
            throw new InvalidRuleException("Invalid rule format");
        }

        Rule rule = RuleMapper.mapToRule(ruleDto);
        rule = ruleRepository.save(rule);
        return RuleMapper.mapToRuleDto(rule);
    }

    @Override
    public List<Object> execute(RuleJsonBean ruleJsonBean) throws InvalidRuleException {

        ruleJsonBean = ParserUtil.populateValues(ruleJsonBean);
        Specification<Object> specification = getSpecification(ruleJsonBean);

        Models model = ParserUtil.getModelFromRuleJson(ruleJsonBean);

        //can use interface/factory but for ease using switch
        switch(model) {
            case MODEL_COMPANY:
                return getCompanyData(specification);
            case MODEL_USER:
                return getUserData(specification);
            case MODEL_CONTRACT:
                return getContractData(specification);
            default:
                logger.error(model + "is not a defined model");
                throw new InvalidRuleException(model + "is not a defined model");
        }
    }

    @Override
    public List<Object> executeByRuleName(String ruleName) throws InvalidRuleException {
        RuleJsonBean ruleJsonBean = getRuleJsonBeanByRuleName(ruleName);
        return execute(ruleJsonBean);
    }

    @Override
    public List<Object> executeRuleWithPlaceHolders(String ruleName, Map<String, String> placeholderMap)
            throws InvalidRuleException {
        RuleJsonBean ruleJsonBean = getRuleJsonBeanByRuleName(ruleName);
        ruleJsonBean = ParserUtil.populatePlaceHolders(ruleJsonBean, placeholderMap);
        return execute(ruleJsonBean);
    }

    @Override
    public RuleDto getRuleById(Long id) {
        RuleDto ruleDto = null;
        Optional<Rule> rule = ruleRepository.findById(id);
        if(rule.isPresent())
            return RuleMapper.mapToRuleDto(rule.get());
        return ruleDto;
    }

    @Override
    public Map<Object, List<Object>> executeRuleWithPlaceHoldersAndTuples(String ruleName, List<Map<String,
            String>> tuples) throws InvalidRuleException{
        Map<Object, List<Object>> result = new HashMap<>();

        for(Map<String, String> placeholders : tuples){
            String key = placeholders.toString();
            List<Object> values = executeRuleWithPlaceHolders(ruleName, placeholders);
            result.put(key, values);
        }
        return result;
    }

    @Override
    public RuleDto updateRuleByName(RuleDto ruleDto) throws InvalidRuleException {
        try {
            Rule rule = getRuleByName(ruleDto.getName());
            rule.setRuleJson(ruleDto.getRuleJson());
            return RuleMapper.mapToRuleDto(ruleRepository.save(rule));
        }catch (InvalidRuleException e){
            throw new InvalidRuleException("Rule not found or invalid rule format for rule" + ruleDto.getName());
        }
    }

    private RuleJsonBean getRuleJsonBeanByRuleName(String ruleName) throws InvalidRuleException {
        try {
            Rule rule = getRuleByName(ruleName);
            return ParserUtil.getRuleJsonBeanFromString(rule.getRuleJson());
        }catch (Exception e){
            throw new InvalidRuleException("Invalid rule" + ruleName);
        }
    }

    private Rule getRuleByName(String ruleName) throws InvalidRuleException {
        List<Rule> rulesDbWithName = ruleRepository.findAll(SpecificationUtils
                .equals(DataFields.RULE_NAME.value, ruleName)).stream().toList();

        if(rulesDbWithName.size() != 1)
            throw new InvalidRuleException("Multiple rules or no rules with name" + ruleName);

        return rulesDbWithName.get(0);
    }

    private List<Object> getCompanyData(Specification specification){
        List<Object> companyList = new ArrayList<>();
        companyRepository.findAll(specification)
                .forEach(x -> companyList.add(CompanyMapper.mapToCompanyDto((Company)x)));
        return companyList;
    }

    private List<Object> getUserData(Specification specification){
        List<Object> userList = new ArrayList<>();
        userRepository.findAll(specification)
                .forEach(x -> userList.add(UserMapper.mapToUserDto((User)x)));
        return userList;
    }

    private List<Object> getContractData(Specification specification){
        List<Object> contractList = new ArrayList<>();
        contractRepository.findAll(specification)
                .forEach(x -> contractList.add(ContractMapper.mapToContractDto((Contract) x)));
        return contractList;
    }

    private Specification<Object> getSpecification(RuleJsonBean ruleJsonBean) throws InvalidRuleException {

        Specification<Object> specificationCurrent = null;

        if(CollectionUtils.isEmpty(ruleJsonBean.getRules())){
            specificationCurrent = getSpecificationFromExpression(ruleJsonBean.getExpression());
        }
        else{

            specificationCurrent = getSpecification(ruleJsonBean.getRules().get(0));
            for(int i = 1; i < ruleJsonBean.getRules().size(); i++){
                RuleJsonBean subRule = ruleJsonBean.getRules().get(i);

                Optional<Operator> connector = Operator.getMatchingOperator(subRule.getLinkToPrev());
                if(connector.isEmpty())
                    throw new InvalidRuleException("Invalid operator " + subRule.getLinkToPrev());

                specificationCurrent = joinSpecification(specificationCurrent,
                        getSpecification(subRule), connector.get());
            }
        }

        return specificationCurrent;
    }

    private Specification<Object> joinSpecification(Specification<Object> specification1,
                                                    Specification<Object> specification2, Operator operator){
        if(Operator.getMatchingOperator(operator.value).isPresent() &&
                Operator.getMatchingOperator(operator.value).get().equals(Operator.OR))
            return specification1.or(specification2);

        if(Operator.getMatchingOperator(operator.value).isPresent() &&
                Operator.getMatchingOperator(operator.value).get().equals(Operator.AND))
            return specification1.and(specification2);

        throw new InvalidOperatorException(operator.value);
    }

    private Specification<Object> getSpecificationFromExpression(Expression expression){
        //fieldName, operator, value

        Map<String, String> parts = ParserUtil.getMapForFieldName(expression.getFieldName());
        Operator operator = Operator.getMatchingOperator(expression.getOperator()).get();
        String fieldName = parts.get(Constants.FIELD_NAME_CONSTANT.value);
        String model = parts.get(Constants.MODEL_CONSTANT.value);


        Specification specification = null;
        switch (DataFields.getDataType(fieldName, model)){
            case INTEGER :
                specification = SpecificationUtils.getSpecificationForOperator( operator,
                        fieldName, convertInstanceOfObject(expression.getValue(), Integer.class) );
                break;
            case DATE :
                specification = SpecificationUtils.getSpecificationForOperator( operator,
                        fieldName, convertInstanceOfObject(expression.getValue(), Date.class) );
                break;
            case LONG:
                specification = SpecificationUtils.getSpecificationForOperator( operator,
                        fieldName, convertInstanceOfObject(expression.getValue(), Long.class) );
                break;
            case STRING:
                specification = SpecificationUtils.getSpecificationForOperator( operator,
                        fieldName, convertInstanceOfObject(expression.getValue(), String.class) );
        }
        return specification;
    }

    public static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
        try {
            return clazz.cast(o);
        } catch(ClassCastException e) {
            return null;
        }
    }
}
