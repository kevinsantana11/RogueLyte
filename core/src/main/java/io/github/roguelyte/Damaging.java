package io.github.roguelyte;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Damaging {
    String id;
    float amt;
}
