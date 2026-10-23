package com.myspring.demo.account;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.core.io.*;

import com.myspring.demo.account.dto.AccountValidation;
import com.myspring.demo.account.dto.AccountResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired 
    private AccountService accountService;


    @GetMapping("/{id}")
    public AccountResponse getOneAccount(@PathVariable Long id) {
        AccountResponse Account = accountService.getOneAccount(id);
        return Account;
    }

    @GetMapping("/all")
    public List<AccountResponse> getAllAccounts() {
        List<AccountResponse> Accounts = accountService.getAllAccounts();
        return Accounts;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse addOneAccount(@Valid @RequestBody AccountValidation request) {
        AccountResponse Account = accountService.addOneAccount(request);
        return Account;
    }

    @PostMapping("/create-account")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountValidation request) {
        AccountResponse account = accountService.createAccount(request);

        return ResponseEntity.ok(account);
    }

    @PatchMapping("/update/{id}")
    public AccountResponse addOneAccount(@PathVariable Long id, @Valid @RequestBody AccountValidation request) {
        AccountResponse Account = accountService.updateOneAccount(id, request);
        return Account;
    }

    @DeleteMapping("/delete/{id}")
    public AccountResponse deleteOneAccount(@PathVariable Long id){
        AccountResponse Account = accountService.deleteOneAccount(id);
        return Account;
    }

    @DeleteMapping("/delete/{id}/no-response")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneAccountNoContent(@PathVariable Long id){
        accountService.deleteOneAccount(id);
    }


    // LARGE FILES STREAM: Download a PDF file from FileSystem
    @GetMapping("download/{id}/pdf-tunnel-stream")
    public ResponseEntity<Resource> downloadPdfStream(){

            File file = new File("/files/document.pdf");
            // Spring create a stream
            Resource resource = new FileSystemResource(file);

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
            

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .cacheControl(CacheControl.noStore())
                .contentLength(file.length())
                .body(resource);

    }


    // CREATE A STREAM of a pdf file and send it has a StreamingResponseBody
    // fileContent byte should return IOException
    // copies the file in chunks rather than loading it all into memory
    @GetMapping("/documents/{id}/chunks-stream")
    public ResponseEntity<StreamingResponseBody> downloadPdfStreamedChunks(@PathVariable Long id) throws IOException {

        String fileName = "document.pdf";
        Path path = Paths.get("/files/" + fileName);

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return ResponseEntity.notFound().build();
        }

        StreamingResponseBody stream = outputStream -> {
            try (var inputStream = Files.newInputStream(path)) {
                inputStream.transferTo(outputStream);
            }
        };

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .cacheControl(CacheControl.noStore())
                .contentLength(Files.size(path))
                .body(stream);
    }

    // SEND CONTENT IN MEMORY of a pdf file and send it has an array of bytes
    // fileContent byte should return IOException
    // copies the file into memory !!!! should be small !!!!!!
    @GetMapping("/documents/{id}/from-memory-bytes-document")
    public ResponseEntity<byte[]> downloadPdfBytes(@PathVariable Long id) throws IOException {

        String fileName = "document.pdf";
        Path path = Paths.get("/files/" + fileName);

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileContent = Files.readAllBytes(path);
        // should do something with content and then send it

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .cacheControl(CacheControl.noStore())
                .contentLength(fileContent.length)
                .body(fileContent);
    }

    // UPLOADS

}