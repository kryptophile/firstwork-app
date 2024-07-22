package kanishka.firstwork.firstwork_app.service;

import kanishka.firstwork.firstwork_app.dto.ContractDto;
import kanishka.firstwork.firstwork_app.dto.ContractInputDto;
import kanishka.firstwork.firstwork_app.dto.UserDto;
import kanishka.firstwork.firstwork_app.model.Contract;

import java.util.Date;
import java.util.List;

public interface ContractService {
    ContractDto createContract(ContractInputDto contractInputDto);

    ContractDto createContract(Contract contract);

    List<ContractDto> getContractOfUserFromCountry(String country);

    List<ContractDto> getContractOfUserBeforeDate(Date date);

    List<ContractDto> getContractByIndustryAndStartDate(String industry, Date startDate);

    List<ContractDto> createContractWithUserAndCompanyInSameCountry(String country, Date startDate, Date endDate);

    List<UserDto> getActiveContractorsForCountry(String country);
}
