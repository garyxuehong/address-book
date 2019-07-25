package au.com.reece.phoenix.addressbook.addressbook;

import au.com.reece.phoenix.Utils;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AddressbookController.class)
public class AddressbookResourceTest {

    private final Addressbook addressbook = new Addressbook();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressbookRepository addressbookRepository;

    @Before
    public void init() {
        addressbook.setBookName("Work Profile");
        addressbook.setId(1L);
        List<Addressbook> addressbookList = new ArrayList<>();
        addressbookList.add(addressbook);
        when(addressbookRepository.findById(1L)).thenReturn(Optional.of(addressbook));
        when(addressbookRepository.findAll()).thenReturn(addressbookList);

    }

    @Test
    public void retrieveAllAddressBooks() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/addressbooks/")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        Map<String, Object> addressBooks = (Map<String, Object>) Utils.mapper.readValue(response, List.class).get(0);
        assertEquals(1, addressBooks.get("id"));
        assertEquals("Work Profile", addressbook.getBookName());

    }

    @Test
    public void retrieveAddressBook() throws Exception {
        mockMvc.perform(get("/api/addressbooks/1")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}