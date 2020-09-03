package com.dcn.aaserver.config;

import com.dcn.aaserver.domain.entity.UserEntity;
import com.dcn.aaserver.repository.UserRepository;
import com.dcn.aaserver.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        String username = authentication.getName();
        UserEntity userEntity = userRepository.findByUserName(username);
        this.doSetAdditionData(userEntity, additionalInfo);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    private void doSetAdditionData(UserEntity userEntity, Map<String, Object> additionalInfo) {
        additionalInfo.put(Constants.AA.USER_ID, userEntity.getId());
        additionalInfo.put(Constants.AA.FULL_NAME, userEntity.getFullName());
        additionalInfo.put(Constants.AA.LANGUAGE, userEntity.getLanguage());
    }

}
