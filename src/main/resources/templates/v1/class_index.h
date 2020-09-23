#pragma once

#include "../globals.h"
#include "mapping.h"

#define findClass(name) jni->FindClass(name)
#define staticMethodId(clazz, name, sig) jni->GetStaticMethodID(clazz, name, sig)
#define methodId(clazz, name, sig) jni->GetMethodID(clazz, name, sig)
#define staticFieldId(clazz, name, sig) jni->GetStaticFieldID(clazz, name, sig)
#define fieldId(clazz, name, sig) jni->GetFieldID(clazz, name, sig)
#define OBF(cleanName) Mapping::find(#cleanName)

struct Bindings;
inline Bindings *bindings;

struct Bindings {
<generate>
};