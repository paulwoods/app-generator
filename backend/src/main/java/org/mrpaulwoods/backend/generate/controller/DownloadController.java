package org.mrpaulwoods.backend.generate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.service.DownloadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/v1/download")
@Slf4j
@RequiredArgsConstructor
public class DownloadController {

    private final DownloadService downloadService;

    /**
     * returns a zip file of the code
     *
     * @param appRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<byte[]> download(@Valid @RequestBody AppRequest appRequest) {
        log.info("download: {}", appRequest);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        downloadService.download(appRequest, baos);

        return ResponseEntity.ok()
                .header("Content-Type", "application/zip")
                .header("Content-Disposition", "attachment; filename=app-generator.zip")
                .body(baos.toByteArray());
    }

}
