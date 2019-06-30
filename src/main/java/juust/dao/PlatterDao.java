package juust.dao;

import juust.model.Platter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlatterDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final BeanPropertyRowMapper<Platter> mapper = new BeanPropertyRowMapper<>(Platter.class);

    public Platter getPlatterByName(String name) {
        return jdbcTemplate.queryForObject("select * from platter where name = ?", mapper, name);
    }
}
