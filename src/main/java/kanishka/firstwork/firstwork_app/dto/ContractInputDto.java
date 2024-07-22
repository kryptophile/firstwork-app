package kanishka.firstwork.firstwork_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class ContractInputDto {
    private Long userId;
    private Long companyId;
    private Date startDate;
    private Date endDate;
}
