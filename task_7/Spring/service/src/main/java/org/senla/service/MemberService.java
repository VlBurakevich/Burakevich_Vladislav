package org.senla.service;

import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.entity.Member;
import org.senla.repository.MemberRepository;

import java.util.List;

@Component
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

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
