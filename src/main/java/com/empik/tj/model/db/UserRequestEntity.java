package com.empik.tj.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User_Request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestEntity {

    @Id
    private String login;
    private Long requestCount;
}
