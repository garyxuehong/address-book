package au.com.reece.phoenix.addressbook.errorHandler;

class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String exception) {
        super(exception);
    }

}
