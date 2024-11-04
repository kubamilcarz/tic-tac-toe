cmake_minimum_required(VERSION 3.29)
project(backend)

set(CMAKE_CXX_STANDARD 20)

add_library(backend SHARED library.cpp)

target_include_directories(backend PRIVATE "/Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home/include")
target_include_directories(backend PRIVATE "/Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home/include/darwin")