package com.qiong.handshaker.data.properties;

import com.qiong.handshaker.data.properties.innerMy.PropertiesMyInnerSecurity;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "my", ignoreInvalidFields = true)
public class PropertiesMy {

    private String interviewHeader;
    private String documentFolder;

    private Map<String, String> author;

    private PropertiesMyInnerSecurity security;
}
