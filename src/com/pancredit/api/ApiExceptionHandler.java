package com.pancredit.api;

import com.pancredit.config.ApiModule;
import com.pancredit.exception.ResourceNotFoundException;
import com.pancredit.json.ApiJsonHelper;
import com.pancredit.model.ErrorMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.io.PrintWriter;

import static javax.servlet.RequestDispatcher.*;

@WebServlet("/ApiExceptionHandler")
public class ApiExceptionHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ApiJsonHelper apiJsonHelper = new ApiJsonHelper(ApiModule.INSTANCE.getObjectMapper());

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {

        if (HttpServletResponse.SC_NOT_FOUND == (int) request.getAttribute(ERROR_STATUS_CODE)) {
            printResponse(response, new ErrorMessage(HttpServletResponse.SC_NOT_FOUND, request.getAttribute(ERROR_MESSAGE).toString()));
            return;
        }

        Exception exception = (Exception) request.getAttribute(ERROR_EXCEPTION);

        getServletContext().log("Error on an application argument", exception);

        if (exception instanceof ValidationException) {
            printResponse(response, new ErrorMessage(HttpServletResponse.SC_BAD_REQUEST, "Bad request."));
            return;
        }

        if (exception instanceof ResourceNotFoundException) {
            printResponse(response, new ErrorMessage(HttpServletResponse.SC_NOT_FOUND, exception.getMessage()));
            return;
        }

        printResponse(response, new ErrorMessage(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error."));
    }

    private void printResponse(HttpServletResponse response, ErrorMessage errorMessage) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorMessage.getStatus());

        PrintWriter out = response.getWriter();
        out.print(apiJsonHelper.toJson(errorMessage));
        out.flush();
    }
}