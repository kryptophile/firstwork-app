package kanishka.firstwork.firstwork_app.controller;

import kanishka.firstwork.firstwork_app.dto.CompanyDto;
import kanishka.firstwork.firstwork_app.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto){
        return new ResponseEntity<>(companyService.createCompany(companyDto), HttpStatus.CREATED);
    }

    @GetMapping("/getCompaniesByName")
    public ResponseEntity<List<CompanyDto>> getCompaniesByName(@RequestBody Map<String, String> request){
        return new ResponseEntity<>(companyService.getCompanyNameEquals(request.get("name")), HttpStatus.OK);
    }

    @GetMapping("/getAllCompanies")
    public ResponseEntity<List<CompanyDto>> getAllCompanies(){
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }
}
