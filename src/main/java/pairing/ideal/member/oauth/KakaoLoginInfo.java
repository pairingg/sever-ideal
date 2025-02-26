package pairing.ideal.member.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pairing.ideal.member.entity.Member;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginInfo implements OauthLoginInfo {

    @Value("${security.oauth2.client.registration.kakao.client-id}")
    private String kakao_client_id;

    @Value("${security.oauth2.client.registration.kakao.client-secret}")
    private String kakao_client_secret;

    @Value("${security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakao_redirect_uri;

    @Value("${security.oauth2.client.registration.kakao.grant-type}")
    private String kakao_grant_type;

    @Value("${security.oauth2.client.registration.kakao.token-uri}")
    private String kakao_token_uri;

    @Value("${security.oauth2.client.registration.kakao.user-info-uri}")
    private String kakao_user_info_uri;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<String> requestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.set("grant_type", kakao_grant_type);
        body.set("client_id", kakao_client_id);
        body.set("client_secret", kakao_client_secret);
        body.set("redirect_uri", kakao_redirect_uri);
        body.set("code", code);
        return restTemplate.exchange(kakao_token_uri, HttpMethod.POST, new HttpEntity<>(body,headers), String.class);
    }

    @Override
    public OauthTokenDto getAccessToken(ResponseEntity<String> response) {
        try {
            log.info("response.getBody() : {}", response.getBody());
            return objectMapper.readValue(response.getBody(), OauthTokenDto.class);
        } catch (JsonProcessingException e) {
            log.error("Error while processing JSON: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public ResponseEntity<String> requestUserInfo(OauthTokenDto oauthTokenDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthTokenDto.getAccess_token());

        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        URI uri = UriComponentsBuilder
                .fromUriString(kakao_user_info_uri)
                .encode().build().toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    @Override
    public Member getUserInfo(ResponseEntity<String> userInfoRes) {
        try {
            JsonNode jsonNode = objectMapper.readTree(userInfoRes.getBody());
            String email = jsonNode.get("kakao_account").get("email").asText();
            log.info("JsonNode is {}", jsonNode);
            return Member.builder().email(email)
//                    .nickname(nickname)
//                    .memberRole(MemberRole.MEMBER)
//                    .loginType(LoginType.KAKAO)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}