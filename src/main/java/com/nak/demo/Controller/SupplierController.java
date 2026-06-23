package com.nak.demo.Controller;

import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.dto.supplier.SupplierUpdateDto;
import com.nak.demo.exception.model.ResourceNotFoundException;
import com.nak.demo.Service.SupplierService;
import com.nak.demo.dto.supplier.SupplierDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/suppliers")
public class SupplierController {
        @Autowired
        private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<BaseResponseModelWithData> listSuppliers() {
        return supplierService.listSuppliers();
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> createSupplier(@Valid  @RequestBody SupplierDto payload) {
        return supplierService.createSupplier(payload);
    }

    @PutMapping("{supplier_id}")
    public ResponseEntity<BaseResponseModel> updateSupplier(
            @PathVariable("supplier_id") Long supplierId,
            @RequestBody SupplierUpdateDto payload
    ) {
        return supplierService.updateSupplier(supplierId,payload);
    }

    @DeleteMapping("{supplier_id}")
    public ResponseEntity<BaseResponseModel> deleteSupplier(@PathVariable("supplier_id") Long supplierId) {
        return supplierService.deleteSupplier(supplierId);
    }

}