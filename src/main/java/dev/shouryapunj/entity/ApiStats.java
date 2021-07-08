package dev.shouryapunj.entity;

import lombok.*;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "APISTATS")
public class ApiStats {

    @Id
    @Column(name = "apiID", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private RequestMethod method;

    @Column(name = "executionTime", nullable = false)
    private Long executionTime;

    @Column(name = "createdOn", nullable = false)
    private ZonedDateTime createdOn;

    @Column(name = "modifiedOn", nullable = false)
    private ZonedDateTime modifiedOn;

    public ApiStats(String name, RequestMethod method, Long executionTime, ZonedDateTime createdOn, ZonedDateTime modifiedOn) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.method = method;
        this.executionTime = executionTime;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }
}
