package org.example.labeebsystem.DTO_in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlDTO {
    private String join_url;
    private String password;
    private String sessionId;
}
