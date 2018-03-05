package com.github.vedenin.atoms.depedency;

import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atoms.depedency.exceptions.DependencyInjectionAtomException;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * DependencyInjectionAtom using Guice
 *
 * Created by slava on 03.03.18.
 */
@SuppressWarnings("WeakerAccess")
public class DependencyInjectionAtom {
    private Injector injector;

    public static class ApplicationModule extends AbstractModule {
        @Override
        protected void configure() { //todo add binding
        }
    }

    public <T> T getInstance(Class<T> cls) {
        try {
            return getInjector().getInstance(cls);
        } catch (DependencyInjectionAtomException exp) {
            throw new DependencyInjectionAtomException("DependencyInjectionAtom.getInstance ", exp);
        }
    }

    private Injector getInjector() {
        if(injector == null) {
            injector = Guice.createInjector(new ApplicationModule());
        }
        return injector;
    }

    @BoilerPlate
    public static DependencyInjectionAtom create() {
        return new DependencyInjectionAtom();
    }
}
