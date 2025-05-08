package com.supience.service;

import com.supience.dto.LoginResponse;
import com.supience.dto.OAuthAttributes;
import com.supience.entity.User;
import com.supience.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        /* user 없으면 새로 생성 */
        User user = userRepository.findByEmail(attributes.getEmail())
                .orElseGet( ()-> userRepository.save(attributes.toEntity()));

        httpSession.setAttribute("user", LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .sessionId(httpSession.getId())
                .build());

        return new DefaultOAuth2User(Collections.emptyList(),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }
}
