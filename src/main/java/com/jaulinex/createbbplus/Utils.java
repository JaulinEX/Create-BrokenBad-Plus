package com.jaulinex.createbbplus;

import net.minecraft.core.component.DataComponentMap;

public class Utils {

    public class Interfaces {
        //My custom interface to implement in the Items class of minecraft using mixins in order to access the component map of the meth items
        public interface EditItemComponents {
            public void setItemComponents(DataComponentMap nMap);
        }


    }


}
