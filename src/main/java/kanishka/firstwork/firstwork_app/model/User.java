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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private Integer age;

    private String country;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Contract> contracts = new HashSet<>();

    public User(String name, Integer age, String country){
        this.name = name;
        this.age = age;
        this.country = country;
    }
}
