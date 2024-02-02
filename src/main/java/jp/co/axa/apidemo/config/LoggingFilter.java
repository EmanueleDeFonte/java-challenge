package jp.co.axa.apidemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {

            chain.doFilter(wrappedRequest, wrappedResponse);

        } finally {

            logRequestBody(wrappedRequest);
            logResponseBody(wrappedResponse);
            wrappedResponse.copyBodyToResponse();

        }
    }

    private static void logRequestBody(ContentCachingRequestWrapper request) {

        logger.info("Request Method : {}", request.getMethod());
        logger.info("Request URI : {}", request.getRequestURI());

        byte[] requestBuffer = request.getContentAsByteArray();

        if (requestBuffer.length > 0) {

            try {

                String requestBody = new String(requestBuffer, 0, requestBuffer.length, request.getCharacterEncoding());
                logger.info("Request Body : {}", requestBody);

            } catch (Exception e) {

                logger.error("Error reading the request body.");

            }

        }

    }

    private static void logResponseBody(ContentCachingResponseWrapper response) {

        logger.info("Response Status: {}", response.getStatus());

        byte[] responseBugger = response.getContentAsByteArray();

        if (responseBugger.length > 0) {

            try {

                String responseBodyString = new String(responseBugger, 0, responseBugger.length, response.getCharacterEncoding());
                logger.info("Response Body: {}", responseBodyString);

            } catch (Exception e) {

                logger.error("Error reading the response body.", e);
            }

        }
    }

}
