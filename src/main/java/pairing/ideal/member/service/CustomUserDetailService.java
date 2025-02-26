package pairing.ideal.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pairing.ideal.member.entity.CustomUserDetails;
import pairing.ideal.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomUserDetails(memberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
                ));
    }
}