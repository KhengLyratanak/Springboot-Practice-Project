package com.nak.demo.Service;

import com.nak.demo.Model.BaseResponseModel;
import com.nak.demo.Repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public ResponseEntity<BaseResponseModel> createdSupplier ();
}
