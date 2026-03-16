package com.nak.demo.Service;

import com.nak.demo.Entity.Stock;
import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Model.StockModel;
import com.nak.demo.Model.UpdateStockModel;
import com.nak.demo.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public ResponseEntity<BaseResponseModelWithData> listStocks() {
        List<Stock> stocks = stockRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success", "successfully retrieved stocks", stocks));

    }

    public ResponseEntity<BaseResponseModel> createdStocks(StockModel payload) {
        Stock stock = new Stock();
        stock.setQuantity(payload.getQuantity());
        stock.setProductId(payload.getProductId());
        stock.setCreatedAt(LocalDateTime.now());
        stockRepository.save(stock);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "successfully created stock"));
    }

    public ResponseEntity<BaseResponseModel> adjustQuantity(Long stockId,UpdateStockModel updateStock) {
        Optional<Stock> existingStock = stockRepository.findById(stockId);

        // stock not found in DB
        if(existingStock.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","stock not found with id: " + stockId));
        }

        Stock stock = existingStock.get();

        if(updateStock.getOperationType() == 1) { // add
            Long newQty = stock.getQuantity() + updateStock.getQuantity();

            stock.setQuantity(newQty);
        } else if(updateStock.getOperationType() == 2) { // remove
            //  when remove amount > existing amount
            if(stock.getQuantity() < updateStock.getQuantity()) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(new BaseResponseModel("fail","quantity to remove can not be exceeded than existing stock: " + stock.getQuantity()));
            }

            Long newQty = stock.getQuantity() - updateStock.getQuantity();

            stock.setQuantity(newQty);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponseModel("fail","invalid operation type"));
        }

        stock.setUpdatedAt(LocalDateTime.now());
        stockRepository.save(stock);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully adjusted stock quantity"));
    }
        public ResponseEntity<BaseResponseModel> deletedStock(Long stockId){
            if(!stockRepository.existsById(stockId)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new BaseResponseModel("fail","stock not found with id:"+ stockId));
            }
            stockRepository.deleteById(stockId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new BaseResponseModel("success","successfully deleted stock"));
        }
}