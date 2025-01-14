package com.nos.home.common.interceptor;

import com.nos.home.common.menu.dto.MenuDto;
import com.nos.home.common.util.MenuHelper;
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
public class ModuleUrlInterceptor implements HandlerInterceptor
{
    ///=================================================================================================================
    /// [1] 필드 및 레포지토리
    ///=================================================================================================================
    private final       MenuHelper          menuHelper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //--------------------------------------------------------------------------------------------------------------
        //
        //--------------------------------------------------------------------------------------------------------------
        String originalUrl = request.getRequestURI();
        log.debug("[URL ReWrite]: {}", originalUrl);

        //--------------------------------------------------------------------------------------------------------------
        // 이미 처리된 URL인 경우, 다른 인터셉터 실행 중지
        //--------------------------------------------------------------------------------------------------------------
        if(request.getAttribute("originalUrl") != null)
        {
            return true;
        }

        //--------------------------------------------------------------------------------------------------------------
        // URL 패턴 분석 ==> (Slug 추출 및 메뉴 정보 조회)
        //--------------------------------------------------------------------------------------------------------------
        Matcher matcher = Pattern.compile("/([^/]+)/(.*)").matcher(originalUrl);

        if (matcher.find())
        {
            String slug                 = matcher.group(1);
            String remainingPath        = matcher.group(2);

            log.debug("Slug: {}, Remaining path: {}", slug, remainingPath);
            try
            {
                //------------------------------------------------------------------------------------------------------
                // "Slug" 정보를 바탕으로 메뉴가 존재하는지 체크
                //------------------------------------------------------------------------------------------------------
                MenuDto menuDto = menuHelper.checkSlug(slug);
                if(menuDto != null)
                {
                    String internalUrl = createInternalUrl(menuDto, remainingPath);
                    log.debug("URL 주소 변경 : [{} ==> {}]", originalUrl, internalUrl);
                    //--------------------------------------------------------------------------------------------------
                    // 원본 URL과 메뉴 정보 저장
                    //--------------------------------------------------------------------------------------------------
                    request.setAttribute("originalUrl",     originalUrl);
                    request.setAttribute("instanceId",  menuDto.getInstanceId());

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

    //------------------------------------------------------------------------------------------------------------------
    // Slug 정보를 바탕으로 내부 URL 생성
    //------------------------------------------------------------------------------------------------------------------
    private String createInternalUrl(MenuDto menuDto, String remainingPath) {
        //--------------------------------------------------------------------------------------------------------------
        // 1. Slug 정보 추출
        //--------------------------------------------------------------------------------------------------------------
        String slug             = menuDto.getUrl();
        String modulePrefix     = menuDto.getModuleId();
        return "/" + modulePrefix + "/" + slug + "/" + remainingPath; // 앞에 "/" 추가
    }
}
