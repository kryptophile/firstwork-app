package kanishka.firstwork.firstwork_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company")
public class Company {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String industry;

    private String location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private Set<Contract> contracts = new HashSet<>();

    public Company(String name, String industry, String location){
        this.name = name;
        this.industry = industry;
        this.location = location;
    }
}
