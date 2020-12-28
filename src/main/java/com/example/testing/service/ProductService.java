package com.example.testing.service;


import com.example.testing.dto.DealerDto;
import com.example.testing.dto.ProductDto;
import com.example.testing.entity.Dealer;
import com.example.testing.entity.Product;
import com.example.testing.exception.ResourceNotFoundException;
import com.example.testing.repository.ProductRepository;
import com.example.testing.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;


    public ProductService(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public Page<ProductDto> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return MapperUtil.mapEntityPageIntoDtoPage(products, ProductDto.class);
    }

    public Page<ProductDto> getAllProducts(DealerDto dealerDto, Pageable pageable) {

        Page<Product> products = productRepository.findByDealersIn(Collections.singletonList(mapper.map(dealerDto, Dealer.class)), pageable);
        return MapperUtil.mapEntityPageIntoDtoPage(products, ProductDto.class);
    }

    public ProductDto getProductById(Long id) {
        return mapper.map(getProduct(id), ProductDto.class);
    }

    private Product getProduct(Long id) {
        // Getting the requiring product or throwing exception if not found
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);
        productRepository.save(product);
        return mapper.map(product, ProductDto.class);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = getProduct(id);
        product.setName(productDto.getName());
        productRepository.save(product);
        return mapper.map(product, ProductDto.class);
    }

    public void deleteProduct(Long id) {
        productRepository.delete(getProduct(id));
    }
}