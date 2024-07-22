package kanishka.firstwork.firstwork_app.dataload;

import kanishka.firstwork.firstwork_app.dto.CompanyDto;
import kanishka.firstwork.firstwork_app.dto.ContractDto;
import kanishka.firstwork.firstwork_app.dto.UserDto;
import kanishka.firstwork.firstwork_app.enumeration.Constants;
import kanishka.firstwork.firstwork_app.model.Company;
import kanishka.firstwork.firstwork_app.model.Contract;
import kanishka.firstwork.firstwork_app.model.User;
import kanishka.firstwork.firstwork_app.service.CompanyService;
import kanishka.firstwork.firstwork_app.service.ContractService;
import kanishka.firstwork.firstwork_app.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Order(2)
@Component
public class ContractLoader implements ApplicationRunner {

    private ContractService contractService;
    private CompanyService companyService;
    private UserService userService;

    public ContractLoader(ContractService contractService, CompanyService companyService, UserService userService){
        this.contractService = contractService;
        this.companyService = companyService;
        this.userService = userService;
    }

    public void run(final ApplicationArguments args) throws ParseException {

        List<ContractDto> contractList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat(Constants.YYY_MM_DD.value, Locale.ENGLISH);
        Date date = new Date(), startDate1, endDate1, startDate2, endDate2, startDate3, endDate3, startDate4, endDate4;
        String dateString1 = "2010-01-01";
        String dateString2 = "2014-11-21";

        String dateString3 = "2013-02-21";
        String dateString4 = "2018-12-21";

        String dateString5 = "2017-02-21";
        String dateString6 = "2022-11-31";

        String dateString7 = "2023-02-21";
        String dateString8 = "9999-12-31";

        startDate1 = formatter.parse(dateString1);
        endDate1 = formatter.parse(dateString2);

        startDate2 = formatter.parse(dateString3);
        endDate2 = formatter.parse(dateString4);

        startDate3 = formatter.parse(dateString5);
        endDate3 = formatter.parse(dateString6);

        startDate4 = formatter.parse(dateString7);
        endDate4 = formatter.parse(dateString8);


        Company company1 = companyService.getCompanyByName("Company1");
        Company company2 = companyService.getCompanyByName("Company2");
        Company company3 = companyService.getCompanyByName("Company3");
        Company company4 = companyService.getCompanyByName("Company4");

        User user1 = userService.getUserByName("User1");
        User user2 = userService.getUserByName("User2");
        User user3 = userService.getUserByName("User3");
        User user4 = userService.getUserByName("User4");
        User user5 = userService.getUserByName("User5");
        User user6 = userService.getUserByName("User6");
        User user7 = userService.getUserByName("User7");

        /**
         * 2010 - 2014 --> User1, user2 * company1, company2, company3
         * 2013 - 2018 --> User3, user4, user5 * company2, company3, company4
         * 2017 - 2022 --> User1, user 6 * company1, company2
         * 2023 - inf --> user 2, user3, user 7 * company 2, company3
         *
         * 2010 ----- 2013 ---- 2014 ----- 2017 ----- 2018 -----2022 ------ 2023 ---- inf
         *        6         15        9           13        4         0            6
         *
         */


        Company [] companyList1 = {company1, company2, company3};
        User [] userList1 = {user1, user2};

        Company [] companyList2 = {company2, company3, company4};
        User [] userList2 = {user3, user4, user5};

        Company [] companyList3 = {company1, company2};
        User [] userList3 = {user1, user6};

        Company [] companyList4 = {company2, company3};
        User [] userList4 = {user2, user3};

        for(Company company : companyList1){
            for(User user : userList1){
                contractService.createContract(new Contract(user, company, startDate1, endDate1));
            }
        }

        for(Company company : companyList2){
            for(User user : userList2){
                contractService.createContract(new Contract(user, company, startDate2, endDate2));
            }
        }

        for(Company company : companyList3){
            for(User user : userList3){
                contractService.createContract(new Contract(user, company, startDate3, endDate3));
            }
        }

        for(Company company : companyList4){
            for(User user : userList4){
                contractService.createContract(new Contract(user, company, startDate4, endDate4));
            }
        }
    }
}
