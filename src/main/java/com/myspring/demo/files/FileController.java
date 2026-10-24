package com.myspring.demo.files;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

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
    private final StorageProperties properties;

    public FileController(FileSystemStorageService storage, StorageProperties properties) {
        this.storage = storage;
        this.properties = properties;
    }


    @GetMapping("/files/list-model")
    public Model listUploadFiles(Model model){

         model.addAttribute("files", storage.loadAll()
         .map(path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
            "serveOneFile", path.getFileName().toString())
            .build()
            .toUri()
            .toString()
        )
        .collect(Collectors.toList()));

    return model;

    }

    @GetMapping("/file/{...fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveOneFile(@PathVariable String fileName){

        Resource fileResource = storage.loadAsResource(fileName);

        if(fileResource == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, 
            "attachment, filename=\"" + fileResource.getFilename() + "\"").body(fileResource);
    };

    // methode 1.
    @PostMapping("/file/post")
    @ResponseBody
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        storage.store(file);

        return ResponseEntity.ok("File has been uploaded");
    }

    // methode 2.
    @PostMapping("/upload-pdf")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        Path path = Paths.get(properties.getLocation()).toAbsolutePath();
        if(!Files.isDirectory(path)){
            Files.createDirectories(path);
        }
       

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
