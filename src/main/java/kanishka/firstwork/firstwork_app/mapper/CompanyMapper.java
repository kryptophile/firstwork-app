package kanishka.firstwork.firstwork_app.mapper;

import kanishka.firstwork.firstwork_app.dto.CompanyDto;
import kanishka.firstwork.firstwork_app.model.Company;

public class CompanyMapper {
    public static Company mapToCompany(CompanyDto companyDto){
        return new Company(
                companyDto.getName(),
                companyDto.getIndustry(),
                companyDto.getLocation()
        );
    }

    public static CompanyDto mapToCompanyDto(Company company){
        return new CompanyDto(
                company.getName(),
                company.getIndustry(),
                company.getLocation()
        );
    }
}
