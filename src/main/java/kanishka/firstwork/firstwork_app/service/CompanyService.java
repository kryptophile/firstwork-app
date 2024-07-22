package kanishka.firstwork.firstwork_app.service;

import kanishka.firstwork.firstwork_app.dto.CompanyDto;
import kanishka.firstwork.firstwork_app.model.Company;

import java.util.List;

public interface CompanyService {
    CompanyDto createCompany(CompanyDto companyDto);

    List<CompanyDto> getCompanyNameEquals(String name);

    List<CompanyDto> getAllCompanies();

    List<Company> getCompanyNameIn(List<String> names);

    Company getCompanyByName(String name);
}
