package ru.ar.deposit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ar.deposit.entity.Deposit;
@Repository
public interface DepositRepository extends CrudRepository<Deposit, Long> {

}
