package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteProductTest {
    @Spy
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        reset(productRepository);
    }

    @Test
    void testDeleteProduct_Positive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        productService.create(product);

        productService.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        List<Product> allProducts = productService.findAll();
        assertTrue(allProducts.isEmpty());
    }

    @Test
    void testDeleteProduct_Negative() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        productService.create(product);

        productService.delete("eb558e9f-1c39-460e-8860-71af6af63bd7");

        List<Product> allProducts = productService.findAll();
        assertEquals(1, allProducts.size());
        assertEquals("Sampo Cap Bambang", allProducts.get(0).getProductName());
    }
}
