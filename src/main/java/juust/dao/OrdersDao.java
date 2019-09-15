package juust.dao;

import juust.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class OrdersDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertOrder(Order o ) {
        jdbcTemplate.update("insert into orders (price, order_name, date, created, phone, email, person_name) values (?, ?, ?, ?, ?, ?, ?)",
            o.getPrice(), o.getOrderName(), o.getDate(), o.getCreated(), o.getPhone(), o.getEmail(), o.getPersonName());
    }

    public Long getOrderId() {
        return jdbcTemplate.queryForObject("select id from orders order by id desc limit 1", Long.class);
    }

    public List<Timestamp> getOrderDates() {
        return jdbcTemplate.query("select date from orders where date > ?", (rs, rowNum) ->
            rs.getTimestamp("date"), new Date()
        );
    }
}
