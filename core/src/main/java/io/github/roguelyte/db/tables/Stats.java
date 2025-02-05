package io.github.roguelyte.db.tables;

import java.util.Map;
import java.util.Map.Entry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Stats")
@NoArgsConstructor
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Column(nullable = false)
    @Getter @Setter private String name;

    @Column(nullable = false, name = "start_range")
    @Getter @Setter private Float startRange;

    @Column(nullable = false, name = "end_range")
    @Getter @Setter private Float endRange; 

    @ManyToOne(optional = false)
    @Getter @Setter private Entities entity;

    public Entry<Float, Float> toEntry() {
        return Map.entry(startRange, endRange);
    }
    
}
