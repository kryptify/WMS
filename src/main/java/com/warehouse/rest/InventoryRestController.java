package com.warehouse.rest;

import com.warehouse.administration.user.User;
import com.warehouse.country.Country;
import com.warehouse.exception.InvalidValueFoundException;
import com.warehouse.helper.PageHelper;
import com.warehouse.helper.WarehouseHelper;
import com.warehouse.inventory.inventoryrecounciliation.stock.Stock;
import com.warehouse.inventory.inventoryrecounciliation.stock.StockLocation;
import com.warehouse.inventory.inventoryrecounciliation.stock.StockService;
import com.warehouse.sales.salesorder.SalesOrderSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryRestController {

    @Autowired
    private StockService stockService;

    @PostMapping("/stock")
    public List<Stock> addUpdateStock(@Valid @RequestBody List<Stock> theStockList) {

        for (Stock stock : theStockList) {
            String error = stockService.validateStockRequest(stock);
            if (error != null) {
                throw new InvalidValueFoundException("Stock", error);
            }
        }

        for (Stock stock : theStockList) {

            User user = WarehouseHelper.getLoggedInUser();
            stock.setCreatedBy(user);
            stock.setModifiedBy(user);

            List<StockLocation> stockLocationList = stock.getStockLocation();

            for (StockLocation stockLocation : stockLocationList) {
                stockLocation.setCreatedBy(user);
                stockLocation.setModifiedBy(user);
            }
        }

        return stockService.saveStocks(theStockList);
    }

    @GetMapping("/stocks")
    public List<Stock> getAllStocks() {
       return stockService.findAll();
    }

}
