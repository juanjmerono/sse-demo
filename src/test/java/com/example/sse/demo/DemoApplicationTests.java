package com.example.sse.demo;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void sseTest() throws Exception {
		String rPid = UUID.randomUUID().toString();
		// Emulate browser quit
		MvcResult asyncResult = 
			mockMvc.perform(get("/news?pid="+rPid))
					.andExpect(request().asyncStarted())
					.andReturn();
		asyncResult = 
			mockMvc.perform(get("/news?pid="+rPid))
				.andExpect(request().asyncStarted())
				.andReturn();		
		MvcResult mvcResult = 
			mockMvc.perform(asyncDispatch(asyncResult))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "text/event-stream"))
				.andReturn();

		String response = mvcResult.getResponse().getContentAsString();
		// Response: data:{...}
		JSONObject json = new JSONObject(response.substring(5));
		String texto = json.getString("texto");
		int number = json.getInt("number");
		assertTrue(rPid.equals(texto));
		assertTrue(number>0);
		assertTrue(number<100);
	}


}
