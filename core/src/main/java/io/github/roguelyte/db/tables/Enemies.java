package io.github.roguelyte.db.tables;

import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Enemies")
@NoArgsConstructor
public class Enemies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Column(nullable = false)
    @Getter @Setter private String name;

    @Column(nullable = false)
    @Getter @Setter private String assetpath;

    @OneToOne(optional = false)
    @Getter @Setter private Entities entity;

    public Enemies(String name, String assetpath) {
        this.name = name;
        this.assetpath = assetpath;

    }

    public static List<Enemies> allEnemies(Session session) {
        return session.createQuery("FROM Enemies", Enemies.class)
            .list();

    }
}
