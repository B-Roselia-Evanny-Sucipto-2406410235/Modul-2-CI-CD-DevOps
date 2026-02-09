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
public class EditProductTest {
    @Spy
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        reset(productRepository);
    }

    @Test
    void testEditProduct_Positive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productService.create(product);

        product.setProductName("Sampo Cap Cacing");
        product.setProductQuantity(50);
        productService.edit(product);

        Product editedProduct = productService.findById(product.getProductId());
        assertNotNull(editedProduct);
        assertEquals("Sampo Cap Cacing", editedProduct.getProductName());
        assertEquals(50, editedProduct.getProductQuantity());
    }

    @Test
    void testEditProduct_Negative() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        productService.create(product);

        Product fakeProduct = new Product();
        fakeProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        fakeProduct.setProductName("Sampo Cap Bambu");

        productService.edit(fakeProduct);

        Product originalProduct = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("Sampo Cap Bambang", originalProduct.getProductName());

        assertNull(productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd7"));
    }
}
