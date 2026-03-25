package com.nak.demo.Service;

import com.nak.demo.Entity.Product;
import com.nak.demo.Entity.Stock;
import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Repository.ProductRepository;
import com.nak.demo.dto.stock.StockDto;
import com.nak.demo.dto.stock.UpdateStockDto;
import com.nak.demo.Repository.StockRepository;
import com.nak.demo.dto.stock.StockResponseDto;
import com.nak.demo.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockMapper mapper;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<BaseResponseModelWithData> listStocks() {
        List<Stock> stocks = stockRepository.findAll();
        List<StockResponseDto> dtos = mapper.toDtoList(stocks);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success", "successfully retrieved stocks", dtos));

    }
    public ResponseEntity<BaseResponseModelWithData> getStock(Long stockId){
        Optional<Stock> stock = stockRepository.findById(stockId);
        if (stock.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModelWithData("fail","stock not found with id: "+stockId,"null"));
        }
        StockResponseDto dto = mapper.toDto(stock.get());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData("success","successfully retrieved stock  ",stock.get()));

    }


    public ResponseEntity<BaseResponseModel> createdStocks(StockDto stock) {
       //product not found
        if(!productRepository.existsById(stock.getProductId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail", "product not found :"+stock.getProductId())
                    );

        }
        Stock stockEntity = mapper.toEntity(stock);
        stockRepository.save(stockEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "successfully created stock"));
    }

    public ResponseEntity<BaseResponseModel> adjustQuantity(Long stockId, UpdateStockDto updateStock) {
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