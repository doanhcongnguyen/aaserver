package com.dcn.aaserver.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenResource {

    @Resource(name = "tokenServices")
    private ConsumerTokenServices tokenServices;

    @Resource(name = "tokenStore")
    private TokenStore tokenStore;

    @Autowired
    private Environment env;

    @PostMapping(value = "/oauth/token/revokeById/{tokenId}")
    @ResponseBody
    public void revokeToken(HttpServletRequest request, @PathVariable String tokenId) {
        tokenServices.revokeToken(tokenId);
    }

    @GetMapping(value = "/tokens")
    @ResponseBody
    public List<String> getTokens() {
        List<String> tokenValues = new ArrayList<>();
        String clientId = env.getProperty("simple-resource.client");
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId(clientId);
        if (tokens != null) {
            for (OAuth2AccessToken token : tokens) {
                tokenValues.add(token.getValue());
            }
        }
        return tokenValues;
    }

    @PostMapping(value = "/tokens/revokeRefreshToken/{tokenId}")
    @ResponseBody
    public String revokeRefreshToken(@PathVariable String tokenId) {
        if (tokenStore instanceof JdbcTokenStore) {
            ((JdbcTokenStore) tokenStore).removeRefreshToken(tokenId);
        }
        return tokenId;
    }
}
