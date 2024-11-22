package ru.ar.bill.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ar.bill.entity.Bill;

@Repository
public interface BillRepository extends CrudRepository<Bill, Long> {

}
