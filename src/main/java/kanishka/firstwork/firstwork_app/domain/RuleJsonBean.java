package kanishka.firstwork.firstwork_app.domain;


import kanishka.firstwork.firstwork_app.enumeration.Operator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuleJsonBean {
    Expression expression;
    List<RuleJsonBean> rules;
    String linkToPrev;
}
