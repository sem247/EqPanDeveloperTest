/*
 *  PANCREDIT SYSTEMS LTD
 *  (C) Copyright PanCredit Systems Ltd 2021
 *
 *  COPYRIGHT NOTICE
 *  ---------------------------------
 *  The contents of this file are protected by copyright. Any unauthorised
 *  copying, duplication of its contents are in breach of the copyright.
 *
 *  Last Checked In By: $Author$
 *  Date Checked In:    $Date$
 *  Name and Version:   $Id$
 *
 *  Log messages:       $Log$
 *
 */
package com.pancredit.api;

//Import required java libraries

import com.pancredit.config.ApiModule;
import com.pancredit.json.ApiJsonHelper;
import com.pancredit.model.TransactionData;
import com.pancredit.service.TransactionService;
import com.pancredit.validation.ApiRequestValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

//Extend HttpServlet class
public class ApiServlet extends HttpServlet {

    private final ApiRequestValidator apiRequestValidator = ApiModule.INSTANCE.provideApiRequestValidator();
    private final TransactionService transactionService = ApiModule.INSTANCE.provideCreditService();
    private final ApiJsonHelper apiJsonHelper = new ApiJsonHelper(ApiModule.INSTANCE.getObjectMapper());

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            Collection<TransactionData> all = transactionService.getAll();
            respondAsJson(response, HttpServletResponse.SC_OK, all);
            return;
        }

        apiRequestValidator.validatePathInfo(pathInfo);

        String resourceId = pathInfo.split("/")[1];
        TransactionData one = transactionService.getOne(resourceId);

        respondAsJson(response, HttpServletResponse.SC_OK, one);
    }

    protected void doDelete(HttpServletRequest pReq, HttpServletResponse pResp) throws ServletException, IOException {
        String pathInfo = pReq.getPathInfo();

        apiRequestValidator.validatePathInfo(pathInfo);

        String resourceId = pathInfo.split("/")[1];
        TransactionData one = transactionService.deleteOne(resourceId);

        respondAsJson(pResp, HttpServletResponse.SC_OK, one);
    }

    protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp) throws ServletException, IOException {
        apiRequestValidator.validatePathInfoForPost(pReq.getPathInfo());

        String payload = getPayload(pReq);

        TransactionData transactionData = apiJsonHelper.fromJson(payload, TransactionData.class);

        apiRequestValidator.validatePayload(transactionData);

        transactionService.createOne(transactionData);

        respondAsJson(pResp, HttpServletResponse.SC_CREATED, transactionData);
    }

    protected void doPut(HttpServletRequest pReq, HttpServletResponse pResp) throws ServletException, IOException {
        String pathInfo = pReq.getPathInfo();

        apiRequestValidator.validatePathInfo(pathInfo);

        String resourceId = pathInfo.split("/")[1];

        String payload = getPayload(pReq);

        TransactionData transactionData = apiJsonHelper.fromJson(payload, TransactionData.class);

        apiRequestValidator.validatePayload(transactionData);
        apiRequestValidator.validateResourceIds(resourceId, transactionData);

        transactionService.putOne(resourceId, transactionData);

        respondAsJson(pResp, HttpServletResponse.SC_OK, transactionData);
    }

    public void destroy() {
        // do nothing.
    }

    private <T> void respondAsJson(HttpServletResponse response, int statusCode, T dto) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);

        String json = apiJsonHelper.toJson(dto);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private String getPayload(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        return buffer.toString();
    }
}
