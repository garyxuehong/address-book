package au.com.reece.phoenix.addressbook.contact;

import au.com.reece.phoenix.Utils;
import au.com.reece.phoenix.addressbook.addressbook.Addressbook;
import au.com.reece.phoenix.addressbook.errorHandler.ErrorMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ContactController.class)
public class ContactResourceTest {

    private final Addressbook addressbook = new Addressbook();
    private final Contact contact = new Contact();
    private final Contact newContact = new Contact();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactRepository contactRepository;

    @Before
    public void init() {
        addressbook.setBookName("Work Profile");
        addressbook.setId(1L);
        newContact.setPhone("12345");
        newContact.setName("new Contact");
        Contact newContactWithId = newContact;
        newContactWithId.setContactId(10L);
        contact.setContactId(1L);
        contact.setName("test");
        contact.setPhone("12345");
        contact.setAddressbook(addressbook);
        contactRepository.save(contact);
        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact);
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        when(contactRepository.findByAddressbook_Id(1L)).thenReturn(contactList);
        when(contactRepository.save(newContact)).thenReturn(newContactWithId);
        when(contactRepository.findAll()).thenReturn(contactList);

    }

    @Test
    public void retrieveContact() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/addressbooks/1/contacts/1")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        Contact c = Utils.mapper.readValue(response, Contact.class);
        assertEquals(contact.getContactId(), c.getContactId());
        assertEquals(contact.getName(), c.getName());

    }

    @Test
    public void failToRetrieveContact() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/addressbooks/1/contacts/-1")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        ErrorMessage errorMessage = Utils.mapper.readValue(response, ErrorMessage.class);
        assertEquals("Cannot find contact id: -1", errorMessage.getMessage());
    }

    @Test
    public void retrieveAllContact() throws Exception {
        contactRepository.save(contact);
        MvcResult result = mockMvc.perform(get("/api/addressbooks/1/contacts/")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        Map<String, Object> contacts = (Map<String, Object>) Utils.mapper.readValue(response, List.class).get(0);
        assertEquals(1, contacts.get("contactId"));
        assertEquals("test", contacts.get("name"));
        assertEquals("12345", contacts.get("phone"));
    }

    @Test
    public void createContact() throws Exception {
        mockMvc.perform(post("/api/addressbooks/1/contacts/")
                .contentType(APPLICATION_JSON)
                .content(Utils.mapper.writeValueAsString(newContact))
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

    }

    @Test
    public void failToCreateContact() throws Exception {
        newContact.setPhone("wrong format");
        mockMvc.perform(post("/api/addressbooks/1/contacts/")
                .contentType(APPLICATION_JSON)
                .content(Utils.mapper.writeValueAsString(newContact))
                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void updateContact() throws Exception {
        Contact updateContact = new Contact();
        updateContact.setName("Updated");
        MvcResult result = mockMvc.perform(put("/api/addressbooks/1/contacts/1")
                .contentType(APPLICATION_JSON)
                .content(Utils.mapper.writeValueAsString(updateContact))
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Contact returnContact = Utils.mapper.readValue(json, Contact.class);
        assertEquals("Updated", returnContact.getName());
    }

    @Test
    public void deleteContact() throws Exception {
        mockMvc.perform(delete("/api/addressbooks/1/contacts/1")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void failToDeleteContact() throws Exception {
        mockMvc.perform(delete("/api/addressbooks/1/contacts/-1")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

    }
}