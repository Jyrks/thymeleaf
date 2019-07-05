package juust.dao;

import juust.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrdersDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertOrder(Order o ) {
        jdbcTemplate.update("insert into orders (price, order_name, date, created, phone, email, person_name) values (?, ?, ?, ?, ?, ?, ?)",
            o.getPrice(), o.getOrderName(), o.getDate(), o.getCreated(), o.getPhone(), o.getEmail(), o.getPersonName());
    }

    public Long getOrderId() {
        return jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
    }
}
