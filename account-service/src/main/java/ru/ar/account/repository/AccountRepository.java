package ru.ar.account.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ar.account.Entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

}
