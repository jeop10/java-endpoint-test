package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	private final String apiKeyHeaderName = "Api-Key";
	private final String testApiKey = "sup3rs3cr3t";


//	@Test
//	void shouldReturnDefaultMessage() throws Exception {
//		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
//				.andExpect(content().string(containsString("Hello, World")));
//	}

	@Test
	void shouldReturnValidateResultOK() throws Exception {

		ValidateRequest testBody = new ValidateRequest();
		testBody.setCommand("A0");
		testBody.setKey(1234);
		testBody.setLmk(4);
		testBody.setEncoding("V");
		testBody.setOutput("J");

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonBody = ow.writeValueAsString(testBody);

		this.mockMvc.perform(post("/validate")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON)
						.header(this.apiKeyHeaderName, this.testApiKey)
						.content(jsonBody))
				.andExpect(status().isOk());
	}

	@Test
	void shouldReturnValidateResultMissingValue() throws Exception {

		ValidateRequest testBody = new ValidateRequest();
		testBody.setCommand("A0");
		testBody.setKey(1234);
		testBody.setLmk(4);
		testBody.setEncoding("V");

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonBody = ow.writeValueAsString(testBody);

		this.mockMvc.perform(post("/validate")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON)
						.header(this.apiKeyHeaderName, this.testApiKey)
						.content(jsonBody))
				.andExpect(status().is4xxClientError())
				.andExpect(content().json("{\"output\": \"must not be blank\"}"));
	}

	@Test
	void shouldReturnInvalidResponse() throws Exception {

		ValidateRequest testBody = new ValidateRequest();
		testBody.setCommand("A1");
		testBody.setKey(1234);
		testBody.setLmk(4);
		testBody.setEncoding("V");
		testBody.setOutput("J");

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonBody = ow.writeValueAsString(testBody);

		this.mockMvc.perform(post("/validate")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON)
						.header(this.apiKeyHeaderName, this.testApiKey)
						.content(jsonBody))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().json("{\"valid\": \"false\", \"message\": \"Command not valid\"}"));
	}

	@Test
	void shouldReturnValidResponse() throws Exception {

		ValidateRequest testBody = new ValidateRequest();
		testBody.setCommand("A0");
		testBody.setKey(1234);
		testBody.setLmk(4);
		testBody.setEncoding("V");
		testBody.setOutput("J");

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonBody = ow.writeValueAsString(testBody);

		this.mockMvc.perform(post("/validate")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON)
						.header(this.apiKeyHeaderName, this.testApiKey)
						.content(jsonBody))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().json("{\"valid\": \"true\", \"message\": null}"));
	}

	@Test
	void shouldReturnErrorOnApiKey() throws Exception {

		ValidateRequest testBody = new ValidateRequest();
		testBody.setCommand("A0");
		testBody.setKey(1234);
		testBody.setLmk(4);
		testBody.setEncoding("V");
		testBody.setOutput("J");

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonBody = ow.writeValueAsString(testBody);

		this.mockMvc.perform(post("/validate")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON)
						.header(this.apiKeyHeaderName, "iamerror")
						.content(jsonBody))
				.andExpect(status().is4xxClientError())
				.andExpect(content().string(""));
	}
}
