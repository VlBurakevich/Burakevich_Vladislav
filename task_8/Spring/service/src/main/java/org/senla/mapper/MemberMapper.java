package org.senla.mapper;

import lombok.experimental.UtilityClass;
import org.senla.dto.MemberDto;
import org.senla.entity.Member;

@UtilityClass
public class MemberMapper {
    public static MemberDto toDto(Member member) {
        if (member == null) {
            return new MemberDto();
        }
        return new MemberDto(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getNationality(),
                member.getType(),
                member.getGender()
        );
    }

    public static Member toEntity(MemberDto memberDto) {
        if (memberDto == null) {
            return new Member();
        }
        Member member = new Member();
        member.setId(memberDto.getId());
        member.setFirstName(memberDto.getFirstName());
        member.setLastName(memberDto.getLastName());
        member.setNationality(memberDto.getNationality());
        member.setType(memberDto.getType());
        member.setGender(memberDto.getGender());
        return member;
    }
}
