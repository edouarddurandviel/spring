package com.myspring.demo.files;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.myspring.demo.exceptions.StorageFileNotFoundException;

import org.springframework.http.HttpHeaders;

public class FileController {

    private final FileSystemStorageService storage;

    @Autowired 
    public FileController(FileSystemStorageService storage) {
        this.storage = storage;
    }

    @GetMapping("/files/list-model")
    public Model listUploadFiles(Model model){
         model.addAttribute("files", this.storage.loadAll().map(
        path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
            "serveFile", path.getFileName().toString()).build().toUri().toString())
        .collect(Collectors.toList()));

    return model;

    }

    @GetMapping("/files/{...fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFiles(@PathVariable String fileName){
        Resource file = storage.loadAsResource(fileName);
        if(file == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, 
            "attachment, filename=\"" + file.getFilename() + "\"").body(file);
    };

    @PostMapping("/files/post")
    @ResponseBody
    public ResponseEntity<String> uploadFiles(@RequestParam("file") MultipartFile file){
        storage.store(file);

        return ResponseEntity.ok("Files uploaded");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        // Path and File 
        // (toAbsolutePath, createDirectories, resolve, getOriginalFileName, 
        // getFileName, inputStream, getInputStream, copy
        // StandardCopyOption.REPLACE_EXISTING)
        Path path = Paths.get("/files").toAbsolutePath();
        Files.createDirectories(path);

        Path destination = path.resolve(Paths.get(file.getOriginalFilename()).getFileName());

        try (InputStream input = file.getInputStream()) {
            Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
        }


        return ResponseEntity.ok("File uploaded successfully");
    }




    
    @ExceptionHandler(StorageFileNotFoundException.class)
     public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    
}
