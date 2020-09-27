package wtf.pants.bindings.generators.v1;

import org.junit.Test;

import static org.junit.Assert.*;


import wtf.pants.bindings.mappings.ClassMap;
import wtf.pants.bindings.mappings.MemberMap;
import wtf.pants.bindings.mappings.MethodArg;

import java.util.Arrays;
import java.util.List;

public class ClassHeaderTest {

    @Test
    public void testGetMethodArguments() {
        final ClassMap testClassMap = new ClassMap("abc", "a/b/c/Class01", "a/b/c/TestClass");
        final MemberMap testMethod = new MemberMap(true,
                "(Lxyz;FFILxyz;[F[Lxyz;)V",
                "a", "method_01", "doSomething");

        testMethod.getParams().add("otherTestClass");
        testMethod.getParams().add("posX");
        testMethod.getParams().add("posY");
        testMethod.getParams().add("color");
        testMethod.getParams().add("nextTestClass");
        testMethod.getParams().add("floatArray");
        testMethod.getParams().add("classArray");

        testClassMap.getMembers().add(testMethod);

        final ClassMap testClassMap02 = new ClassMap("xyz", "a/b/c/Class02", "a/b/c/OtherTestClass");

        final List<ClassMap> classMapList = Arrays.asList(testClassMap, testClassMap02);

        final ClassHeader classHeader = new ClassHeader(testClassMap);
        List<MethodArg> methodArgs = classHeader.getMethodArguments(testMethod, classMapList);

        assertEquals("Invalid Method Args Size", testMethod.getParams().size(), methodArgs.size());

        assertEquals("OtherTestClass", methodArgs.get(0).getArgumentType());
        assertEquals("otherTestClass", methodArgs.get(0).getArgumentName());
    }

}
