package com.nak.demo.Controller;

import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Service.SupplierService;
import com.nak.demo.dto.supplier.SupplierDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/suppliers")
public class SupplierController {
        @Autowired
        private SupplierService supplierService;

        @GetMapping
       public ResponseEntity<BaseResponseModelWithData> listSupplier(){
            return supplierService.listSuppliers();
        }

        @PostMapping
        public ResponseEntity<BaseResponseModel> createSupplier(@RequestBody SupplierDto dto){
            return supplierService.createdSupplier(dto);
        }

        @PutMapping({"supplier_id"})
        public ResponseEntity<BaseResponseModel> updateSupplier
                (@PathVariable ("supplier_id") Long supplierId ,
                 @RequestBody SupplierDto payload){
            return supplierService.updateSuppliers(payload,supplierId);
        }

}
