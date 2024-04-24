package co.istad.mobilebankingcstad.security;

import co.istad.mobilebankingcstad.domain.User;
import co.istad.mobilebankingcstad.features.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;


@Getter
@Setter
//@NoArgsConstructor
@RequiredArgsConstructor
@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    private final UserRepository userRepository;

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        User user = userRepository.findByEmail(source.getSubject())
                .orElseThrow(() -> new BadCredentialsException("Invalid token!!!"));
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);

        System.out.println("This is the login User ! : "+user);
        System.out.println("CustomUserDetails : "+customUserDetails);
        return new UsernamePasswordAuthenticationToken(
                customUserDetails,
                "",
                customUserDetails.getAuthorities()
        );
    }
}
