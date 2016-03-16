package br.unb.sma.utils;

import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class MyJOOQGeneratorStrategy extends DefaultGeneratorStrategy {
    @Override
    public String getJavaClassName(Definition definition, Mode mode) {
        if (mode == Mode.POJO) {
            return super.getJavaClassName(definition, mode).substring(1);
        } else if (mode == Mode.INTERFACE) {
            return "I" + super.getJavaClassName(definition, mode).substring(2);
        } else {
            return super.getJavaClassName(definition, mode);
        }
    }

    @Override
    public String getJavaPackageName(Definition definition, Mode mode) {
        if (mode == Mode.POJO) {
            return "br.unb.sma.entities";
        } else if (mode == Mode.INTERFACE) {
            return "br.unb.sma.interfaces";
        } else {
            return super.getJavaPackageName(definition, mode);
        }
    }
}

