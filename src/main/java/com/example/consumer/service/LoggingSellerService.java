package com.example.consumer.service;

import com.example.consumer.model.LoggingSellerModel;
import com.example.consumer.model.LoggingSellerQueryModel;
import com.example.consumer.model.MarketPlaceModel;
import com.example.consumer.model.ResponseModel;
import com.example.consumer.repository.LoggingSellerNativeRepository;
import com.example.consumer.repository.MarketPlaceNativeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class LoggingSellerService {
    public LoggingSellerService(LoggingSellerNativeRepository loggingSellerNativeRepository){
        this.loggingSellerNativeRepository = loggingSellerNativeRepository;
    }
    private final LoggingSellerNativeRepository loggingSellerNativeRepository;
    public ResponseModel<Integer> insertLoggingSeller(LoggingSellerModel loggingSellerModel) {
        ResponseModel<Integer> result = new ResponseModel<>();

        try {
            int insertedRows = this.loggingSellerNativeRepository.insertLoggingSeller(loggingSellerModel);
            result.setStatus(201);
            result.setDescription("Market inserted successfully.");
            result.setData(insertedRows);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(500);
            log.error("Insert market error: {}", e.getMessage());
        }
        return result;
    }

    public ResponseModel<List<LoggingSellerQueryModel>> getLoggingSeller() {
        ResponseModel<List<LoggingSellerQueryModel>>  result = new ResponseModel<>();
        try {
            List<LoggingSellerQueryModel> loggings = loggingSellerNativeRepository.getLoggingSeller();
            result.setStatus(201);
            result.setDescription("get log successfully.");
            result.setData(loggings);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get log error: {}", e.getMessage());
        }
        return result;
    }

    public void getExcel(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        log.info("getExcel");
        try {
            httpServletResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setHeader("Content-Disposition"
                    , "attachment; filename=" + "excel" + new Date().getTime() + ".xlsx");
            OutputStream outputStream = httpServletResponse.getOutputStream();

            Workbook wb = this.generateCustomerReportExcel();
            wb.write(outputStream);
            outputStream.flush();

        } catch (Exception e) {
            log.info("getExcel error {}", e.getMessage());

            ResponseModel<Void> result = new ResponseModel<>();
            result.setStatus(500);
            result.setDescription("getExcel error " + e.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            httpServletResponse.setHeader("Content-Disposition"
                    , "inline");
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            OutputStream outputStream = null;
            try {
                outputStream = httpServletResponse.getOutputStream();
                outputStream.write(mapper.writeValueAsBytes(result));
                outputStream.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
    private Workbook generateCustomerReportExcel(){

        Workbook wb = new XSSFWorkbook();
        Sheet sheet1 = wb.createSheet("sheet1");
        int row  = 5;
        Row headerRow = sheet1.createRow(row++);
        int headerCol = 0;
        Cell h1 = headerRow.createCell(headerCol++);
        h1.setCellValue("no");
        Cell h2 = headerRow.createCell(headerCol++);
        h2.setCellValue("adventureName");

        Cell h3 = headerRow.createCell(headerCol++);
        h3.setCellValue("itemName");

        Cell h4 = headerRow.createCell(headerCol++);
        h4.setCellValue("createdAt");

        Cell h5 = headerRow.createCell(headerCol++);
        h5.setCellValue("updateAt");

        CellStyle dateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

        List<LoggingSellerQueryModel> datas = this.loggingSellerNativeRepository.getLoggingSeller();
        for (int i = 0; i < datas.size(); i++) {
            int dataCol = 0;
            int rownum = i+1;
            Row dataRow = sheet1.createRow(row++);
            Cell no = dataRow.createCell(dataCol++);
            no.setCellValue(rownum);

            Cell adventureName = dataRow.createCell(dataCol++);
            adventureName.setCellValue(datas.get(i).getAdventureName());

            Cell itemName = dataRow.createCell(dataCol++);
            itemName.setCellValue(datas.get(i).getItemName());

            Cell createdAt = dataRow.createCell(dataCol++);
            createdAt.setCellValue(datas.get(i).getCreatedAt());
            createdAt.setCellStyle(dateStyle);

            Cell updateAt = dataRow.createCell(dataCol++);
            updateAt.setCellValue(datas.get(i).getUpdateAt());
            updateAt.setCellStyle(dateStyle);
        }

        return wb;
    }

    public List<LoggingSellerQueryModel> readCsvFromInputStream(InputStream inputStream) throws IOException {
        List<LoggingSellerQueryModel> result = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String lineData = null;

            while ((lineData = bufferedReader.readLine()) != null) {
                log.info("line data {}", lineData);

                if (lineData.startsWith("no,adventureName,itemName,createdAt,updateAt")) {
                    continue;
                }

                String[] lineDataArray = lineData.split(",");
                LoggingSellerQueryModel x = new LoggingSellerQueryModel();
                log.info("=====> line Data  Array length {}", lineDataArray.length);
                log.info("=====> line Data  Array {}", Arrays.toString(lineDataArray));

                if (lineDataArray.length == 5) {
                    log.info("=====> line DataArray {}", lineDataArray[1]);
                    log.info("=====> line DataArray {}", lineDataArray[2]);
                    log.info("=====> line DataArray {}", lineDataArray[3]);
                    log.info("=====> line DataArray {}", lineDataArray[4]);

                    x.setAdventureName(lineDataArray[1]);
                    x.setItemName(lineDataArray[2]);
                    x.setUpdateAt(parseTimestamp(lineDataArray[4]));
                    x.setCreatedAt(parseTimestamp(lineDataArray[3]));

                    log.info("x {}", x);
                    result.add(x);
                }
                log.info("=======> {}", x.toString());
            }
        }

        log.info("=======> result {}", result);
        return result;
    }



    public ResponseModel<Void> readCsvFile(MultipartFile file, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        ResponseModel<Void> result = new ResponseModel<>();
        result.setStatus(200);
        result.setDescription("OK");

        log.info("readCsvFile");
        try{
            List<LoggingSellerQueryModel> loggingSellerQueryModels = this.readCsvFromInputStream(file.getInputStream());
            log.info("loggingSellerQueryModels  {}", loggingSellerQueryModels);

        } catch (Exception e) {
            log.info("readCsvFile error {}",e.getMessage());
            result.setStatus(500);
            result.setDescription("readCsvFile error "+e.getMessage());
        }
        return result;
    }

    private LocalDateTime parseTimestamp(String timestampString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(timestampString, formatter);
    }

}
