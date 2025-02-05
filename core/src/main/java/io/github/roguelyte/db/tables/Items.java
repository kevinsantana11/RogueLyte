package io.github.roguelyte.db.tables;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Session;

import io.github.roguelyte.core.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Items")
@NoArgsConstructor
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Column(nullable = false)
    @Getter @Setter private String name;

    @Column(nullable = false)
    @Getter @Setter private String assetpath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter @Setter private ItemType type;

    @OneToMany(mappedBy = "item")
    @Getter private Set<Stats> stats;

    public Items(String name, ItemType type, String assetpath) {
        this.name = name;
        this.type = type;
        this.assetpath = assetpath;

    }

    public static List<Items> allItems(Session session) {
        return session.createQuery("FROM Items", Items.class)
            .list();

    }
}
