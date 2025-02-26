package pairing.ideal.member.dto.requset;

import pairing.ideal.member.common.LoginType;

public record OauthRequest(String code, LoginType type) {
}