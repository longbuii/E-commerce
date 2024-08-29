package com.LB.Ecommerce.service.Impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.LB.Ecommerce.dto.ProductDto;
import com.LB.Ecommerce.dto.Response;
import com.LB.Ecommerce.entity.Category;
import com.LB.Ecommerce.entity.Product;
import com.LB.Ecommerce.exception.NotFoundException;
import com.LB.Ecommerce.mapper.EntityDtoMapper;
import com.LB.Ecommerce.repository.CategoryRepo;
import com.LB.Ecommerce.repository.ProductRepo;
import com.LB.Ecommerce.service.AwsS3Service;
import com.LB.Ecommerce.service.interf.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo  categoryRepo;
    private final EntityDtoMapper entityDtoMapper;
    private final AwsS3Service awsS3Service;
    

    @Override
    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found"));
        String productImageUrl = awsS3Service.saveImageToS3(image);
        
        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(productImageUrl);

        productRepo.save(product);

        return Response.builder()
                    .status(200)
                    .message("Product successfully created")
                    .build();
    }

    @Override
    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("Product Not Found"));

        Category category = null;
        String productImageUrl = null;

        if(categoryId != null){
            category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found"));
        }

        if(category != null) product.setCategory(category);
        if(category != null) product.setName(name);
        if(category != null) product.setPrice(price);
        if(category != null) product.setDescription(description);
        if(category != null) product.setImageUrl(productImageUrl);

        productRepo.save(product);
        return Response.builder()
                .status(200)    
                .message("Product updated successfully")
                .build();    }

    

    @Override
    public Response deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("Product Not Found"));
        productRepo.delete(product);

        return Response.builder()
                .status(200)    
                .message("Product deleted successfully")
                .build(); 

    }

    @Override
    public Response getProductById(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()-> new NotFoundException("Product Not Found"));
        ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);

        return Response.builder()
                .status(200)
                .product(productDto)
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<ProductDto> productList = productRepo.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productList)
                .build();

    }
    
    @Override
    public Response getProductsByCategory(Long categoryId) {
        List<Product> products = productRepo.findByCategoryId(categoryId);
        if(products.isEmpty()){
            throw new NotFoundException("No Products found for this category");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();

    }


    @Override
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepo.findByNameContainingOrDescriptionContaining(searchValue, searchValue);

        if (products.isEmpty()){
            throw new NotFoundException("No Products Found");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());


        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }




}

