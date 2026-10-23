package com.myspring.demo.files;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.myspring.demo.exceptions.StorageException;
import com.myspring.demo.exceptions.StorageFileNotFoundException;


@Service
public class FileSystemStorageService implements StorageServiceInterface {

  private final Path storagePath;

  @Autowired
  public FileSystemStorageService(StorageProperties properties) {    
        if(properties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty."); 
        }

    this.storagePath = Paths.get(properties.getLocation());
  }


  public void store(MultipartFile file) {
    try {
      if (file.isEmpty()) {
        throw new StorageException("Cannot store empty file.");
      }

      Path destinationFilePath = this.storagePath.resolve(
          Paths.get(file.getOriginalFilename()))
          .normalize().toAbsolutePath();

      if (!destinationFilePath.getParent().equals(this.storagePath.toAbsolutePath())) {
        throw new StorageException(
            "Cannot store file outside current directory.");
      }

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFilePath,
          StandardCopyOption.REPLACE_EXISTING);
      }
    }
    catch (IOException e) {
      throw new StorageException("Failed to store file: ", e);
    }
  }


  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.storagePath, 1)
        .filter(path -> !path.equals(this.storagePath))
        .map(this.storagePath::relativize);
    }
    catch (IOException e) {
      throw new StorageException("Failed to read stored files: ", e);
    }

  }


  public Path load(String filename) {
    return storagePath.resolve(filename);
  }

 
  public Resource loadAsResource(String filename) {
    try {
      Path filePath = load(filename);
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      }
      else {
        throw new StorageFileNotFoundException(
            "File path does not exist, or is this file not readable: " + filename);

      }
    }
    catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Url for this file is mal formed: " + filename, e);
    }
  }


  public void deleteAll() {
    FileSystemUtils.deleteRecursively(storagePath.toFile());
  }


  public void init() {
    try {
      Files.createDirectories(storagePath);
    }
    catch (IOException e) {
      throw new StorageException("Could not initialize 'Storage': ", e);
    }
  }
}
