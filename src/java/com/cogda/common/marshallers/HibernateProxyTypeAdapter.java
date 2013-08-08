package com.cogda.common.marshallers;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.io.IOException;

/**
 * This TypeAdapter unproxies Hibernate proxied objects, and serializes them
 * through the registered (or default) TypeAdapter of the base class.
 */
//public class HibernateProxyTypeAdapter extends TypeAdapter<HibernateProxy> {
//
//    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
//        @Override
//        @SuppressWarnings("unchecked")
//        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
//            System.out.println("HibernateProxyTypeAdapter:");
//            System.out.println("\t" + type.getRawType());
//            System.out.println("\t" + HibernateProxy.class.isAssignableFrom(type.getRawType()));
//            return (HibernateProxy.class.isAssignableFrom(type.getRawType())
//                    ? (TypeAdapter<T>) new HibernateProxyTypeAdapter((TypeAdapter)gson.getAdapter(TypeToken.get(type.getRawType().getSuperclass())))
//                    : null);
//        }
//    };
//    private final TypeAdapter<Object> delegate;
//
//    private HibernateProxyTypeAdapter(TypeAdapter<Object> delegate) {
//        this.delegate = delegate;
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @Override
//    public void write(JsonWriter out, HibernateProxy value) throws IOException {
//        if (value == null) {
//            out.nullValue();
//            return;
//        }
//        delegate.write(out, value.getHibernateLazyInitializer()
//                .getImplementation());
//    }
//
//    @Override
//    public HibernateProxy read(JsonReader in) throws IOException {
//        throw new UnsupportedOperationException("Not supported");
//    }
//}

/**
 * This TypeAdapter unproxies Hibernate proxied objects, and serializes them
 * through the registered (or default) TypeAdapter of the base class.
 */
public class HibernateProxyTypeAdapter extends TypeAdapter<HibernateProxy> {

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @Override
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            return (HibernateProxy.class.isAssignableFrom(type.getRawType()) ? (TypeAdapter<T>) new HibernateProxyTypeAdapter(gson) : null);
        }
    };
    private final Gson context;

    private HibernateProxyTypeAdapter(Gson context) {
        this.context = context;
    }

    @Override
    public HibernateProxy read(JsonReader in) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void write(JsonWriter out, HibernateProxy value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        // Retrieve the original (not proxy) class
        Class<?> baseType = Hibernate.getClass(value);
        // Get the TypeAdapter of the original class, to delegate the serialization
        TypeAdapter delegate = context.getAdapter(TypeToken.get(baseType));
        // Get a filled instance of the original class
        Object unproxiedValue = ((HibernateProxy) value).getHibernateLazyInitializer()
                .getImplementation();
        // Serialize the value
        delegate.write(out, unproxiedValue);
    }
}
