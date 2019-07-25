package au.com.reece.phoenix.addressbook.errorHandler;

public class ContactNotFoundException extends ResourceNotFoundException {

    public ContactNotFoundException(String exception) {
        super(exception);
    }

}