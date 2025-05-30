package com.myblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Email;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GitHubUser {
    private String Login;
    private String email;
}
