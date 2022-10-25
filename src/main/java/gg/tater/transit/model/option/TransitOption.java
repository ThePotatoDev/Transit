package gg.tater.transit.model.option;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.lucko.helper.terminable.module.TerminableModule;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class TransitOption implements TerminableModule {

    private final String friendly;
    private boolean enabled;

}
