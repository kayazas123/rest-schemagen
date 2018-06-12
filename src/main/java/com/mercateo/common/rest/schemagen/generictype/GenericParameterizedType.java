/**
 * Copyright © 2015 Mercateo AG (http://www.mercateo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mercateo.common.rest.schemagen.generictype;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.googlecode.gentyref.GenericTypeReflector;

public class GenericParameterizedType<T> extends GenericType<T> {

    private final ParameterizedType type;

    GenericParameterizedType(ParameterizedType type, Class<T> rawType) {
        super(rawType);
        this.type = requireNonNull(type);
    }

    @Override
    public ParameterizedType getType() {
        return type;
    }

    @Override
    public String getSimpleName() {
        return getRawType().getSimpleName();
    }

    @Override
    public GenericType<?> getContainedType() {
        Type[] actualTypeArguments = type.getActualTypeArguments();
        if (actualTypeArguments.length > 1) {
            throw new IllegalStateException(type + " not supported for subtyping");
        }
        return GenericType.of(actualTypeArguments[0], getRawType());
    }

    @Override
    public GenericType<? super T> getSuperType() {
        final Class<? super T> superclass = getRawType().getSuperclass();
        return superclass != null ? GenericType.of(GenericTypeReflector.getExactSuperType(type,
                superclass), superclass) : null;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
