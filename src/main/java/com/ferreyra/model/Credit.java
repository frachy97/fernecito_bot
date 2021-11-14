package com.ferreyra.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Credit")
@Table(name = "credits")
public class Credit {

    public Credit(User user, Double price, Boolean active, String hashCode) {
        this.user = user;
        this.price = price;
        this.active = active;
        this.hashCode = hashCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_credit")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_id_user")
    @JsonBackReference
    private User user;

    @Column(name = "price")
    private Double price;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "hash_code")
    private String hashCode;

    public Integer getId() { return id; }

    public User getUser() { return user; }

    public Double getPrice() { return price; }

    public Boolean getActive() { return active; }

    public LocalDateTime getDate() { return date; }

    public String getHashCode() { return hashCode; }
}
