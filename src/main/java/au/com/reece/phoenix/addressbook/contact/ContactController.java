package au.com.reece.phoenix.addressbook.contact;

import au.com.reece.phoenix.addressbook.errorHandler.ContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addressbooks/")
public class ContactController {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("{addressId}/contacts")
    public List<Contact> retrieveAllContacts(@PathVariable long addressId) {
        return contactRepository.findByAddressbook_Id(addressId);
    }

    @GetMapping("{addressId}/contacts/{id}")
    public ResponseEntity<Contact> retrieveContact(@PathVariable long id, @PathVariable long addressId) {
        Optional<Contact> contact = contactRepository.findById(id);

        if (!contact.isPresent())
            throw new ContactNotFoundException("Cannot find contact id: " + id);
        return ResponseEntity.ok(contact.get());
    }

    @DeleteMapping("{addressId}/contacts/{id}")
    public ResponseEntity deleteAddressBook(@PathVariable long id, @PathVariable long addressId) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (!contact.isPresent())
            throw new ContactNotFoundException("Cannot find contact id: " + id);
        contactRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("{addressId}/contacts")
    public ResponseEntity<Object> createContact(@Valid @RequestBody Contact contact, @PathVariable long addressId) {
        Contact savedContact = contactRepository.save(contact);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{contactId}")
                .buildAndExpand(savedContact.getContactId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("{addressId}/contacts/{id}")
    public ResponseEntity<Object> updateContact(@Valid @RequestBody Contact contact, @PathVariable long id, @PathVariable long addressId) {

        Optional<Contact> contactOptional = contactRepository.findById(id);

        if (!contactOptional.isPresent())
            throw new ContactNotFoundException("Contact not found on :: " + id);

        contact.setContactId(id);

        contactRepository.save(contact);

        return ResponseEntity.ok(contact);

    }
}
