package juust.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class RestrictionDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertRestriction(Date date) {
        jdbcTemplate.update("insert into restriction (date, created) values (?, ?)", date, new Date());
    }

    public List<Timestamp> getRestrictions() {
        return jdbcTemplate.query("select date from restriction", (rs, rowNum) ->
            rs.getTimestamp("date")
        );
    }
}
