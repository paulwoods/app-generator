package org.mrpaulwoods.backend.generate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.backend.utils.FileBuilderName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1/file-builder-name")
@Slf4j
@RequiredArgsConstructor
public class FileBuilderNameController {

    @GetMapping
    public List<Map<String, String>> list() {
        log.info("list");

        return Stream.of(FileBuilderName.values())
                .map(v -> Map.of("name", v.name()))
                .toList();
    }

}
