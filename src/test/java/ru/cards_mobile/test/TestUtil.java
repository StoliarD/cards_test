package ru.cards_mobile.test;

import java.lang.reflect.Field;

public class TestUtil {

    public static void sneaky(Action action) {
        try {
            action.invoke();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T viaReflection(Object obj, String field) {
        Field declaredField = null;
        boolean access = true;
        try {
            declaredField = obj.getClass().getDeclaredField(field);
            access = declaredField.isAccessible();
            declaredField.setAccessible(true);
            return (T) declaredField.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            if (declaredField != null && !access) declaredField.setAccessible(false);
        }
    }

    public interface Action {
        void invoke() throws Throwable;
    }

}
