package org.senla.dto;

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
    private String firstName;
    private String lastName;
    private String nationality;
    private MemberType type;
    private GenderType gender;
}
