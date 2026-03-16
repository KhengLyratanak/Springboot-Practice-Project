package com.nak.demo.Controller;

import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Model.StockModel;
import com.nak.demo.Model.UpdateStockModel;
import com.nak.demo.Repository.StockRepository;
import com.nak.demo.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @PostMapping()
    public ResponseEntity<BaseResponseModel> createStock(@RequestBody StockModel payload){
        return stockService.createdStocks(payload);
    }
    @GetMapping()
    public ResponseEntity<BaseResponseModelWithData > listStocks(){
        return stockService.listStocks();
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updatedStocks(@PathVariable ("id") Long stockId,@RequestBody UpdateStockModel payload){
        return stockService.adjustQuantity(stockId,payload);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deletedStocks(@PathVariable ("id") Long stockId){
        return stockService.deletedStock(stockId);
    }
}