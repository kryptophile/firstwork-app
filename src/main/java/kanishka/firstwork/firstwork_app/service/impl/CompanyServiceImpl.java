package kanishka.firstwork.firstwork_app.service.impl;

import kanishka.firstwork.firstwork_app.dto.CompanyDto;
import kanishka.firstwork.firstwork_app.enumeration.DataFields;
import kanishka.firstwork.firstwork_app.mapper.CompanyMapper;
import kanishka.firstwork.firstwork_app.model.Company;
import kanishka.firstwork.firstwork_app.repository.CompanyRepository;
import kanishka.firstwork.firstwork_app.service.CompanyService;
import kanishka.firstwork.firstwork_app.utils.SpecificationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    static Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        return CompanyMapper.mapToCompanyDto(companyRepository.save(CompanyMapper.mapToCompany(companyDto)));
    }

    public List<CompanyDto> getCompanyNameEquals(String name){
        List<CompanyDto> companyDtoList = new ArrayList<>();
        companyRepository.findAll().forEach(x -> companyDtoList.add(CompanyMapper.mapToCompanyDto(x)));
        return companyDtoList;
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<CompanyDto> companyDtoList = new ArrayList<>();
        companyRepository.findAll().forEach(x -> companyDtoList.add(CompanyMapper.mapToCompanyDto(x)));
        return companyDtoList;
    }

    public List<Company> getCompanyNameIn(List<String> names){
        List<Company> companies = (List<Company>) companyRepository.findAll(SpecificationUtils
                        .in(DataFields.COMPANY_NAME.value, names))
                .stream().collect(Collectors.toList());
        return companies;
    }

    @Override
    public Company getCompanyByName(String name) {
        Optional<Company> company =
                companyRepository.findAll(SpecificationUtils.equals(DataFields.COMPANY_NAME.value, name)).stream().findFirst();

        if(company.isPresent())
            return company.get();
        logger.debug("No company found with name =" + name );
        return null;
    }

}
