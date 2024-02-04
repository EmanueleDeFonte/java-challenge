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

/**
 * Logging filter that intercepts requests and responses to log their details.
 */
@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    /**
     * Performs the filtering of requests and responses to log their details.
     *
     * @param req the ServletRequest
     * @param res the ServletResponse
     * @param chain the FilterChain
     * @throws IOException if an I/O error occurs during the filtering process
     * @throws ServletException if a servlet exception occurs during the filtering process
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Create a Wrapper of the request and the response object.
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {

            chain.doFilter(wrappedRequest, wrappedResponse);

        } finally {

            // Log request and response details.
            logRequestBody(wrappedRequest);
            logResponseBody(wrappedResponse);

            // After consuming the Response Body for logging purpose, restore it back.
            wrappedResponse.copyBodyToResponse();

        }
    }

    /**
     * Logs the request body.
     *
     * @param request the ContentCachingRequestWrapper containing the request
     */
    private static void logRequestBody(ContentCachingRequestWrapper request) {

        // Log the Request Method and URI and if there is a Body in the request, log the request body too.
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

    /**
     * Logs the response body.
     *
     * @param response the ContentCachingResponseWrapper containing the response
     */
    private static void logResponseBody(ContentCachingResponseWrapper response) {

        // Log the Response Status and if there is a Body in the response, log the response body too.
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
