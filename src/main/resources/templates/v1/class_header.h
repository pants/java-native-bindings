#include "class_index.h"
#include "java_class.h"

struct <class_name> : public <parent> {
    explicit <class_name>(JNIEnv *env, jobject instance) : <parent>(env, instance) {}

    <fields>

    <static_functions>

    <methods>
};