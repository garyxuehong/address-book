package au.com.reece.phoenix.addressbook.snapshot;

import au.com.reece.phoenix.addressbook.contact.ContactRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ContactSnapshotController.class)
public class ContactSnapshotTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContactRepository contactRepository;

    @Test
    public void getUniqueContacts() throws Exception {
        mockMvc.perform(get("/api/snapshot/contacts/unique-contacts")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}