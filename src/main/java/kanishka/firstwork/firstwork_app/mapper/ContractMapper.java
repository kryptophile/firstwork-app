package kanishka.firstwork.firstwork_app.mapper;

import kanishka.firstwork.firstwork_app.dto.ContractDto;
import kanishka.firstwork.firstwork_app.model.Contract;

public class ContractMapper {
    public static Contract mapToContract(ContractDto contractDto){
        return new Contract(
                UserMapper.mapToUser(contractDto.getUser()),
                CompanyMapper.mapToCompany(contractDto.getCompany()),
                contractDto.getStartDate(),
                contractDto.getEndDate()
        );
    }

    public static ContractDto mapToContractDto(Contract contract){
        return new ContractDto(
                UserMapper.mapToUserDto(contract.getUser()),
                CompanyMapper.mapToCompanyDto(contract.getCompany()),
                contract.getStartDate(),
                contract.getEndDate()
        );
    }
}
