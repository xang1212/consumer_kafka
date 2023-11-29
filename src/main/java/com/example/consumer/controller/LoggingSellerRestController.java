package com.example.consumer.controller;

import com.example.consumer.model.LoggingSellerQueryModel;
import com.example.consumer.model.ResponseModel;
import com.example.consumer.service.LoggingSellerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoggingSellerRestController {

    public LoggingSellerRestController(LoggingSellerService loggingSellerService) {this.loggingSellerService = loggingSellerService;}

    private LoggingSellerService loggingSellerService;

    @GetMapping("/sellect/seller")
    public ResponseModel<List<LoggingSellerQueryModel>> sellectSeller(){
        return this.loggingSellerService.getLoggingSeller();
    }

    @GetMapping("/generate/normal/excel")
    public void getNormalExcel(HttpServletRequest request, HttpServletResponse response) {
        this.loggingSellerService.getExcel(request, response);
    }

    @PostMapping("/upload/csv")
    public ResponseModel<Void> uploadCsvFile(@RequestParam("file") MultipartFile file
            , HttpServletRequest request, HttpServletResponse response){
        return this.loggingSellerService.readCsvFile(file, request, response);
    }
}
