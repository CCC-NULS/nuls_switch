package io.nuls.nulsswitch.demo.dto;

import io.nuls.nulsswitch.common.validation.ValidForm;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 *
 * </pre>

 */
@ValidForm
@Data
public class TestValidDTO {

    @NotNull
    @Length(max = 20, min = 6)
    private String name;

    @NotNull
    @Range(min = 1, max = 120)
    private Integer age;

    @NotNull
    @Range(min = 1, max = 3)
    private Integer sex;
}

