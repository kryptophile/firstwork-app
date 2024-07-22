package kanishka.firstwork.firstwork_app.controller;

import kanishka.firstwork.firstwork_app.dto.ContractInputDto;
import kanishka.firstwork.firstwork_app.dto.UserDto;
import kanishka.firstwork.firstwork_app.enumeration.Constants;
import kanishka.firstwork.firstwork_app.dto.ContractDto;
import kanishka.firstwork.firstwork_app.enumeration.DataFields;
import kanishka.firstwork.firstwork_app.model.Contract;
import kanishka.firstwork.firstwork_app.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    public ContractController(ContractService contractService){
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<ContractDto> createContract(@RequestBody ContractInputDto contractInputDto){
        return new ResponseEntity<>(contractService.createContract(contractInputDto), HttpStatus.CREATED);
    }

    @GetMapping("/getContractBeforeDate")
    public ResponseEntity<List<ContractDto>> getContractBeforeDate(@RequestBody Map<String,Date> dateMap){
        return new ResponseEntity<List<ContractDto>>(contractService
                .getContractOfUserBeforeDate(dateMap.get(DataFields.CONTRACT_START_DATE.value)), HttpStatus.OK);
    }

    @GetMapping("/getContractByIndustryAndStartDate")
    public ResponseEntity<List<ContractDto>> getContractByIndustryAndStartDate(@RequestBody Map<String, String> request)
    throws Exception{
        String industry = request.get("industry");
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.YYY_MM_DD.value, Locale.ENGLISH);

        Date date = formatter.parse(request.get(DataFields.CONTRACT_START_DATE.value));
        return new ResponseEntity<List<ContractDto>>(contractService.getContractByIndustryAndStartDate(industry, date),
                HttpStatus.OK);
    }

    @PostMapping("/createContractWithUserAndCompanyInSameCountry")
    public ResponseEntity<List<ContractDto>> createContractWithUserAndCompanyInSameCountry(
            @RequestBody Map<String, String> request) throws Exception{
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.YYY_MM_DD.value, Locale.ENGLISH);
        String country = request.get(DataFields.USER_COUNTRY.value);
        Date startDate = formatter.parse(request.get(DataFields.CONTRACT_START_DATE.value));
        Date endDate = formatter.parse(request.get(DataFields.CONTRACT_END_DATE.value));
        return new ResponseEntity<>(contractService.createContractWithUserAndCompanyInSameCountry(
                country, startDate, endDate), HttpStatus.CREATED);
    }

    @GetMapping("/getActiveContractorsForCountry")
    public ResponseEntity<List<UserDto>> getActiveContractorsForCountry(
            @RequestBody Map<String, String> request){
        String country = request.get(DataFields.USER_COUNTRY.value);
        return new ResponseEntity<>(contractService.getActiveContractorsForCountry(country), HttpStatus.OK);
    }

}
