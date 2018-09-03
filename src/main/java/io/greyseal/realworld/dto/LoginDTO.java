package io.greyseal.realworld.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = LoginDTO.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {
	@JsonIgnore
	private String email;
	@JsonIgnore
	private String password;

	private LoginDTO(Builder builder) {
		this.email = builder.email;
		this.password = builder.password;
	}

	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Builder {
		private String email;
		private String password;

		public Builder() {
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		public LoginDTO build() {
			return new LoginDTO(this);
		}
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}
}
