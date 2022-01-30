package com.rsupport.notice.service.posts;

import com.rsupport.notice.common.FileProperties;
import com.rsupport.notice.web.dto.FilesSaveRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FilesService {
    private final Path rootLocation;

    @Autowired
    public FilesService(FileProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String upload(MultipartFile file) {
        UUID uuid = UUID.randomUUID();

        Path destinationFile = this.rootLocation.resolve(
                        Paths.get(uuid+"_"+file.getOriginalFilename()))
                .normalize().toAbsolutePath();

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destinationFile.toString();
    }

    public List<FilesSaveRequestDto> save(MultipartFile[] files) {
        List<FilesSaveRequestDto> filesDtoList = new ArrayList<>();

        for (MultipartFile file : files) {
            String destinationFile = upload(file);

            FilesSaveRequestDto filesDto = FilesSaveRequestDto.builder()
                    .fileName(file.getName())
                    .filePath(destinationFile)
                    .origFileName(file.getOriginalFilename())
                    .build();
            filesDtoList.add(filesDto);
        }
        return filesDtoList;
    }

    public void delete() {

    }

}
