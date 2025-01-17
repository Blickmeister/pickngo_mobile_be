package cz.uhk.fim.bs.pickngo_mobile_be.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerByEmail(String email);
    Customer findCustomerByEmailShort(String emailShort);
}
