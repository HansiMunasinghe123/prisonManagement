package lk.prison_management.asset.commendation.service;

import lk.prison_management.asset.commendation.dao.CommendationDao;
import lk.prison_management.asset.commendation.entity.Commendation;
import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommendationService implements AbstractService< Commendation, Integer> {
    private final CommendationDao commendationDao;

    public CommendationService(CommendationDao commendationDao) {
        this.commendationDao = commendationDao;
    }

    public List< Commendation > findAll() {
        return commendationDao.findAll();
    }

    public Commendation findById(Integer id) {
        return commendationDao.getOne(id);
    }

    public Commendation persist(Commendation commendation) {
        return commendationDao.save(commendation);
    }

    public boolean delete(Integer id) {
        commendationDao.deleteById(id);
        return true;
    }

    public List< Commendation > search(Commendation commendation) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Commendation > instituteExample = Example.of(commendation, matcher);
        return commendationDao.findAll(instituteExample);
    }

  public Commendation lastCommendation() {
  return commendationDao.findFirstByOrderByIdDesc();
    }

  public List< Commendation> findByEmployee(Employee employee) {
  return commendationDao.findByEmployee(employee);}

//aluth report ekata



    public List< Commendation> findByCreatedAtIsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return commendationDao.findByCreatedAtIsBetween(startDateTime,endDateTime);
    }



}
