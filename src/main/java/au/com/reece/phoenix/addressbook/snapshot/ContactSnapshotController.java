package au.com.reece.phoenix.addressbook.snapshot;

import au.com.reece.phoenix.addressbook.contact.Contact;
import au.com.reece.phoenix.addressbook.contact.ContactRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/snapshot/contacts")
public class ContactSnapshotController {

    private final ContactRepository contactRepository;

    public ContactSnapshotController(ContactRepository contactResource) {
        this.contactRepository = contactResource;
    }

    @GetMapping("/unique-contacts")
    public List<Contact> getUniqueContacts() {
        return contactRepository.findUniqueContacts();
    }
}
