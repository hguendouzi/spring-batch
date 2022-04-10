package fr.hguendouzi.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author hguendouzi
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName;
    private String firstName;
    @Column(nullable = false)
    private String email;
    private String address;

}
