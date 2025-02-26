package pairing.ideal.member.dto.response;

import lombok.Getter;

@Getter
public class OauthResponse {
    private String accessToken;
    private boolean enrolled;

    public OauthResponse(String jwt, boolean enrolled) {
        this.accessToken = jwt;
        this.enrolled = enrolled;
    }
}
