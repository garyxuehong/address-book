package au.com.reece.phoenix.addressbook.contact;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByAddressbook_Id(Long addressId);

    @Query(value = "SELECT a.*\n" +
            "FROM CONTACT a\n" +
            "       INNER JOIN\n" +
            "     (SELECT name,\n" +
            "             PHONE,\n" +
            "             MIN(CONTACT_ID) as id\n" +
            "      FROM CONTACT\n" +
            "      GROUP BY name\n" +
            "     ) AS b\n" +
            "     ON a.NAME = b.NAME\n" +
            "       AND a.CONTACT_ID = b.id\n" +
            "       AND a.phone = b.phone\n" +
            "order by a.CONTACT_ID;\n", nativeQuery = true)
    List<Contact> findUniqueContacts();
}

