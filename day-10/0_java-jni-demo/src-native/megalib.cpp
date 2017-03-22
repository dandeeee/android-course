#include <iostream>
#include "com_dandeeee_jnidemo_NativeCallsClass.h"


JNIEXPORT void JNICALL Java_com_dandeeee_jnidemo_NativeCallsClass_printOne(JNIEnv *env, jclass myclass)
{
    std::cout << "One" << std::endl;
}

JNIEXPORT void JNICALL Java_com_dandeeee_jnidemo_NativeCallsClass_printTwo(JNIEnv *env, jclass myclass)
{
    std::cout << "Two" << std::endl;
}
