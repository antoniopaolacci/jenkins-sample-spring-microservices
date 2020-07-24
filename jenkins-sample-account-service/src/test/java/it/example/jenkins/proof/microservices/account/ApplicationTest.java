package it.example.jenkins.proof.microservices.account;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {
    
	@Autowired
    private MockMvc mockMvc;
    
	
	@Test
    public void getAllAccounts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
	
    @Test
    public void getSingleAccountByCustomerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/customer/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
    
    //.isNotFound() --> java.lang.AssertionError: Status expected:<404> but was:<200>
    @Test
    public void returnsValidSingleAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/444444")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) 
                .andReturn();
    }
  //.isNotFound() --> java.lang.AssertionError: Status expected:<404> but was:<200>
    @Test
    public void returnsNotValidSingleAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    
    @Ignore
    @Test
    public void addNew() throws Exception {
        
    	String newRequest = ""
        		+ "{\"name\":"
        		+ "\"Monorail\","
        		+ "\"description\":"
        		+ "\"Sedate travelling ride.\","
        		+ "\"thrillFactor\":2,"
        		+ "\"vomitFactor\":1}";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
    
}

