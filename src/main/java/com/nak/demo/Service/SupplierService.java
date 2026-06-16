package com.nak.demo.Service;

import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.Repository.SupplierRepository;
import com.nak.demo.dto.stock.UpdateStockDto;
import com.nak.demo.dto.supplier.SupplierDto;
import com.nak.demo.dto.supplier.SupplierResponseDto;
import com.nak.demo.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nak.demo.Entity.Supplier;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper mapper;

    public ResponseEntity<BaseResponseModelWithData> listSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new BaseResponseModelWithData(
                                "success",
                                "successfully retrieved suppliers",
                                mapper.toDtoList(suppliers)
                        )
                );
    }

    public ResponseEntity<BaseResponseModel> createSupplier(SupplierDto dto) {
        // if duplicate supplier name , then reject
        if(supplierRepository.existsByName(dto.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new BaseResponseModel("fail","supplier already existed with name: " + dto.getName()));
        }

        Supplier supplier = mapper.toEntity(dto);

        supplierRepository.save(supplier);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully created supplier"));
    }

    public ResponseEntity<BaseResponseModel> updateSupplier(Long supplierId, SupplierDto dto) {
        Optional<Supplier> existingSupplier = supplierRepository.findById(supplierId);

        // if supplier not found, return 404
        if(existingSupplier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","supplier not found with id: " + supplierId));
        }

        Supplier supplier = existingSupplier.get();
        mapper.updateEntityFromDto(supplier,dto);

        supplierRepository.save(supplier);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully updated supplier"));
    }

    public ResponseEntity<BaseResponseModel> deleteSupplier(Long supplierId) {
        if(!supplierRepository.existsById(supplierId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","supplier not found with id: " + supplierId));
        }

        supplierRepository.deleteById(supplierId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully deleted supplier"));
    }

}