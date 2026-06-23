package com.nak.demo.Service;

import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Model.BaseResponseModelWithData;
import com.nak.demo.dto.supplier.SupplierUpdateDto;
import com.nak.demo.exception.model.DuplicateException;
import com.nak.demo.exception.model.ResourceNotFoundException;
import com.nak.demo.Repository.SupplierRepository;
import com.nak.demo.dto.supplier.SupplierDto;
import com.nak.demo.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nak.demo.Entity.Supplier;

import java.util.List;

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
                throw new DuplicateException("supplier already existed");
        }

        Supplier supplier = mapper.toEntity(dto);

        supplierRepository.save(supplier);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully created supplier"));
    }

    public ResponseEntity<BaseResponseModel> updateSupplier(Long supplierId, SupplierUpdateDto dto) {
        Supplier existingSupplier = supplierRepository.findById(supplierId)

        // if supplier not found, return 404
                .orElseThrow(() ->
                        new ResourceNotFoundException("supplier not found with :"  +supplierId));

        mapper.updateEntityFromDto(existingSupplier,dto);

        supplierRepository.save(existingSupplier);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully updated supplier"));
    }

    public ResponseEntity<BaseResponseModel> deleteSupplier(Long supplierId) {
        if(!supplierRepository.existsById(supplierId)) {
           throw new ResourceNotFoundException("supplier not found with id:"  +supplierId);
        }

        supplierRepository.deleteById(supplierId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully deleted supplier"));
    }

}