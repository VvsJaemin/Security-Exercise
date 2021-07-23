package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.domain.MemberDto;
import com.example.demo.domain.Role;
import com.example.demo.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private MemberRepository memberRepository;


    @Transactional
    public Long joinUser(MemberDto memberDto) {
        //비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 아이디는 이메일, 로그인하는 form(즉, html)에서는 name="username"으로 요청해야함.
        Optional<Member> userEntityWrapper = memberRepository.findByEmail(username);
        Member member = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        // 새로운 권한 리스트 객체를 생성해서 아래 .add를 통해 롤을 부여함.

        if (("admin@example.com").equals(username)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        return new User(member.getEmail(), member.getPassword(), authorities);
        // return security에서 제공하는 UserDetails를 구현한 User를 반환, 매개변수는 아이디, 패스워드, 권한 순
    }
}
