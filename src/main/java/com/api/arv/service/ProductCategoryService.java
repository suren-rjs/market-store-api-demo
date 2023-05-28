package com.api.arv.service;

import com.api.arv.model.document.ProductCategory;
import com.api.arv.repository.crud.ProductCategoryRepository;
import com.api.arv.repository.data.ProductCategoryDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@SuppressWarnings("unused")
@Service
public class ProductCategoryService implements ProductCategoryDtoRepository {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    @Override
    public void save(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    @Override
    public void deleteById(String id) {
        productCategoryRepository.deleteById(id);
    }

    @Override
    public <S extends ProductCategory> Optional<ProductCategory> getOneById(String id) {
        return productCategoryRepository.findOneById(id);
    }

    @Override
    public <S extends ProductCategory> ProductCategory update(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }
}
