package au.com.reece.phoenix.addressbook.addressbook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AddressbookRepository extends JpaRepository<Addressbook, Long> {
}
