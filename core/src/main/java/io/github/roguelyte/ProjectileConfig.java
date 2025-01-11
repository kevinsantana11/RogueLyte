package io.github.roguelyte;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProjectileConfig {
    private final float damage;
    private final float maxDistance;
}
