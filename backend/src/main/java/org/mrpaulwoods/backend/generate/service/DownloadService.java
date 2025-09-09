package org.mrpaulwoods.backend.generate.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.backend.exceptions.AppGeneratorException;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DownloadService {

    private final GenerateService generateService;

    public ByteArrayOutputStream download(@Valid AppRequest appRequest, ByteArrayOutputStream baos) {

        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            // First file
            zos.putNextEntry(new ZipEntry("file1.txt"));
            zos.write("Hello World".getBytes());
            zos.closeEntry();

            // Second file
            zos.putNextEntry(new ZipEntry("file2.txt"));
            zos.write("Another file".getBytes());
            zos.closeEntry();
        } catch (IOException e) {
            throw new AppGeneratorException("Unable to create the zip file.");
        }

        return baos;
    }

}
