package pairing.ideal.member.entity;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@Builder
@Getter
public class CustomUserDetails implements UserDetails {

    private Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getMemberRole()));
//        return authorities;
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
//        return member.getEmail();
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}