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

    public ResponseEntity<BaseResponseModel> createdSupplier(SupplierDto payload) {
        if (supplierRepository.existsByName(payload.getName())) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new BaseResponseModel(
                            "fail",
                            "supplier name already existed"));
        }
        Supplier supplier = mapper.toEntity(payload);
        supplierRepository.save(supplier);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel(
                        "Success",
                        "successfully created Supplier"));
    }

    public ResponseEntity<BaseResponseModelWithData> listSuppliers() {
        List<Supplier> supplier = supplierRepository.findAll();
        List<SupplierResponseDto> dtos = mapper.toDtoList(supplier);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModelWithData(
                        "Success",
                        "successfully retrieved supplier"
                        , supplier));
    }

    public ResponseEntity<BaseResponseModel> updateSuppliers(SupplierDto dto, Long supplierId) {
        Optional<Supplier> existing = supplierRepository.findById(supplierId);

        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel(
                            "fail",
                            "supplier not found with id :" + supplierId));
        }
        Supplier updateSuppliers = existing.get();
        mapper.updateEntityFromDto(updateSuppliers, dto);
        supplierRepository.save(updateSuppliers);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel(
                        "success",
                        "successfully updated supplier"));

    }
    public ResponseEntity<BaseResponseModel> deleteSupplier(Long supplierId){
        if (!supplierRepository.existsById(supplierId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel(
                            "fail",
                            "supplier not found with id:"+supplierId));
        }
        supplierRepository.deleteById(supplierId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new BaseResponseModel(
                            "success",
                            "successfully deleted supplier id:"+supplierId));
    }

}
