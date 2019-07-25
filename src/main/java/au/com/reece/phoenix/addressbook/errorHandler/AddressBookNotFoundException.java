package au.com.reece.phoenix.addressbook.errorHandler;

public class AddressBookNotFoundException extends ResourceNotFoundException {

    public AddressBookNotFoundException(String exception) {
        super(exception);
    }

}