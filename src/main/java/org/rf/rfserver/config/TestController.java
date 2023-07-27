package org.rf.rfserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class TestController {
    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        String s3FilePath = s3Uploader.upload(multipartFile, "test");
        return ResponseEntity.ok().body(s3FilePath);
    }
}
