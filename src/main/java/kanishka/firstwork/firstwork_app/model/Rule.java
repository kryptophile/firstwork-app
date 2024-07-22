package kanishka.firstwork.firstwork_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rule")
public class Rule {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(name = "rule_json", nullable = false, length = 10000)
    private String ruleJson;

    public Rule(String name, String ruleJson){
        this.name = name;
        this.ruleJson = ruleJson;
    }
}
