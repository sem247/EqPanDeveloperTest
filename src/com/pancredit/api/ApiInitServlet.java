package com.pancredit.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pancredit.config.ApiModule;
import com.pancredit.data.DataStore;
import com.pancredit.data.DatasetProvider;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ApiInitServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiInitServlet.class);

    private final ObjectMapper objectMapper = ApiModule.INSTANCE.getObjectMapper();
    private final DataStore dataStore = DataStore.INSTANCE;
    private final DatasetProvider datasetProvider = new DatasetProvider(objectMapper, dataStore);

    @Override
    public void init() throws ServletException {
        final String dataFilename = getServletContext().getInitParameter("data.filename");
        try (InputStream resource = getServletContext().getResourceAsStream(dataFilename)) {
            final String content = IOUtils.toString(Objects.requireNonNull(resource), StandardCharsets.UTF_8);
            datasetProvider.prepareDataset(content);
        } catch (IOException e) {
            LOGGER.error("Data load failed:: ", e);
            throw new ServletException(e);
        }
    }
}
