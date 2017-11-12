package com.sneakerate.marketing.service;

import com.sneakerate.marketing.FacebookAppConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.sneakerate.marketing.SignatureUtil.computeSignature;

@Service
public class TokenService {
    private static final String URL = "https://graph.facebook.com/v2.9/%s/access_tokens";

    private RestTemplate restTemplate = new RestTemplate();

    public String getAdAccountAccessTokenByAdminAccessToken(String adminAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("business_app", String.valueOf(FacebookAppConfig.APP_ID));
        params.add("scope", "ads_management,ads_read,business_management");
        params.add("appsecret_proof", computeSignature(adminAccessToken, FacebookAppConfig.APP_SECRET));
        params.add("access_token", adminAccessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        return restTemplate.postForObject(String.format(URL, FacebookAppConfig.SYSTEM_USER_ID), request, String.class);
    }
}
