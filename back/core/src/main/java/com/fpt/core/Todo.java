package com.fpt.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue
    private long id;
    @Type(type = "uuid-char")
    @Column(unique = true, updatable = false, nullable = false)
    private UUID uuid;

    private String  text;
    private Boolean done;

    @Version
    private int version;

    @PrePersist
    protected void onCreate() {
        this.uuid = UUID.randomUUID();
    }
}
