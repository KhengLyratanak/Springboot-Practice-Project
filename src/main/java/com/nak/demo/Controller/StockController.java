package com.nak.demo.Controller;

import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.exception.model.ResourceNotFoundException;
import com.nak.demo.dto.stock.StockDto;
import com.nak.demo.dto.stock.UpdateStockDto;
import com.nak.demo.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @PostMapping()
    public ResponseEntity<BaseResponseModel> createStock(@RequestBody StockDto payload){
        return stockService.createdStocks(payload);
    }
    @GetMapping()
    public ResponseEntity<BaseResponseModelWithData > listStocks(){
        return stockService.listStocks();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseModelWithData> getStock(@PathVariable ("id") Long stockId){
        return stockService.getStock(stockId);

    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updatedStocks(@PathVariable ("id") Long stockId,@RequestBody UpdateStockDto payload){
        return stockService.adjustQuantity(stockId,payload);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deletedStocks(@PathVariable ("id") Long stockId){
        return stockService.deletedStock(stockId);
    }

}