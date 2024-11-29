package org.senla.service;

import org.senla.dao.MemberDao;

import org.senla.entity.Member;

import java.util.List;

public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

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
