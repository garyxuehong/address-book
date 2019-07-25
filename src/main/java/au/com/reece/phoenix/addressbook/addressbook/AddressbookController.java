package au.com.reece.phoenix.addressbook.addressbook;

import au.com.reece.phoenix.addressbook.errorHandler.AddressBookNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addressbooks")
public class AddressbookController {
    private final AddressbookRepository addressbookRepository;

    public AddressbookController(AddressbookRepository addressbookRepository) {
        this.addressbookRepository = addressbookRepository;
    }

    @GetMapping("/")
    public List<Addressbook> retrieveAllAddressBooks() {
        return addressbookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Addressbook> retrieveAddressBook(@PathVariable long id) {
        Optional<Addressbook> addressbook = addressbookRepository.findById(id);

        if (!addressbook.isPresent())
            throw new AddressBookNotFoundException("Cannot find AddressBook with id: " + id);
        return ResponseEntity.ok(addressbook.get());
    }
}
