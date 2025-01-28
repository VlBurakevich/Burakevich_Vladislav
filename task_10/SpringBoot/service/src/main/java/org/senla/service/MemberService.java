package org.senla.service;

import lombok.AllArgsConstructor;
import org.senla.entity.Member;
import org.senla.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getById(Long id) {
        return memberRepository.getById(id);
    }

    public List<Member> getAll() {
        return memberRepository.fetchLimitedRandom(20);
    }

    public void insert(Member member) {
        memberRepository.save(member);
    }

    public void update(Member member) {
        memberRepository.update(member);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
