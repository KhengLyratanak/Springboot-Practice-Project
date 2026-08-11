package com.nak.demo.controller;

import com.nak.demo.dto.base.Response;
import com.nak.demo.dto.stock.StockResponseDto;
import com.nak.demo.dto.stock.StockDto;
import com.nak.demo.dto.stock.UpdateStockDto;
import com.nak.demo.service.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @PostMapping()
    public ResponseEntity<Response> createStock(@Valid @RequestBody StockDto payload){
         stockService.createdStocks(payload);
         return ResponseEntity.status(HttpStatus.CREATED)
                 .body(Response.success("201","successsss","successfully created stock"));
    }
    @GetMapping()
    public ResponseEntity<Response > listStocks(){

        List<StockResponseDto> stocks =  stockService.listStocks();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","successfully retrieved ",stocks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getStock(@PathVariable ("id") Long stockId){
       StockResponseDto stocks =  stockService.getStock(stockId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","successfully retrived stock",stocks));
    }
    @PatchMapping ("/{id}")
    public ResponseEntity<Response> updatedStocks(  @PathVariable ("id") Long stockId, @Valid @RequestBody UpdateStockDto payload){
         stockService.adjustQuantity(stockId,payload);
         return ResponseEntity.status(HttpStatus.OK)
                 .body(Response.success("200","success","successfully updated stock"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deletedStocks(@PathVariable ("id") Long stockId){
         stockService.deletedStock(stockId);
         return ResponseEntity.status(HttpStatus.OK)
                 .body(Response.success("209","success","successfully deleted stock"));
    }

}