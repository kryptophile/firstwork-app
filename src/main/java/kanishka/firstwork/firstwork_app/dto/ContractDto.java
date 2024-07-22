package kanishka.firstwork.firstwork_app.dto;

import kanishka.firstwork.firstwork_app.model.Company;
import kanishka.firstwork.firstwork_app.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter@AllArgsConstructor
public class ContractDto {

    private UserDto user;
    private CompanyDto company;
    private Date startDate;
    private Date endDate;

}
