package com.llm.backend.service;

import com.llm.backend.repository.MemberRepository;
import com.llm.backend.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplication(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplication(Member member) {
        List<Member> findNames = memberRepository.findByName(member.getName());

        if (!findNames.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }


}
