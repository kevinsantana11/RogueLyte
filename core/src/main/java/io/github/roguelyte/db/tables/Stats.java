package io.github.roguelyte.db.tables;

import java.util.Map;
import java.util.Map.Entry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToOne(fetch=FetchType.EAGER)
    @Getter @Setter private Items item;

    public Stats(String name, Float startRange, Float endRange, Items item) {
        this.name = name;
        this.startRange = startRange;
        this.endRange = endRange;
        this.item = item;
    }

    public Entry<Float, Float> toEntry() {
        return Map.entry(startRange, endRange);
    }
    
}
