package com.xaoilin.translate.services;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ParseService {

    public String parseContent(MultipartFile file) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        return IOUtils.toString(stream, StandardCharsets.UTF_8);
    }
}
