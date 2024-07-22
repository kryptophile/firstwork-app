package kanishka.firstwork.firstwork_app.service.impl;

import kanishka.firstwork.firstwork_app.dto.ContractDto;
import kanishka.firstwork.firstwork_app.dto.ContractInputDto;
import kanishka.firstwork.firstwork_app.dto.UserDto;
import kanishka.firstwork.firstwork_app.enumeration.DataFields;
import kanishka.firstwork.firstwork_app.mapper.ContractMapper;
import kanishka.firstwork.firstwork_app.mapper.UserMapper;
import kanishka.firstwork.firstwork_app.model.Company;
import kanishka.firstwork.firstwork_app.model.Contract;
import kanishka.firstwork.firstwork_app.model.User;
import kanishka.firstwork.firstwork_app.repository.CompanyRepository;
import kanishka.firstwork.firstwork_app.repository.ContractRepository;
import kanishka.firstwork.firstwork_app.repository.UserRepository;
import kanishka.firstwork.firstwork_app.service.ContractService;
import kanishka.firstwork.firstwork_app.utils.SpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;


    public ContractServiceImpl(ContractRepository contractRepository){
        this.contractRepository = contractRepository;
    }

    @Override
    public ContractDto createContract(ContractInputDto contractInputDto) {
        User user;
        Company company;
        try {
            user = userRepository.findById(contractInputDto.getUserId()).get();
            company = companyRepository.findById(contractInputDto.getCompanyId()).get();
        }
        catch (NoSuchElementException e){
            throw new NoSuchElementException("userId or companyId for contract invalid");
        }
        Contract contract = new Contract(user, company, contractInputDto.getStartDate(), contractInputDto.getEndDate());
        return ContractMapper.mapToContractDto(contractRepository.save(contract));
    }

    @Override
    public ContractDto createContract(Contract contract) {
        return ContractMapper.mapToContractDto(contractRepository.save((contract)));
    }

    @Override
    public List<ContractDto> getContractOfUserFromCountry(String country) {
        return null;//contractRepository.findAll(where(SpecificationUtils.))
    }

    @Override
    public List<ContractDto> getContractOfUserBeforeDate(Date date) {
        return contractRepository.findAll(where(SpecificationUtils.lessThan("startDate", date)));
    }

    @Override
    public List<ContractDto> getContractByIndustryAndStartDate(String industry, Date startDate) {
        return contractRepository.findAll(where(SpecificationUtils.equalsFilterOnContractCompanyJoin("company",
                "industry", industry)).and(SpecificationUtils.lessThanDate("startDate", startDate)));
    }

    @Override
    public List<ContractDto> createContractWithUserAndCompanyInSameCountry(String country, Date startTime,
                                                                            Date endTime){
        List<ContractDto> contractDtos = new ArrayList<>();

        List<User> users = userRepository.findAll(SpecificationUtils.equals(
                DataFields.USER_COUNTRY.value, country));
        List<Company> companies = companyRepository.findAll(SpecificationUtils.stringLike(
                DataFields.COMPANY_LOCATION.value, country));

        for(User user : users){
            for(Company company : companies){
                Contract contract = new Contract(user, company, startTime, endTime);
                contractDtos.add(ContractMapper.mapToContractDto(contractRepository.save(contract)));
            }
        }
        return contractDtos;
    }

    @Override
    public List<UserDto> getActiveContractorsForCountry(String country) {
        List<UserDto> users = new ArrayList<>();
        List<Contract> contracts = new ArrayList<>();
        contractRepository.findAll(where(SpecificationUtils.equalsFilterOnContractUserJoin(DataFields.CONTRACT_USER.value,
                DataFields.USER_COUNTRY.value, country))
                .and(SpecificationUtils.lessThanDate(DataFields.CONTRACT_START_DATE.value, new Date()))
                .and(SpecificationUtils.greaterThanDate(DataFields.CONTRACT_END_DATE.value, new Date())))
                .forEach(x -> contracts.add((Contract) x));

        for(Contract contract : contracts){
            users.add(UserMapper.mapToUserDto(contract.getUser()));
        }
        return users;
    }

}
