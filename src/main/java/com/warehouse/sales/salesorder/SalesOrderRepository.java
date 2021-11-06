package com.warehouse.sales.salesorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository
        extends JpaRepository<SalesOrder, Long> {
}
