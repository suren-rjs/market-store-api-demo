package com.market.store.service;

import com.market.store.model.document.ProductItem;
import com.market.store.model.dto.request.product.ProductItemDto;
import com.market.store.repository.crud.ProductItemRepository;
import com.market.store.repository.data.ProductDtoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Service
public class ProductService implements ProductDtoRepository {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductItemRepository productItemRepository;


    @Override
    public <S extends ProductItem> void save(ProductItemDto productItemDto) {
        ProductItem productItem = modelMapper.map(productItemDto, ProductItem.class);
        productItemRepository.save(productItem);
    }

    @Override
    public void deleteById(String id) {
        try {
            getById(id);
            productItemRepository.deleteById(id);
        } catch (Exception ignored) {
        }
    }

    @Override
    public <S extends ProductItem> void updateById(ProductItemDto updatedDetails) throws Exception {
        try {
            ProductItem productItem = getById(updatedDetails.getId()).orElse(null);
            if (productItem == null) throw new NotFoundException("Product not found");
            productItem = modelMapper.map(updatedDetails, ProductItem.class);
            productItemRepository.save(productItem);
        } catch (Exception e) {
            throw new Exception("Unable to update");
        }
    }

    @Override
    public <S extends ProductItem> Optional<S> getById(String id) throws NotFoundException {
        Optional<ProductItem> productItem = productItemRepository.findOneById(id);
        if (productItem.isPresent()) {
            return productItemRepository.findOneById(id);
        } else {
            throw new NotFoundException("Product not exist");
        }
    }

    @Override
    public List<ProductItem> getAllProducts() {
        return productItemRepository.findAll();
    }
}
