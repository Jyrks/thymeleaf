package juust.service;

import juust.dao.RestrictionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static juust.util.DateUtil.createDate;

@Service
public class RestrictionService {

    @Autowired
    private RestrictionDao restrictionDao;

    public void addRestriction(String date) {
        restrictionDao.insertRestriction(createDate(date));
    }
}
