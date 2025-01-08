package com.nos.home.common.interceptor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class ModuleUrlInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String originalUrl = request.getRequestURI();
        log.debug("Intercepting request for path: {}", originalUrl);

        // 모듈 URL 패턴인 경우에만 처리
        Matcher matcher = Pattern.compile("/([^/]+)/(.*)").matcher(originalUrl);
        if (matcher.find()) {
            String slug                 = matcher.group(1);
            String remainingPath        = matcher.group(2);

            log.debug("Slug: {}, Remaining path: {}", slug, remainingPath);
            try {

                if(slug.equals("gizzi") || slug.equals("heero"))
                {
                    //--------------------------------------------------------------------------------------------------
                    // 내부 URL 생성
                    //--------------------------------------------------------------------------------------------------
                    String internalUrl = createInternalUrl(slug, remainingPath);
                    log.debug("Rewriting URL from {} to {}", originalUrl, internalUrl);

                    //--------------------------------------------------------------------------------------------------
                    // 원본 URL과 메뉴 정보 저장
                    //--------------------------------------------------------------------------------------------------
                    request.setAttribute("originalUrl", originalUrl);

                    //--------------------------------------------------------------------------------------------------
                    // 내부 URL로 포워딩
                    //--------------------------------------------------------------------------------------------------
                    RequestDispatcher dispatcher = request.getRequestDispatcher(internalUrl);
                    dispatcher.forward(request, response);
                    return false; // 포워딩 후 다른 인터셉터 실행 중지
                }
                else
                {
                    return true;
                }
            }
            catch (Exception e)
            {
                log.error("Error processing URL rewrite", e);
                throw new RuntimeException("Error processing URL rewrite", e);
            }
        }

        return true;
    }

    private String createInternalUrl(String slug, String remainingPath) {
        String modulePrefix = switch (slug)
        {
            case "gizzi" -> "/board";
            case "heero" -> "/blog";
            default -> throw new IllegalStateException("Unknown module type: " + slug);
        };

        return modulePrefix + "/" + slug + "/" + remainingPath;
    }
}
