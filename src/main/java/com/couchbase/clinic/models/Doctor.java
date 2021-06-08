package com.couchbase.clinic.models;

import com.couchbase.client.java.repository.annotation.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    private String id;
    private String firstName;
    private String lastName;
}
