package com.example.springbootstarter.util.agent;

import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;

@Component
public class UserAgentParser {
    private final Parser parser = new Parser();

    public ParsedUserAgent parse(String userAgentString) {
        if(userAgentString == null) {
            return new ParsedUserAgent(null, null);
        }

        Client client = parser.parse(userAgentString);

        String browser = client.userAgent.family;
        String os = client.os.family;

        return new ParsedUserAgent(browser, os);
    }
}
