cmake_minimum_required(VERSION 3.10)
project(jni-lib)

set(CMAKE_CXX_STANDARD 17)

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})
add_subdirectory(sapi-tts)

add_library(jni_lib SHARED jni_lib.cc)
target_link_libraries(jni_lib tts_lib)

