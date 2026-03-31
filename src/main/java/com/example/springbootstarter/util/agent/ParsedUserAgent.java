package com.example.springbootstarter.util.agent;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParsedUserAgent {
    private final String operatingSystem;
    private final String browser;
}
