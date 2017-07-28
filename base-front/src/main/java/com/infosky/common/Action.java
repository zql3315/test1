package com.infosky.common;

import java.io.Serializable;

import com.infosky.common.template.Node.Leaf;
import com.infosky.framework.entity.dto.DTO;

public interface Action<K extends Serializable> {

    Object[] doAction(DTO<K> target, Leaf leaf, Object... row);

    boolean doCheck(DTO<K> target, Leaf leaf, Object... row);

    Object doConvert(Object obj, Leaf leaf, Object... row);
}
