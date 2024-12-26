package org.senla.service;

import org.senla.dao.MemberDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.entity.Member;

import java.util.List;

@Component
public class MemberService {

    @Autowired
    private MemberDao memberDao;

    public Member getById(Long id) {
        return memberDao.getById(id);
    }

    public List<Member> getAll() {
        return memberDao.getAll();
    }

    public void insert(Member member) {
        memberDao.insert(member);
    }

    public void update(Member member) {
        memberDao.update(member);
    }

    public void delete(Long id) {
        memberDao.delete(id);
    }
}
