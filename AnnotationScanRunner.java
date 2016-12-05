package com.baylrock;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.MessageFormat;
import java.util.*;


public class AnnotationScanRunner {
    public static void main( @Annotated("args") String[] args ) throws NoSuchMethodException {
//        // try to scan current class
//        doScan( AnnotationScanRunner.class );
//
//        // try to scan varargs of classes
//        doScan( AnnotationScanRunner.class, Bean.class );

        // try to scan only single method
        doScan( AnnotationScanRunner.class, "test" );

    }


    public static <T> void doScan( Class<T> tClass, String methodName ) throws NoSuchMethodException {
        for (Method method : tClass.getDeclaredMethods()) {
            if (methodName.equals( method.getName() )) {
                doScan( method );
            }
        }
    }

    public static void doScan( Class tClass ) throws NoSuchMethodException {
        for (Method method : tClass.getDeclaredMethods()) {
            doScan( method );
        }
    }
    public static void doScan( Class... classes ) throws NoSuchMethodException {
        for (Class singleClass : classes) {
            doScan( singleClass );
        }
    }

    /**
     * Initialize scan of params for given method. If param isRequired then do param type scanning
     * {@link #scanSubFields(Class, Set)}.
     * @param method
     * @throws NoSuchMethodException
     */
    public static void doScan( Method method ) throws NoSuchMethodException {
        StringBuilder builder = new StringBuilder( "\n\nScanning: " ).append( method.getName() );
        Set<Class> scanned = new HashSet<>();

        for (Parameter parameter : method.getParameters()) {
            Annotated annotationInstance = parameter.getAnnotation( Annotated.class );
            if ( Objects.nonNull( annotationInstance ) ) {
                Class type = parameter.getType();
                builder
                        .append( "; Param: " )
                        .append( MessageFormat.format(annotationInstance.value(), type.getSimpleName()) )
                        .append( "; Names: " )
                        .append( scanSubFields( type, scanned ) );
            }
        }
        System.out.println( builder );
    }


    /**
     * Going through all declared fields, looking for {@link Annotated} annotation.
     * If field annotated - add field name to names list. If field type wasn't scanned before, then recursive
     * call {@link #scanSubFields(Class, Set)} for current field type
     * @param type class that will be scanned on Annotated fields
     * @param scanned set of already scanned classes (antiDeadLoop)
     * @return list of names
     */
    public static List<String> scanSubFields( Class type, Set<Class> scanned ) {
        List<String> names = new ArrayList<>();
        if ( !scanned.contains( type ) ) {
            scanned.add( type );
            for (Field field : type.getDeclaredFields()) {
                Annotated annotationInstance = field.getAnnotation( Annotated.class );
                if ( Objects.nonNull( annotationInstance ) ) {
                    names.add( MessageFormat.format( annotationInstance.value(), field.getName() ) );
                    names.addAll( scanSubFields( field.getType(), scanned ) );
                }
            }
        }
        return names;
    }

    /**
     * Method example.  Contains annotated param - {@link Bean}
     * @param bean target object which contains declared fields which are annotated with {@link Annotated}
     */
    public void test( @Annotated("bean") Bean bean ) {

    }

}

