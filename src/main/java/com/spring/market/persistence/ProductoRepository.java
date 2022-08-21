package com.spring.market.persistence;
import java.util.List;
import java.util.Optional;

import com.spring.market.domain.Product;
import com.spring.market.domain.repository.ProductRepository;
import com.spring.market.persistence.crud.ProductoCrudRepository;
import com.spring.market.persistence.entity.Producto;
import com.spring.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductoRepository implements ProductRepository {
    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> getAll() {
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return mapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(mapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantify) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantify, true);
    return productos.map(prods -> mapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(int productId) {

        return productoCrudRepository.findById(productId).map(producto -> mapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        Producto producto =mapper.toProducto(product);
        return mapper.toProduct(productoCrudRepository.save(producto));
    }

    //Eliminar un producto
    @Override
    public void delete(int productId) {
        productoCrudRepository.deleteById(idProducto);
    }

}
