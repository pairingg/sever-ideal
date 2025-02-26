package pairing.ideal.member.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pairing.ideal.member.dto.requset.OauthRequest;
import pairing.ideal.member.dto.response.OauthResponse;
import pairing.ideal.member.jwt.JwtUtils;
import pairing.ideal.member.service.AuthService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/oauth/login")
    public OauthResponse login(@RequestBody OauthRequest request, HttpServletResponse response) {
        System.out.println(request.toString());
        OauthResponse oauthResponse = authService.oauthLogin(request, response);
        System.out.println(oauthResponse.toString());
        System.out.println(oauthResponse);
        return oauthResponse;
    }

    @GetMapping("/oauth/token")
    public boolean getToken(@RequestParam String token) {
        return jwtUtils.validateToken(token);
    }
}
