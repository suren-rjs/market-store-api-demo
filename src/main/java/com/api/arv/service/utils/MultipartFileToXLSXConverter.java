package com.api.arv.service.utils;

import com.api.arv.model.dto.request.product.ProductItemDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MultipartFileToXLSXConverter {
    public List<ProductItemDTO> convert(MultipartFile multipartFile) throws Exception {
        try (InputStream inputStream = multipartFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            String outputFilePath = "/temp/uploaded-file.xlsx";
            try (Workbook outputWorkbook = new XSSFWorkbook()) {
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    Sheet inputSheet = workbook.getSheetAt(i);
                    Sheet outputSheet = outputWorkbook.createSheet(inputSheet.getSheetName());
                    copySheet(inputSheet, outputSheet);
                }

                try (OutputStream outputStream = new FileOutputStream(outputFilePath)) {
                    outputWorkbook.write(outputStream);
                    return getProductItemDTOList(Optional.of(outputWorkbook));
                }
            }
        }
    }

    private void copySheet(Sheet inputSheet, Sheet outputSheet) {
        for (Row inputRow : inputSheet) {
            Row outputRow = outputSheet.createRow(inputRow.getRowNum());
            for (Cell inputCell : inputRow) {
                Cell outputCell = outputRow.createCell(inputCell.getColumnIndex(), inputCell.getCellType());
                switch (inputCell.getCellType()) {
                    case STRING:
                        outputCell.setCellValue(inputCell.getStringCellValue());
                        break;
                    case NUMERIC:
                        outputCell.setCellValue(inputCell.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        outputCell.setCellValue(inputCell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        outputCell.setCellFormula(inputCell.getCellFormula());
                        break;
                }
            }
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private List<ProductItemDTO> getProductItemDTOList(Optional<Workbook> uploadedData) throws Exception {
        if (uploadedData.isEmpty()) {
            throw new Exception("Unable to upload");
        }

        Sheet sheet = uploadedData.get().getSheetAt(0);
        List<ProductItemDTO> productItemDTOList = new ArrayList<>();
        for (Row row : sheet) {
            String name = row.getCell(0).getStringCellValue();
            String description = row.getCell(1).getStringCellValue();
            String category = row.getCell(2).getStringCellValue();
            String subCategory = row.getCell(3).getStringCellValue();
            String subSubCategory = row.getCell(4).getStringCellValue();
            Double mrpPrice = row.getCell(5).getNumericCellValue();
            Double sellingPrice = row.getCell(6).getNumericCellValue();
            Double discount = row.getCell(7).getNumericCellValue();
            String productVariation = row.getCell(8).getStringCellValue();
            Integer stock = (int) row.getCell(9).getNumericCellValue();

            ProductItemDTO data = new ProductItemDTO();
            data.setProductName(name);
            data.setDescription(description);
            data.setCategory(category);
            data.setSubCategory(subCategory);
            data.setSubSubCategory(subSubCategory);
            data.setMrpPrice(mrpPrice);
            data.setSellingPrice(sellingPrice);
            data.setDiscount(discount);
            data.setProductVariation(productVariation);
            data.setStock(stock);

            productItemDTOList.add(data);
        }
        return productItemDTOList;
    }
}
