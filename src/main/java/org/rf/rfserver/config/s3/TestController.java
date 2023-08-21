package org.rf.rfserver.config.s3;

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

    /* TestController 사용법
    * 해당 컨트롤러는 MultipartFile 형태로 파일을 전송합니다.
    * 사용 시 아래와 비슷한 형식으로 컨트롤러에 적용시켜주면 됩니다.
    * fileUpload 함수의 dirName은 해당하는 폴더가 S3 버킷에 들어가 있어야만 정상적으로 동작하니 버킷에 해당 폴더가 있는지 꼭 확인부탁드립니다.
    * 현재 구현해놓은 PartyController 부분을 보시면 이해가 더 잘 되니 사용하실때 참고하시면 좋을 것 같습니다.
    */
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        String s3FilePath = s3Uploader.fileUpload(multipartFile, "test");
        return ResponseEntity.ok().body(s3FilePath);
    }

}
