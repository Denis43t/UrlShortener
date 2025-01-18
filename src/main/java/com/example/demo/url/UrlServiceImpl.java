package com.example.demo.url;

import com.example.demo.security.AuthorizationService;
import com.example.demo.url.dto.UrlRequest;
import com.example.demo.url.dto.UrlResponse;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.demo.util.MessageProvider.*;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;
    private final LongUrlValidator validator;
    private final ShortUrlGenerator urlGenerator;
    private final AuthorizationService authorizationService;


    @Override
    @Transactional
    public UrlResponse getShortUrlFromLongUrl(UrlRequest request) {
        String longUrl = request.getUrl();

        if (longUrl == null || longUrl.isEmpty()) {
            return UrlResponse.failed(INCORRECT_URL_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!validator.isValid(longUrl)) {
            return UrlResponse.failed(INCORRECT_URL_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOptional = authorizationService.getAuthorizedUser(request.getAuthorizationHeader());

        if (userOptional.isEmpty()) {
            return UrlResponse.failed(NOT_AUTHENTICATED_MESSAGE,
                    HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();

        String shortUrl = urlGenerator.generateShortUrl();

        Url url = Url.builder()
                .longUrl(longUrl)
                .shortUrl(shortUrl)
                .user(user)
                .build();

        urlRepository.save(url);

        return UrlResponse.success(shortUrl, null);
    }

    @Override
    @Transactional
    public UrlResponse getLongUrlFromShortUrl(UrlRequest request) {
        String shortUrl = request.getUrl();

        if (shortUrl == null || shortUrl.isEmpty()) {
            return UrlResponse.failed(INCORRECT_URL_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        Optional<Url> urlOptional = urlRepository.findUrlByShortUrl(shortUrl);
        if (urlOptional.isEmpty()) {
            return UrlResponse.failed(URL_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
        }

        Url url = urlOptional.get();
        url.setVisits(url.getVisits() + 1);
        urlRepository.save(url);
        return UrlResponse.success(null, url.getLongUrl());
    }
}
