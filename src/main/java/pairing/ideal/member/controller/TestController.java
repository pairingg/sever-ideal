package pairing.ideal.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class TestController {
    @Value("${security.oauth2.client.registration.kakao.client-id}")
    private String kakao_client_id;

    @Value("${security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakao_redirect_uri;

    @Value("${security.oauth2.client.registration.naver.client-id}")
    private String naver_client_id;

    @Value("${security.oauth2.client.registration.naver.client-secret}")
    private String naver_client_secret;

    @Value("${security.oauth2.client.registration.naver.redirect-uri}")
    private String naver_redirect_uri;

    @Value("${security.oauth2.client.registration.naver.authorize-uri}")
    private String getNaver_client_id; // code

    @Value("${security.oauth2.client.registration.kakao.authorize-uri}")
    private String authorize_uri_kakao;

    @GetMapping("/oauth/login/naver")
    public ResponseEntity<String> naverLogin() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("response_type", "code");
        params.set("client_id", naver_client_id);
        params.set("redirect_uri", naver_redirect_uri);
        params.set("state", "STATE_STRING");

        URI uri = UriComponentsBuilder
                .fromUriString(getNaver_client_id)
                .queryParams(params)
                .encode().build().toUri();
        return ResponseEntity.status(302).location(uri).build();
    }

    @GetMapping("/oauth/login/kakao")
    public ResponseEntity<String> kakaoLogin() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("response_type", "code");
        params.set("client_id", kakao_client_id);
        params.set("redirect_uri", kakao_redirect_uri);
//        params.set("state", "STATE_STRING");

        URI uri = UriComponentsBuilder
                .fromUriString(authorize_uri_kakao)
                .queryParams(params)
                .encode().build().toUri();
        return ResponseEntity.status(302).location(uri).build();
    }
}
