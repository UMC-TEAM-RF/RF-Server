package org.rf.rfserver.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class TestController {
    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    public String upload(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException{
        return s3Uploader.upload(multipartFile, "test");
    }
}
