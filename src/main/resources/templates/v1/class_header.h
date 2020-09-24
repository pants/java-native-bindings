#include "class_index.h"
#include "java_class.h"

struct <class_name> : public JavaClass {
    explicit <class_name>(JNIEnv *env, jobject instance) : JavaClass(env, instance) {}

    <fields>

    <static_functions>

    <methods>
};