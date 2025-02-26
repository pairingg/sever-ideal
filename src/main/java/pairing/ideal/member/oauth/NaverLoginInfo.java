package pairing.ideal.member.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import pairing.ideal.member.entity.Member;

@Component
@Slf4j
@RequiredArgsConstructor
public class NaverLoginInfo implements OauthLoginInfo{

    @Value("${security.oauth2.client.registration.naver.client-id}")
    private String naver_client_id;

    @Value("${security.oauth2.client.registration.naver.client-secret}")
    private String naver_client_secret;

//    @Value("${security.oauth2.client.registration.naver.redirect-uri}")
//    private String naver_redirect_uri;

    @Value("${security.oauth2.client.registration.naver.grant-type}")
    private String naver_grant_type; // code

    @Value("${security.oauth2.client.registration.naver.token-uri}")
    private String naver_token_uri;

    @Value("${security.oauth2.client.registration.naver.user-info-uri}")
    private String naver_user_info_uri;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private String userInfoUri;
    @Override
    public ResponseEntity<String> requestAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", naver_grant_type);
        body.add("client_id", naver_client_id);
        body.add("client_secret",  naver_client_secret);
        body.add("code", code);
//        body.add("state", new BigInteger(130, new SecureRandom()).toString()); // 임의의 값
        body.add("state", "STATE_STRING"); // 임의의 값

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(naver_token_uri, HttpMethod.POST, requestEntity, String.class);
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
        headers.add("Authorization","Bearer " + oauthTokenDto.getAccess_token());

        System.out.println(oauthTokenDto.getAccess_token());
        return restTemplate.exchange(naver_user_info_uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    @Override
    public Member getUserInfo(ResponseEntity<String> userInfoRes) {
        try{
            JsonNode jsonNode = objectMapper.readTree(userInfoRes.getBody());
//            {"resultcode":"00","message":"success","response":{"id":"URNSpP9JXgw6JBlP8FD6cmShYlx4cZmn7bF_f7S0qtg","gender":"M","email":"leejintwo12@naver.com"}}
            String email = jsonNode.get("response").get("email").asText();
            return Member.builder().email(email)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}