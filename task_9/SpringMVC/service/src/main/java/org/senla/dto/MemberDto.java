package org.senla.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.senla.enums.GenderType;
import org.senla.enums.MemberType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long id;

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    private String nationality;

    private MemberType type;

    private GenderType gender;
}
