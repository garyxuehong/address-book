package au.com.reece.phoenix.addressbook.contact;

import au.com.reece.phoenix.addressbook.addressbook.Addressbook;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    @Size(min = 5, message = "Phone field should have at least 5 numbers")
    @Pattern(regexp = "-?\\d+", message = "Phone field must contain only numbers (0-9)")
    private String phone;

    @ManyToOne(cascade = CascadeType.ALL)
    private Addressbook addressbook;

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Addressbook getAddressbook() {
        return addressbook;
    }

    public void setAddressbook(Addressbook addressbook) {
        this.addressbook = addressbook;
    }
}
