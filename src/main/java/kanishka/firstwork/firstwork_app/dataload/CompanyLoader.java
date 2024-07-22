package kanishka.firstwork.firstwork_app.dataload;

import kanishka.firstwork.firstwork_app.dto.CompanyDto;
import kanishka.firstwork.firstwork_app.enumeration.Industry;
import kanishka.firstwork.firstwork_app.enumeration.Location;
import kanishka.firstwork.firstwork_app.model.Company;
import kanishka.firstwork.firstwork_app.model.User;
import kanishka.firstwork.firstwork_app.repository.CompanyRepository;
import kanishka.firstwork.firstwork_app.repository.UserRepository;
import kanishka.firstwork.firstwork_app.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Order(1)
@Component
public class CompanyLoader implements ApplicationRunner {

    private CompanyService companyService;

    @Autowired
    public CompanyLoader(final CompanyService companyService) {
        this.companyService = companyService;
    }

    public void run(final ApplicationArguments args) {

        List<CompanyDto> companyDtoList = new ArrayList<>();
        companyDtoList.add(new CompanyDto("Company1", Industry.IT.value, Location.DELHI.value));
        companyDtoList.add(new CompanyDto("Company2", Industry.AGRICULTURE.value, Location.BANGALORE.value));
        companyDtoList.add(new CompanyDto("Company3", Industry.MANUFACTURE.value, Location.BANGALORE.value));
        companyDtoList.add(new CompanyDto("Company4", Industry.IT.value, Location.DELHI.value));
        companyDtoList.add(new CompanyDto("Company5", Industry.MANUFACTURE.value, Location.MOSCOW.value));
        companyDtoList.add(new CompanyDto("Company6", Industry.CONSULTANCY.value, Location.SAN_FRANCISCO.value));
        companyDtoList.add(new CompanyDto("Company7", Industry.MANUFACTURE.value, Location.DELHI.value));
        companyDtoList.add(new CompanyDto("Company8", Industry.CONSULTANCY.value, Location.SAN_FRANCISCO.value));
        companyDtoList.add(new CompanyDto("Company9", Industry.MANUFACTURE.value, Location.MOSCOW.value));
        companyDtoList.add(new CompanyDto("Company10", Industry.CONSULTANCY.value, Location.SAN_FRANCISCO.value));

        for (CompanyDto companyDto : companyDtoList) {
            companyService.createCompany(companyDto);
        }
    }
}
