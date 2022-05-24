import model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("uuid98");
/*        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);

        System.out.println("Field name = " + field.getName());
        System.out.println(field.get(resume));

        field.set(resume, "new_uuid");
        System.out.println(resume);*/

        Method method = resume.getClass().getDeclaredMethod("toString");
        method.setAccessible(true);
        String result = (String) method.invoke(resume);
        System.out.println(result);
    }
}
