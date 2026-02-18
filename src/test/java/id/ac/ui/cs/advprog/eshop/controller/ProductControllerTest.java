package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "Sampo Cap Bambang")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(service, times(1)).create(any(Product.class));
    }

    @Test
    void testProductListPage() throws Exception {
        Product product = new Product();
        List<Product> allProducts = Arrays.asList(product);
        when(service.findAll()).thenReturn(allProducts);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attribute("products", allProducts));
    }

    @Test
    void testDeleteProduct() throws Exception {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";

        mockMvc.perform(get("/product/delete/{productId}", productId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(service, times(1)).delete(productId);
    }

    @Test
    void testEditProductPage() throws Exception {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        when(service.findById(productId)).thenReturn(product);

        mockMvc.perform(get("/product/edit/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attribute("product", product));
    }

    @Test
    void testEditProductPost() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", "a0f9de46-90b1-437d-a0bf-d0821dde9096")
                        .param("productName", "Sampo Cap Sapi")
                        .param("productQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(service, times(1)).edit(any(Product.class));
    }
}