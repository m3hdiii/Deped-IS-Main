package com.deped.restcontroller.config;

import com.deped.model.config.client.ClientConfig;
import com.deped.model.config.client.ClientEnumKey;
import com.deped.utils.NetworkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ConfigRestController {
    private static final String BASE_NAME = "config";
    private static final String FETCH_MAPPING = BASE_NAME + "/fetch-all";

    @Autowired
    private HttpServletRequest request;


    @RequestMapping(value = FETCH_MAPPING, method = RequestMethod.POST)
    public ResponseEntity<List<ClientConfig>> fetchAll() {
        List<ClientConfig> clientConfigs = new ArrayList<>();
        ClientConfig resourcePath = new ClientConfig();
        resourcePath.setKey(ClientEnumKey.RESOURCE_BASE_URL);
        resourcePath.setValue(getBaseUrl().concat("/public/"));
        clientConfigs.add(resourcePath);
        ResponseEntity<List<ClientConfig>> response = new ResponseEntity<>(clientConfigs, HttpStatus.OK);
        return response;
    }

    public String getBaseUrl() {
        InetAddress network = null;
        try {
            network = NetworkUtils.getLocalHostLANAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String host = network.getHostAddress();

        if (host == null) {
            return null;
        }

        String formatUrl = "%s://%s%s%s";
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String url = String.format(formatUrl, request.getScheme(), host, serverPort, request.getContextPath());
        return url;
    }
}
