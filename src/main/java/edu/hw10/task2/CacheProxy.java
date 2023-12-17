package edu.hw10.task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy {
    private CacheProxy() {
    }

    public static <T> T create(Object originalInstance, Class<T> implementedInterface) {
        return (T) Proxy.newProxyInstance(
            implementedInterface.getClassLoader(),
            new Class[] {implementedInterface},
            new Handler(originalInstance)
        );
    }

    private static class Handler implements InvocationHandler {
        private final Object originalInstance;
        private final Map<Method, Map<Object[], Object>> cachedResults = new HashMap<>();
        private final File tempFile;

        Handler(Object originalInstance) {
            this.originalInstance = originalInstance;
            try {
                tempFile = File.createTempFile(originalInstance.toString() + "-cache-", ".tmp");
                tempFile.deleteOnExit();
                saveOnDisk(new HashMap<>());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getReturnType() == void.class || !method.isAnnotationPresent(Cache.class)) {
                return method.invoke(originalInstance, args);
            }

            if (method.getAnnotation(Cache.class).persist()) {
                return getFromDisk(method, args);
            }
            return cachedResults.computeIfAbsent(method, k -> new HashMap<>()).computeIfAbsent(args, key -> {
                try {
                    return method.invoke(originalInstance, key);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        private Object getFromDisk(Method method, Object[] args) {
            Map<String, Map<Object[], Object>> diskRepr = loadFromDisk();
            Object result =
                diskRepr.computeIfAbsent(method.toString(), k -> new HashMap<>()).computeIfAbsent(args, key -> {
                    try {
                        return method.invoke(originalInstance, key);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            saveOnDisk(diskRepr);
            return result;
        }

        private Map<String, Map<Object[], Object>> loadFromDisk() {
            try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(tempFile))) {
                return (Map<String, Map<Object[], Object>>) stream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        private void saveOnDisk(Map<String, Map<Object[], Object>> diskMap) {
            try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(tempFile))) {
                stream.writeObject(diskMap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
